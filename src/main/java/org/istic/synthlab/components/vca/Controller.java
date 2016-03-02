package org.istic.synthlab.components.vca;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.istic.synthlab.components.AbstractController;
import org.istic.synthlab.ui.plugins.controls.Potentiometer;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller of the Vca component.
 *
 * @author Dechaud John Marc on 2/8/16.
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class Controller extends AbstractController implements Initializable {
    @FXML
    private Potentiometer gain;

    private Vca vca = new Vca("Voltage Controlled\n Amplifier");

    /**
     * Constructor of the Vca component controller.
     */
    public Controller() {
        configure(vca);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        gain.valueProperty().addListener((observable, oldValue, newValue) -> {
            //vca.getGainModulator().setValue((Double) newValue);
            vca.getGainPotentiometer().setValue((Double) newValue);
        });
        gain.setTitle("Gain");
        /*gain.setMinValue(vca.getGainModulator().getMin());
        gain.setMaxValue(vca.getGainModulator().getMax());*/

        gain.setMinValue(vca.getGainPotentiometer().getMin());
        gain.setMaxValue(vca.getGainPotentiometer().getMax());
    }
}
