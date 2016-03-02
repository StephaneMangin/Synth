package org.istic.synthlab.components.rednoise;

import javafx.fxml.FXML;
import org.istic.synthlab.components.AbstractController;
import org.istic.synthlab.ui.plugins.controls.Potentiometer;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller of the Noise component, the noise generated is a white noise.
 *
 * @author  Ngassam Noumi Paola npaolita[at]yahoo[dot]fr
 */
public class Controller extends AbstractController {

    @FXML
    private Potentiometer frequency;

    private RedNoise redNoise = new RedNoise("Red Noise");

    /**
     * Constructor of the Noise controller component.
     */
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

        // Configure exponential potentiometer
        frequency.valueProperty().addListener((observable, oldValue, newValue) -> {
            redNoise.setFrequency((double)newValue);
        });
        frequency.setTitle("Frequency");
    }
}
