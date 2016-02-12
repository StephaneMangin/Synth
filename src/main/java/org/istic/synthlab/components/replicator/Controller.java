package org.istic.synthlab.components.replicator;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.istic.synthlab.components.AbstractController;
import org.istic.synthlab.ui.ConnectionManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author gottstein[dot]cyprien[at]gmail[dot]com on 09/02/16.
 */
public class Controller extends AbstractController implements Initializable {
    @FXML
    private AnchorPane replicatorPane;
    @FXML
    private ImageView input;
    @FXML
    private ImageView output1;
    @FXML
    private ImageView output2;
    @FXML
    private ImageView output3;
    @FXML
    private Button close;

    private final Replicator replicator = new Replicator("Replicator " + numInstance++);
    private static int numInstance  = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        replicatorPane.setId("replicatorPane"+numInstance);
        close.setStyle("-fx-background-image: url('/ui/images/closeIconMin.png');-fx-background-color: white;");
    }

    @FXML
    public void connectInput(final Event event) {
        ConnectionManager.makeDestination(replicator, input, replicator.getInput());
    }

    @FXML
    public void connectOutput1() {
        ConnectionManager.makeOrigin(replicator, output1, replicator.getOutput());
    }

    @FXML
    public void connectOutput2() {
        ConnectionManager.makeOrigin(replicator, output2, replicator.getOutputReplicated1());
    }

    @FXML
    public void connectOutput3() {
        ConnectionManager.makeOrigin(replicator, output3, replicator.getOutputReplicated2());
    }
    /**
     * Method call when the close button is clicked.
     * Send the instance and the main pane to the deleteComponent method of the ConnectionManager
     */
    @FXML
    public void closeIt(){
        ConnectionManager.deleteComponent(replicator, replicatorPane);
    }
}
