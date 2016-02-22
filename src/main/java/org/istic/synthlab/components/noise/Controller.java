package org.istic.synthlab.components.noise;

/**
 * Created by paola on 22/02/16.
 */

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.istic.synthlab.components.AbstractController;
import org.istic.synthlab.ui.ConnectionManager;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends AbstractController {

    @FXML
    private Pane rootModulePane;
    @FXML private ImageView output;

    private Noise noise = new Noise("White Noise");

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        configure(noise);
    }

    @FXML
    public void connectOutput(final MouseEvent event) {
        ConnectionManager.makeOrigin(this.noise, (Node) event.getSource(), this.noise.getOutput());
    }
}
