package org.istic.synthlab.components.replicator;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
<<<<<<< HEAD
import org.istic.synthlab.components.AbstractController;
=======
import javafx.scene.image.ImageView;
import org.istic.synthlab.core.AbstractController;
import org.istic.synthlab.ui.ConnectionManager;
>>>>>>> master

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author gottstein[dot]cyprien[at]gmail[dot]com on 09/02/16.
 */
public class Controller extends AbstractController implements Initializable {
    @FXML
    private ImageView input;
    @FXML
    private ImageView output1;
    @FXML
    private ImageView output2;
    @FXML
    private ImageView output3;

    private final Replicator replicator = new Replicator("Replicator " + numInstance++);
    private static int numInstance  = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void connectInput() {
        ConnectionManager.makeDestination(input, replicator.getInput());
    }

    @FXML
    public void connectOutput1() {
        ConnectionManager.makeOrigin(output1, replicator.getOutput());
    }

    @FXML
    public void connectOutput2() {
        ConnectionManager.makeOrigin(output2, replicator.getOutputReplicated1());
    }

    @FXML
    public void connectOutput3() {
        ConnectionManager.makeOrigin(output3, replicator.getOutputReplicated2());
    }
}
