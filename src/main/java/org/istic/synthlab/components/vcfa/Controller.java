package org.istic.synthlab.components.vcfa;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.istic.synthlab.core.components.AbstractController;
import org.istic.synthlab.ui.plugins.controls.Potentiometer;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class Controller extends AbstractController implements Initializable {
    @FXML
    public Potentiometer cutoffFrequency;
    @FXML
    public Potentiometer filterResonance;
    @FXML
    private Label frequency;

    private Vcfa vcfa = new Vcfa("Voltage Controlled Filter");

    public Controller() {
        configure(vcfa);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        cutoffFrequency.valueProperty().addListener((observable, oldValue, newValue) -> {
            vcfa.setCutoff((double) newValue);
            updateScreen();
        });
        cutoffFrequency.setTitle("Cutoff");
        cutoffFrequency.setMinValue(Integer.toString(0));
        cutoffFrequency.setMaxValue(Integer.toString(20000));

        filterResonance.valueProperty().addListener((observable, oldValue, newValue) -> {
            vcfa.setResonance((double) newValue);
        });
        filterResonance.setTitle("Resonance");
        filterResonance.setMinValue(0);
        filterResonance.setMaxValue(1);
    }

    private void updateScreen() {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        frequency.setText(String.valueOf(twoDForm.format(vcfa.getCutoff())) + "Hz");
    }
}
