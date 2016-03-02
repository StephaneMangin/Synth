package org.istic.synthlab.components.eg;

import javafx.fxml.FXML;
import org.istic.synthlab.core.components.AbstractController;
import org.istic.synthlab.ui.plugins.controls.Potentiometer;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller of the Envelope Generator component.
 *
 * @author Paola
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 *
 */
public class Controller extends AbstractController {
    @FXML
    private Potentiometer attack;
    @FXML
    private Potentiometer release;
    @FXML
    private Potentiometer sustain;
    @FXML
    private Potentiometer decay;

    private Eg eg = new Eg("Envelope Generator");

    /**
     * Constructor of the Envelope Generator component controller.
     *
     */
    public Controller() {
        configure(eg);
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

        release.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Release time changed from " + oldValue + " to " + newValue);
            eg.getReleasePotentiometer().setValue((double) newValue);
        });
        release.setTitle("Release");
        release.setMinValue(eg.getReleasePotentiometer().getMin());
        release.setMaxValue(eg.getReleasePotentiometer().getMax());

        attack.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Attack changed from " + oldValue + " to " + newValue);
            eg.getAttackPotentiometer().setValue((double) newValue);
        });
        attack.setTitle("Attack");
        attack.setMinValue(eg.getAttackPotentiometer().getMin());
        attack.setMaxValue(eg.getAttackPotentiometer().getMax());

        sustain.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Sustain changed from " + oldValue + " to " + newValue);
            eg.getSustainPotentiometer().setValue((double) newValue);
        });
        sustain.setTitle("Sustain");
        sustain.setMinValue(eg.getSustainPotentiometer().getMin());
        sustain.setMaxValue(eg.getSustainPotentiometer().getMax());

        decay.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Decay changed from " + oldValue + " to " + newValue);
            eg.getDecayPotentiometer().setValue((double) newValue);
        });
        decay.setTitle("Decay");
        decay.setMinValue(eg.getDecayPotentiometer().getMin());
        decay.setMaxValue(eg.getDecayPotentiometer().getMax());
    }
}
