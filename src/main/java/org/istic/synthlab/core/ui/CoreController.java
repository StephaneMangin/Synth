package org.istic.synthlab.core.ui;

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
import javafx.scene.input.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import org.istic.synthlab.core.IObserver;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * FX controller of core.fxml
 * Represents the global view of the application
 * @author Thibaut Rousseau <thibaut.rousseau@outlook.com>
 */
public class CoreController implements Initializable, IObserver {
    /**
     * The default number of rows in the grid
     */
    public static final int DEFAULT_NB_ROWS = 5;
    /**
     * The default number of cols in the grid
     */
    public static final int DEFAULT_NB_COLS = 5;
    /**
     * The default height of the rows in the grid
     */
    public static final int DEFAULT_ROWS_PREF_HEIGHT = 200;
    /**
     * The default width of the columns in the grid
     */
    public static final int DEFAULT_COLS_PREF_WIDTH = 300;

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

    /**
     * This method initializes the list view and the grid
     * @param location Not used
     * @param resources Not used
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeListView();
        initializeGridView();
        ConnectionManager.addObserver(this);
    }

    @Override
    public void update(Map<IOutput, IInput> arg) {
        String total = "";
        Set keys = arg.keySet();
        for (Object key : keys) {
            IOutput origin = (IOutput) key;
            IInput destination = arg.get(origin);
            total += origin.toString() + " ---------> " + destination.toString() + "\n";
        }
        textarea.setText(total);
    }

    private void initializeListView() {
        final ObservableList<Node> data = FXCollections.observableArrayList();
        final Label vcoaLabel = new Label("vcoa");
        final Label outLabel = new Label("out");
        final Label oscilloscopeLabel = new Label("oscilloscope");

        vcoaLabel.setOnDragDetected(new DragDetectedListItemEventHandler());
        outLabel.setOnDragDetected(new DragDetectedListItemEventHandler());
        oscilloscopeLabel.setOnDragDetected(new DragDetectedListItemEventHandler());

        data.add(vcoaLabel);
        data.add(outLabel);
        data.add(oscilloscopeLabel);


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

    private void initializeGridView() {
        for (int row = 0; row < DEFAULT_NB_ROWS; row++) {
            final RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(DEFAULT_ROWS_PREF_HEIGHT);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        for (int col = 0; col < DEFAULT_NB_COLS; col++) {
            final ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPrefWidth(DEFAULT_COLS_PREF_WIDTH);
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        // Fill the GridPane with Panes
        for (int row = 0; row < DEFAULT_NB_ROWS; row++) {
            for (int col = 0; col < DEFAULT_NB_COLS; col++) {
                final Pane p = new Pane();

                p.setOnDragOver(new DragOverPaneEventHandler());
                p.setOnDragEntered(new DragEnteredPaneEventHandler());
                p.setOnDragExited(new DragExitedPaneEventHandler());
                p.setOnDragDropped(new DragDroppedPaneEventHandler());

                gridPane.add(p, col, row);
            }
        }
    }

    /**
     * Close the application
     */
    @FXML
    public void onActionClose() {
        Platform.exit();
    }

    /**
     * Pause the synthesizer
     */
    @FXML
    public void onPause() {
        pauseButton.setDisable(true);
        playButton.setDisable(false);
    }

    /**
     * Start the synthesizer
     */
    @FXML
    public void onPlay() {
        pauseButton.setDisable(false);
        playButton.setDisable(true);
    }

    private class DragDetectedListItemEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            final Label label = (Label) event.getSource();
            final Dragboard db = label.startDragAndDrop(TransferMode.COPY);
            final ClipboardContent content = new ClipboardContent();
            content.putString(label.getText());
            db.setContent(content);
            event.consume();
        }
    }

    private class DragOverPaneEventHandler implements EventHandler<DragEvent> {
        @Override
        public void handle(DragEvent event) {
            final Pane p = (Pane) event.getSource();
            if (event.getGestureSource() != p && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        }
    }

    private class DragEnteredPaneEventHandler implements EventHandler<DragEvent> {
        @Override
        public void handle(DragEvent event) {
            final Pane p = (Pane) event.getSource();
            if (event.getGestureSource() != p && event.getDragboard().hasString()) {
                p.setStyle("-fx-background-color: fuchsia");
            }
            event.consume();
        }
    }

    private class DragExitedPaneEventHandler implements EventHandler<DragEvent> {
        @Override
        public void handle(DragEvent event) {
            final Pane p = (Pane) event.getSource();
            p.setStyle("-fx-background-color: transparent;");
            event.consume();
        }
    }

    private class DragDroppedPaneEventHandler implements EventHandler<DragEvent> {
        @Override
        public void handle(DragEvent event) {
            final Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                try {
                    final Pane p = (Pane) event.getSource();
                    final Node node = FXMLLoader.load(getClass().getResource("/"+db.getString()+".fxml"));
                    p.getChildren().add(node);
                    success = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            event.setDropCompleted(success);
            event.consume();
        }
    }
}
