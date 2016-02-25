package org.istic.synthlab.components.vcfa;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.istic.synthlab.components.AbstractController;
import org.istic.synthlab.ui.plugins.controls.Potentiometer;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends AbstractController implements Initializable {
    @FXML
    public Potentiometer cutoffFrequency;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
