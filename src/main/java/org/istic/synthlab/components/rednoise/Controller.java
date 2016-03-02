package org.istic.synthlab.components.rednoise;


import org.istic.synthlab.components.AbstractController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author  Ngassam Noumi Paola npaolita[at]yahoo[dot]fr
 */
public class Controller extends AbstractController {

    private RedNoise redNoise = new RedNoise("Red Noise");

    public Controller() {
        configure(redNoise);
    }

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
    }
}
