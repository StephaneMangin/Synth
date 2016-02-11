package org.istic.synthlab.components.vca;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.istic.synthlab.core.AbstractController;
import org.istic.synthlab.ui.ConnectionManager;
import org.istic.synthlab.ui.controls.Potentiometer;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Dechaud John Marc on 2/8/16.
 */
public class Controller extends AbstractController implements Initializable {

    private final Vca vca = new Vca("VCA " + numInstance++);
    private static int numInstance = 0;

    @FXML
    private ImageView input;
    @FXML
    private ImageView am;
    @FXML
    private ImageView output;
    @FXML
    private Potentiometer gain;
    @FXML
    private Potentiometer amplitude;
    @FXML
    private ImageView circleEvent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        amplitude.valueProperty().addListener((observable, oldValue, newValue) -> {
            vca.setAmplitudeModulatorValue((Double) newValue);
        });

        gain.valueProperty().addListener((observable, oldValue, newValue) -> {
            vca.setGainModulatorValue((Double) newValue);
        });
    }

    @FXML
    public void connectAm(final MouseEvent event) {
        ConnectionManager.makeDestination(vca, (Node) event.getSource(), vca.getAm());
    }

    @FXML
    public void connectInput(final MouseEvent event) {
        ConnectionManager.makeDestination(vca, (Node) event.getSource(), vca.getInput());
    }

    @FXML
    public void connectOutput(final MouseEvent event) {
        ConnectionManager.makeOrigin(vca, (Node) event.getSource(), vca.getOutput());
    }
}
