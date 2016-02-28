package org.istic.synthlab.components.replicator;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import org.istic.synthlab.components.AbstractController;
import org.istic.synthlab.ui.ConnectionManager;
import org.istic.synthlab.ui.plugins.cable.OutputPlug;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author gottstein[dot]cyprien[at]gmail[dot]com on 09/02/16.
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class Controller extends AbstractController implements Initializable {

    @FXML
    private OutputPlug output2;
    @FXML
    private OutputPlug output3;

    private Replicator replicator = new Replicator("Replicator");

    public Controller() {
        configure(replicator);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        output2.setText("Input 1");
        output2.setOutput(replicator.getOutput2());
        output3.setText("Input 2");
        output3.setOutput(replicator.getOutput3());
    }

    @FXML
    public void connectOutput2(final MouseEvent event) {
        manager.plugOutput((OutputPlug) event.getSource());
    }

    @FXML
    public void connectOutput3(final MouseEvent event) {
        manager.plugOutput((OutputPlug) event.getSource());
    }
}
