package org.istic.synthlab.ui;

import javafx.application.Platform;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import org.istic.synthlab.core.IObserver;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class NotUsedCoreController implements Initializable, IObserver {
    private ObservableList<Node> data;
    @FXML
    public BorderPane borderPane;
    @FXML
    public ListView<Node> listView;
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
        ConnectionManager.addObserver(this);
    }

    @Override
    public void update(Map<IOutput,IInput> arg) {
        String total = "";
        Set keys = arg.keySet();
        for (Object key : keys) {
            IOutput origin = (IOutput) key;
            IInput destination = arg.get(origin);
            total += origin.toString() + " ---------> " + destination.toString() + "\n";
        }
        textarea.setText(total);
    }

    @Override
    public void drawLine(HashMap<Line, HashMap<IOutput, IInput>> arg) {
        Set keys = arg.keySet();
        for(Object key : keys){
            System.out.println("Je suis dans la boucle for avec comme key: "+key);
            borderPane.getChildren().add((Line)key);
        }
    }

    public ObservableList<Node> addComponents2List() {
        data = FXCollections.observableArrayList();
        Label vcoaLabel = new Label("vcoa");
        data.add(vcoaLabel);
        Label outLabel = new Label("ui/components/vcoa/out");
        data.add(outLabel);


        // Label d&d events
        vcoaLabel.setOnDragDetected(event -> {
            Dragboard db = vcoaLabel.startDragAndDrop(TransferMode.COPY);
            ClipboardContent content = new ClipboardContent();
            content.putString(vcoaLabel.getText());
            db.setContent(content);
            event.consume();
        });

        outLabel.setOnDragDetected(event -> {
            Dragboard db = outLabel.startDragAndDrop(TransferMode.COPY);
            ClipboardContent content = new ClipboardContent();
            content.putString(outLabel.getText());
            db.setContent(content);
            event.consume();
        });
        for(int i=0;i<1;i++){
            try {
                Node nodeComponentsList = FXMLLoader.load(getClass().getResource("/ui/ListViewComponents.fxml"));
                nodeComponentsList.setId("paneComponents"+i);
                data.add(nodeComponentsList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    @FXML
    public void onActionClose() {
        Platform.exit();
    }
}
