package org.istic.synthlab.components.vcoa;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.istic.synthlab.core.AbstractController;
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
    private Node output;
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
    private ImageView oscillatorImage;
    @FXML
    private Button close;
    @FXML
    private Label currentFrequency;

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
        vcoa.setExponentialFrequency(0.5);
        expFrequency.valueProperty().addListener((observable, oldValue, newValue) -> {
            vcoa.setExponentialFrequency((double)newValue);
            currentFrequency.setText(""+(int)vcoa.getFrequency());
        });
        close.setStyle("-fx-background-image: url('/ui/images/closeIconMin.png');-fx-background-color: white;");
        linFrequency.valueProperty().addListener((observable, oldValue, newValue) -> {
            vcoa.setLinearFrequency((double)newValue);
            currentFrequency.setText(""+(int)vcoa.getFrequency());
        });

        amplitude.valueProperty().addListener((observable, oldValue, newValue) -> {
            vcoa.setAmplitudeOscillator((double)newValue);
        });

        sineRadio.setToggleGroup(groupRadio);
        squareRadio.setToggleGroup(groupRadio);
        triangleRadio.setToggleGroup(groupRadio);
        sineRadio.setUserData("sineWave");
        squareRadio.setUserData("squareWave");
        triangleRadio.setUserData("triangleWave");
        groupRadio.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (groupRadio.getSelectedToggle() != null) {
                final Image image = new Image(
                        getClass().getResourceAsStream("/ui/images/"+groupRadio.getSelectedToggle().getUserData() + ".png")
                );
                oscillatorImage.setImage(image);
                if (groupRadio.getSelectedToggle().getUserData().toString().equals("sineWave")) {
                    vcoa.setOscillatorType(OscillatorType.SINE);
                }
                else if (groupRadio.getSelectedToggle().getUserData().toString().equals("squareWave")) {
                    vcoa.setOscillatorType(OscillatorType.SQUARE);
                }
                else if (groupRadio.getSelectedToggle().getUserData().toString().equals("triangleWave")) {
                    vcoa.setOscillatorType(OscillatorType.TRIANGLE);
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
    public void closeIt(){
        ConnectionManager.deleteComponent(vcoa, vcoaPane);
    }

    @FXML
    public void connectFm(final Event event) {
        ConnectionManager.makeDestination(vcoa, (Node) event.getSource(), vcoa.getFm());
    }
}
