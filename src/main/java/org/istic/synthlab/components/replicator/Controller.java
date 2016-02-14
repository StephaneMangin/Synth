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

    private Replicator replicator = new Replicator("Replicator");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        configure(replicator);
        super.initialize(location, resources);
    }

    @FXML
    public void connectOutput2(final MouseEvent event) {
        ConnectionManager.makeOrigin(replicator, (Node) event.getSource(), replicator.getOutputReplicated2());
    }

    @FXML
    public void connectOutput1(final MouseEvent event) {
        ConnectionManager.makeOrigin(replicator, (Node) event.getSource(), replicator.getOutputReplicated1());
    }
}
