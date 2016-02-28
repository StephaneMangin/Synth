package org.istic.synthlab.components.vcfa;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.istic.synthlab.components.AbstractController;
import org.istic.synthlab.ui.plugins.controls.Potentiometer;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends AbstractController {
    @FXML
    public Potentiometer cutoffFrequency;

    public Controller() {
        configure(null);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }
}
