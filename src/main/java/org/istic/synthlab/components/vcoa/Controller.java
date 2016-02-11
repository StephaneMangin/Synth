package org.istic.synthlab.components.vcoa;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
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
    private AnchorPane mainPane;
    @FXML
    private Circle output;
    @FXML
    private Circle circleEvent;
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

    private final ToggleGroup groupRadio = new ToggleGroup();

    private Vcoa vcoa               = new Vcoa("VCOA" + numInstance++);
    private static int numInstance  = 0;

    /**
     * When the component is created, it initialize the component representation and adding listener and MouseEvent
     * @param location type URL
     * @param resources type ResourceBundle
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        output.addEventHandler(MouseEvent.MOUSE_CLICKED, new GetIdWithClick());
        vcoa.setAmplitudeSquare(1);
        vcoa.setAmplitudeSine(1);
        vcoa.setAmplitudeTriangle(1);
        vcoa.setAmplitudePulse(1);
        vcoa.setAmplitudeImpulse(1);
        vcoa.setAmplitudeRedNoise(1);
        vcoa.setAmplitudeSawTooth(1);
        vcoa.setExponentialFrequency(440);
        expFrequency.valueProperty().addListener((observable, oldValue, newValue) -> {
            vcoa.setExponentialFrequency((double)newValue);
        });
        close.setStyle("-fx-background-image: url('/ui/images/closeIconMin.png');-fx-background-color: white;");
        linFrequency.valueProperty().addListener((observable, oldValue, newValue) -> {
            vcoa.setLinearFrequency((double)newValue);
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
    public void connectOut() {
        ConnectionManager.makeOrigin(vcoa, circleEvent, vcoa.getOutput());
    }

    @FXML
    public void closeIt(){
        ConnectionManager.deleteComponent(vcoa);
    }
    /**
     * Get the object clicked in the view and cast it into a Circle object
     */
    private class GetIdWithClick implements EventHandler<Event> {
        @Override
        public void handle(Event event) {
            circleEvent = (Circle) event.getSource();
        }
    }
}
