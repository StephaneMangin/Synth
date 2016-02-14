package org.istic.synthlab.components.vcoa;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.istic.synthlab.components.AbstractController;
import org.istic.synthlab.core.modules.oscillators.OscillatorType;
import org.istic.synthlab.ui.ConnectionManager;
import org.istic.synthlab.ui.controls.Potentiometer;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author stephane
 */
public class Controller extends AbstractController implements Initializable {
    @FXML
    private AnchorPane vcoaPane;
    @FXML
    private Potentiometer expFrequency;
    @FXML
    private Potentiometer linFrequency;
    @FXML
    private Potentiometer amplitude;
    @FXML
    private RadioButton sineRadio;
    @FXML
    private RadioButton squareRadio;
    @FXML
    private RadioButton triangleRadio;
    @FXML
    public RadioButton sawtoothRadio;
    @FXML
    public RadioButton redNoiseRadio;
    @FXML
    public RadioButton whiteNoiseRadio;
    @FXML
    private ImageView oscillatorImage;

    private final ToggleGroup groupRadio = new ToggleGroup();

    private Vcoa vcoa = new Vcoa("VCOA" + numInstance++);
    private static int numInstance = 0;

    /**
     * When the component is created, it initialize the component representation and adding listener and MouseEvent
     * @param location type URL
     * @param resources type ResourceBundle
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        vcoaPane.setId("vcoaPane"+numInstance);
        vcoa.setAmplitudeSquare(1);
        vcoa.setAmplitudeSine(1);
        vcoa.setAmplitudeTriangle(1);
        vcoa.setAmplitudePulse(1);
        vcoa.setAmplitudeImpulse(1);
        vcoa.setAmplitudeRedNoise(1);
        vcoa.setAmplitudeSawTooth(1);
        vcoa.setAmplitudeRedNoise(1);
        vcoa.setAmplitudeWhiteNoise(1);
        vcoa.setExponentialFrequency(440);

        // Configure exponential potentiometer
        expFrequency.valueProperty().addListener((observable, oldValue, newValue) -> {
            vcoa.setExponentialFrequency((Double) newValue);
        });
        expFrequency.setTitle("Exp. Freq.");
        expFrequency.setMinValue(vcoa.getExponentialFrequencyMin());
        expFrequency.setMaxValue(vcoa.getExponentialFrequencyMax());

        // Configure linear potentiometer
        linFrequency.valueProperty().addListener((observable, oldValue, newValue) -> {
            vcoa.setLinearFrequency((Double) newValue);
        });
        linFrequency.setTitle("Linear Freq.");
        linFrequency.setMinValue(vcoa.getLinearFrequencyMin());
        linFrequency.setMaxValue(vcoa.getLinearFrequencyMax());

        // Configure amplitude potentiometer
        amplitude.valueProperty().addListener((observable, oldValue, newValue) -> {
            vcoa.setAmplitudeOscillator((Double) newValue);
        });
        amplitude.setTitle("Amp.");
        amplitude.setMinValue(vcoa.getAmplitudeOscillatorMin());
        amplitude.setMaxValue(vcoa.getAmplitudeOscillatorMax());

        sineRadio.setToggleGroup(groupRadio);
        squareRadio.setToggleGroup(groupRadio);
        triangleRadio.setToggleGroup(groupRadio);
        sawtoothRadio.setToggleGroup(groupRadio);
        redNoiseRadio.setToggleGroup(groupRadio);
        whiteNoiseRadio.setToggleGroup(groupRadio);

        sineRadio.setUserData("sineWave");
        squareRadio.setUserData("squareWave");
        triangleRadio.setUserData("triangleWave");
        sawtoothRadio.setUserData("sawtoothWave");
        redNoiseRadio.setUserData("rednoiseWave");
        whiteNoiseRadio.setUserData("whitenoiseWave");

        groupRadio.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (groupRadio.getSelectedToggle() != null) {
                // FIXME: add sawtoothWave.png
                final Image image = new Image(getClass().getResourceAsStream("/ui/images/" + groupRadio.getSelectedToggle().getUserData() + ".png"));
                oscillatorImage.setImage(image);

                switch (groupRadio.getSelectedToggle().getUserData().toString()) {
                    case "sineWave":
                        vcoa.setOscillatorType(OscillatorType.SINE);
                        break;
                    case "squareWave":
                        vcoa.setOscillatorType(OscillatorType.SQUARE);
                        break;
                    case "triangleWave":
                        vcoa.setOscillatorType(OscillatorType.TRIANGLE);
                        break;
                    case "sawtoothWave":
                        vcoa.setOscillatorType(OscillatorType.SAWTOOTH);
                        break;
                    case "rednoiseWave":
                        vcoa.setOscillatorType(OscillatorType.REDNOISE);
                        break;
                    case "whitenoiseWave":
                        vcoa.setOscillatorType(OscillatorType.WHITENOISE);
                        break;
                    default:
                        break;
                }
            }
        });



        squareRadio.setSelected(true);
    }
    /**
     * Method called in view component file and start a connection manager calling the makeDestination method
     * with the output variable
     */
    @FXML
    public void connectOutput(final Event event) {
        ConnectionManager.makeOrigin(vcoa, (Node) event.getSource(), vcoa.getOutput());
    }

    /**
     * Method call when the close button is clicked.
     * Send the instance and the main pane to the deleteComponent method of the ConnectionManager
     */
    @FXML
    public void close(){
        ConnectionManager.deleteComponent(vcoa, vcoaPane);
    }

    @FXML
    public void connectFm(final Event event) {
        ConnectionManager.makeDestination(vcoa, (Node) event.getSource(), vcoa.getFm());
    }
}
