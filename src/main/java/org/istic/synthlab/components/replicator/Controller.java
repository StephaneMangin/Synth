package org.istic.synthlab.components.replicator;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import org.istic.synthlab.components.AbstractController;
import org.istic.synthlab.ui.plugins.plug.OutputPlug;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller of the Replicator component.
 *
 * @author gottstein[dot]cyprien[at]gmail[dot]com on 09/02/16.
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class Controller extends AbstractController implements Initializable {

    @FXML
    private OutputPlug output2;
    @FXML
    private OutputPlug output3;

    private Replicator replicator = new Replicator("Replicator");

    /**
     * Constructor of the Replicator component controller.
     */
    public Controller() {
        configure(replicator);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        output2.setText("Output 2");
        output2.setOutput(replicator.getOutput2());
        output3.setText("Output 3");
        output3.setOutput(replicator.getOutput3());
    }

    @FXML
    public void connectOutput2(final MouseEvent event) {
        manager.plugOutput((OutputPlug) event.getSource());
        event.consume();
    }

    @FXML
    public void connectOutput3(final MouseEvent event) {
        manager.plugOutput((OutputPlug) event.getSource());
        event.consume();
    }
}
