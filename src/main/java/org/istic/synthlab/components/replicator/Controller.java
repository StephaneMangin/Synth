package org.istic.synthlab.components.replicator;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
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

    private final Replicator replicator = new Replicator("Replicator " + numInstance++);
    private static int numInstance  = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        replicatorPane.setId("replicatorPane" + numInstance);
    }

    @FXML
    public void connectInput(final MouseEvent event) {
        ConnectionManager.makeDestination(replicator, (Node) event.getSource(), replicator.getInput());
    }

    @FXML
    public void connectOutput1(final MouseEvent event) {
        ConnectionManager.makeOrigin(replicator, (Node) event.getSource(), replicator.getOutput());
    }

    @FXML
    public void connectOutput2(final MouseEvent event) {
        ConnectionManager.makeOrigin(replicator, (Node) event.getSource(), replicator.getOutputReplicated1());
    }

    @FXML
    public void connectOutput3(final MouseEvent event) {
        ConnectionManager.makeOrigin(replicator, (Node) event.getSource(), replicator.getOutputReplicated2());
    }

    /**
     * Method call when the close button is clicked.
     * Send the instance and the main pane to the deleteComponent method of the ConnectionManager
     */
    @FXML
    public void close() {
        ConnectionManager.deleteComponent(replicator, replicatorPane);
    }
}
