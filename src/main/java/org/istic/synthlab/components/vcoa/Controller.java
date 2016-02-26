package org.istic.synthlab.components.vcoa;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.istic.synthlab.components.AbstractController;
import org.istic.synthlab.core.modules.oscillators.OscillatorType;
import org.istic.synthlab.ui.plugins.controls.Potentiometer;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

/**
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class Controller extends AbstractController {
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
    @FXML
    private Label frequency;

    @FXML
    private RadioButton shortcut1Hz;
    @FXML
    private RadioButton shortcut10Hz;
    @FXML
    private RadioButton shortcut100Hz;
    @FXML
    private RadioButton shortcut1KHz;
    @FXML
    private RadioButton shortcut10KHz;

    private final ToggleGroup groupRadio = new ToggleGroup();
    private final ToggleGroup groupShortcut = new ToggleGroup();

    private Vcoa vcoa = new Vcoa("Voltage Controlled Oscillator type A");

    /**
     * When the component is created, it initialize the component representation and adding listener and MouseEvent
     * @param location type URL
     * @param resources type ResourceBundle
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        super.initialize(location, resources);
        configure(vcoa);
        vcoa.setAmplitudeSquare(1);
        vcoa.setAmplitudeSine(1);
        vcoa.setAmplitudeTriangle(1);
        vcoa.setAmplitudePulse(1);
        vcoa.setAmplitudeImpulse(1);
        vcoa.setAmplitudeRedNoise(1);
        vcoa.setAmplitudeSawTooth(1);
        vcoa.setAmplitudeRedNoise(1);
        vcoa.setAmplitudeWhiteNoise(1);
        vcoa.setExponentialFrequency(0.0);

        // Configure exponential potentiometer
        expFrequency.valueProperty().addListener((observable, oldValue, newValue) -> {
            vcoa.setExponentialFrequency((Double)newValue);
            updateScreen();
            groupShortcut.selectToggle(null);
            linFrequency.setMinValue(String.valueOf((int) (vcoa.getLinearFrequencyMin()*vcoa.getExponentialFrequency())));
            linFrequency.setMaxValue(String.valueOf((int) (vcoa.getLinearFrequencyMax()*vcoa.getExponentialFrequency())));
        });
        expFrequency.setTitle("Exp. Freq.");
        expFrequency.setMinValue(0);
        expFrequency.setMaxValue(10);

        // Configure linear potentiometer
        linFrequency.valueProperty().addListener((observable, oldValue, newValue) -> {
            vcoa.setLinearFrequency((Double) newValue);
            updateScreen();
            groupShortcut.selectToggle(null);
        });
        linFrequency.setTitle("Linear Freq.");

        // Configure amplitude potentiometer
        amplitude.valueProperty().addListener((observable, oldValue, newValue) -> {
            vcoa.setAmplitudeOscillator((Double) newValue);
        });
        amplitude.setTitle("Amplitude");
        amplitude.setMinValue("0%");
        amplitude.setMaxValue("100%");

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
        redNoiseRadio.setUserData("redNoiseWave");
        whiteNoiseRadio.setUserData("whiteNoiseWave");

        shortcut1Hz.setToggleGroup(groupShortcut);
        shortcut10Hz.setToggleGroup(groupShortcut);
        shortcut100Hz.setToggleGroup(groupShortcut);
        shortcut1KHz.setToggleGroup(groupShortcut);
        shortcut10KHz.setToggleGroup(groupShortcut);

        shortcut1Hz.setUserData("1Hz");
        shortcut10Hz.setUserData("10Hz");
        shortcut100Hz.setUserData("100Hz");
        shortcut1KHz.setUserData("1KHz");
        shortcut10KHz.setUserData("10KHz");

        groupRadio.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (groupRadio.getSelectedToggle() != null) {
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
                    case "redNoiseWave":
                        vcoa.setOscillatorType(OscillatorType.REDNOISE);
                        break;
                    case "whiteNoiseWave":
                        vcoa.setOscillatorType(OscillatorType.WHITENOISE);
                        break;
                    default:
                        break;
                }
            }
        });

        // TODO: not finished. Find a way to calculate exponential based on hertz
        groupShortcut.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (groupShortcut.getSelectedToggle() != null) {
                switch (groupShortcut.getSelectedToggle().getUserData().toString()) {
                    case "1Hz":
                        expFrequency.valueProperty().setValue(0.05);
                        break;
                    case "10Hz":
                        expFrequency.valueProperty().setValue(0.2161);
                        break;
                    case "100Hz":
                        expFrequency.valueProperty().setValue(0.78);
                        break;
                    case "1KHz":
                        expFrequency.valueProperty().setValue(1);
                        break;
                    case "10KHz":
                        expFrequency.valueProperty().setValue(1);
                        break;
                    default:
                        break;
                }
                linFrequency.valueProperty().setValue(0);
                updateScreen();
            }
        });

        squareRadio.setSelected(true);
    }

    private void updateScreen() {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        frequency.setText(String.valueOf(twoDForm.format(vcoa.getFrequency())) + "Hz");
    }
}
