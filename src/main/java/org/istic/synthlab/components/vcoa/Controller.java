package org.istic.synthlab.components.vcoa;

import javafx.fxml.FXML;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.istic.synthlab.ui.ConnectionManager;
import org.istic.synthlab.core.AbstractController;
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
    private Potentiometer squareFrequency;

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
        squareFrequency.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Frequency changed from " + oldValue + " to " + newValue);
            vcoa.setExponentialFrequency((double)newValue);
        });
    }
    /**
     * Method called in view component file and start a connection manager calling the makeDestination method
     * with the output variable
     */
    @FXML
    public void connectOut() {
        ConnectionManager.makeOrigin(circleEvent, vcoa.getOutput());
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
