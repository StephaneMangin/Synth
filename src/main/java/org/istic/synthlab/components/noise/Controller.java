package org.istic.synthlab.components.noise;


import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.istic.synthlab.components.AbstractController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author paola
 */
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
}
