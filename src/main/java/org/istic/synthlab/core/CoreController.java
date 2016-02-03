package org.istic.synthlab.core;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class CoreController implements Initializable {
    @FXML
    public ListView<Label> listView;
    @FXML
    public GridPane gridPane;

    public void initialize(URL location, ResourceBundle resources) {
        // Populate the ListView
        Label testLabel = new Label("VCOA");
        listView.setItems(FXCollections.observableArrayList(testLabel));

        // Label d&d events
        testLabel.setOnDragDetected(event -> {
            Dragboard db = testLabel.startDragAndDrop(TransferMode.COPY);

            ClipboardContent content = new ClipboardContent();
            content.putString(testLabel.getText());
            db.setContent(content);

            event.consume();
        });

        // Fill the GridPane with Panes
        for (int row = 0; row < gridPane.getRowConstraints().size(); row++) {
            for (int col = 0; col < gridPane.getColumnConstraints().size(); col++) {
                Pane p = new Pane();
                p.setStyle("-fx-background-color: red; -fx-border-color: black;");

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
                        Label copiedLabel = new Label(db.getString());
                        p.getChildren().add(copiedLabel);
                        success = true;
                    }
                    event.setDropCompleted(success);
                    event.consume();
                });

                gridPane.add(p, col, row);
            }
        }
    }
}
