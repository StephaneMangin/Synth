package org.istic.synthlab.core;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;

import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.ResourceBundle;

public class CoreController implements Initializable {

    private ObservableList data;

    private Node nodeComponentsList;

    @FXML
    public ListView listView;
    @FXML
    public BorderPane mainFrame;
    @FXML
    public GridPane gridPane;
    @FXML
    public ListView<Label> listView;
    @FXML
    public GridPane gridPane;

    public void initialize(URL location, ResourceBundle resources) {
        data = FXCollections.observableArrayList();
        listView.setItems(addComponents2List());
        System.out.println(gridPane);
    }
    public ObservableList addComponents2List(){
        data = FXCollections.observableArrayList();
        for(int i=0;i<5;i++){
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
        // Populate the ListView
        Label testLabel = new Label("VCOA");
        listView.setItems(FXCollections.observableArrayList(testLabel));

    public void initGrid(){
        gridPane.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            for(Node node : gridPane.getChildren()){
                if(node instanceof Pane){
                    if(node.getBoundsInParent().contains(event.getSceneX(), event.getSceneY())){
                        System.out.println( "Node: " + node + " at " + GridPane.getRowIndex( node) + "/" + GridPane.getColumnIndex( node));
                    }
                }
            }
        });
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
                //p.setStyle("-fx-background-color: red; -fx-border-color: black;");

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
