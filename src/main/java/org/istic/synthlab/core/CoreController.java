package org.istic.synthlab.core;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class CoreController implements Initializable, IObserver {
    public static final int NB_ROWS = 5;
    public static final int NB_COLS = 5;
    public static final int ROWS_PREF_HEIGHT = 200;
    public static final int COLS_PREF_WIDTH = 300;

    @FXML
    private ListView<Node> listView;
    @FXML
    private GridPane gridPane;
    @FXML
    private TextArea textarea;
    @FXML
    private Button pauseButton;
    @FXML
    private Button playButton;

    public void initialize(URL location, ResourceBundle resources) {
        populateListView();

        for (int row = 0; row < NB_ROWS; row++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(ROWS_PREF_HEIGHT);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        for (int col = 0; col < NB_COLS; col++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPrefWidth(COLS_PREF_WIDTH);
            gridPane.getColumnConstraints().add(columnConstraints);
        }

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

    private void populateListView() {
        ObservableList<Node> data = FXCollections.observableArrayList();
        Label vcoaLabel = new Label("vcoa");
        Label outLabel = new Label("out");

        vcoaLabel.setOnDragDetected(new DragListItemEventHandler());
        outLabel.setOnDragDetected(new DragListItemEventHandler());

        data.add(vcoaLabel);
        data.add(outLabel);

        /*for (int i=0; i<1; i++){
            try {
                Node nodeComponentsList = FXMLLoader.load(getClass().getResource("/ListViewComponents.fxml"));
                nodeComponentsList.setId("paneComponents"+i);
                data.add(nodeComponentsList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        listView.setItems(data);
    }

    @FXML
    public void onActionClose() {
        Platform.exit();
    }

    @FXML
    public void onPause() {
        pauseButton.setDisable(true);
        playButton.setDisable(false);
    }

    @FXML
    public void onPlay() {
        pauseButton.setDisable(false);
        playButton.setDisable(true);
    }

    private class DragListItemEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            Label label = (Label) event.getSource();
            Dragboard db = label.startDragAndDrop(TransferMode.COPY);
            ClipboardContent content = new ClipboardContent();
            content.putString(label.getText());
            db.setContent(content);
            event.consume();
        }
    }
}
