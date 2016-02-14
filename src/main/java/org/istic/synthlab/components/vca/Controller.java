package org.istic.synthlab.components.vca;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.istic.synthlab.components.AbstractController;
import org.istic.synthlab.ui.ConnectionManager;
import org.istic.synthlab.ui.controls.Potentiometer;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Dechaud John Marc on 2/8/16.
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class Controller extends AbstractController implements Initializable {
    @FXML
    private Potentiometer gain;
    @FXML
    private Potentiometer amplitude;

    private Vca vca = new Vca("VCA");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        configure(vca);

        amplitude.valueProperty().addListener((observable, oldValue, newValue) -> {
            vca.getAmplitudeModulator().setValue((Double) newValue);
        });
        amplitude.setTitle("Amplitude");
        amplitude.setMinValue(vca.getAmplitudeModulator().getMin());
        amplitude.setMaxValue(vca.getAmplitudeModulator().getMax());

        gain.valueProperty().addListener((observable, oldValue, newValue) -> {
            vca.getAmplitudeModulator().setValue((Double) newValue);
        });
        gain.setTitle("Gain");
        gain.setMinValue(vca.getAmplitudeModulator().getMin());
        gain.setMaxValue(vca.getAmplitudeModulator().getMax());
    }
}
