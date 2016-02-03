package org.istic.synthlab.core;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class CoreController implements Initializable, IObserver {
    private ObservableList data;
    private Node nodeComponentsList;

    @FXML
    public ListView<Label> listView;
    @FXML
    public GridPane gridPane;
    @FXML
    private TextArea textarea;

    public void initialize(URL location, ResourceBundle resources){
        // Populate the ListView
        data = FXCollections.observableArrayList();
        listView.setItems(addComponents2List());

        // Fill the GridPane with Panes
        for (int row = 0; row < gridPane.getRowConstraints().size(); row++) {
            for (int col = 0; col < gridPane.getColumnConstraints().size(); col++) {
                Pane p = new Pane();

                // Pane d&d events
                p.setOnDragOver(event -> {
                    if (event.getGestureSource() != p && event.getDragboard().hasString()) {
                        event.acceptTransferModes(TransferMode.COPY);
                    }
                    event.consume();
                });

                p.setOnDragDropped(event -> {
                    Dragboard db = event.getDragboard();
                    boolean success = false;
                    if (db.hasString()) {
                        try {
                            Node node = FXMLLoader.load(getClass().getResource("/"+db.getString()+".fxml"));
                            p.getChildren().add(node);
                            success = true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    event.setDropCompleted(success);
                    event.consume();
                });

                gridPane.add(p, col, row);
            }
        }
        IHMConnectionManager.addObserver(this);
    }

    @Override
    public void update(Map<String, String> arg) {
        String total = "";
        Set cles = arg.keySet();
        Iterator it = cles.iterator();
        while(it.hasNext()){
            String origin = (String) it.next();
            String destination = arg.get(origin);
            total += origin + " ---------> " + destination + "\n";
        }
        textarea.setText(total);
    }

    public ObservableList addComponents2List(){
        data = FXCollections.observableArrayList();
        Label testLabel = new Label("vcoa");
        data.add(testLabel);

        // Label d&d events
        testLabel.setOnDragDetected(event -> {
            Dragboard db = testLabel.startDragAndDrop(TransferMode.COPY);
            ClipboardContent content = new ClipboardContent();
            content.putString(testLabel.getText());
            db.setContent(content);
            event.consume();
        });
        for(int i=0;i<1;i++){
            try {
                nodeComponentsList = FXMLLoader.load(getClass().getResource("/ListViewComponents.fxml"));
                nodeComponentsList.setId("paneComponents"+i);
                data.add(nodeComponentsList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}
