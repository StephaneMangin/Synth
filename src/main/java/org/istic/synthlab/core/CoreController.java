package org.istic.synthlab.core;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class CoreController implements Initializable, IObserver {
    private ObservableList<Node> data;
    @FXML
    public ListView<Node> listView;
    @FXML
    public GridPane gridPane;
    @FXML
    private TextArea textarea;
    @FXML
    public Button pauseButton;
    @FXML
    public Button playButton;

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

    public ObservableList<Node> addComponents2List() {
        data = FXCollections.observableArrayList();
        Label vcoaLabel = new Label("vcoa");
        data.add(vcoaLabel);
        Label outLabel = new Label("out");
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
                Node nodeComponentsList = FXMLLoader.load(getClass().getResource("/ListViewComponents.fxml"));
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

    @FXML
    public void onPause(ActionEvent actionEvent) {
        pauseButton.setDisable(true);
        playButton.setDisable(false);
    }

    @FXML
    public void onPlay(ActionEvent actionEvent) {
        pauseButton.setDisable(false);
        playButton.setDisable(true);
    }
}
