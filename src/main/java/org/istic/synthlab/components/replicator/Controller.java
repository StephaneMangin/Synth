package org.istic.synthlab.components.replicator;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import org.istic.synthlab.core.AbstractController;
import org.istic.synthlab.ui.ConnectionManager;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void connectInput() {
        //ConnectionManager.makeOrigin();
    }

    @FXML
    public void connectOutput1() {
    }

    @FXML
    public void connectOutput2() {
    }

    @FXML
    public void connectOutput3() {
    }
}
