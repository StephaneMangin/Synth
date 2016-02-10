package org.istic.synthlab.components.out;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import org.istic.synthlab.core.AbstractController;
import org.istic.synthlab.ui.ConnectionManager;
import org.istic.synthlab.ui.controls.Potentiometer;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author stephane
 */
public class Controller extends AbstractController implements Initializable {

    @FXML
    Circle input;

    @FXML
    private Potentiometer amplitude;

    private Circle circleEvent;
    private Out componentOut = new Out("Out"+numInstance++);
    private static int numInstance = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        input.addEventHandler(MouseEvent.MOUSE_CLICKED, new GetIdWithClick());
        componentOut.start();
        amplitude.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Amplitude changed from " + oldValue + " to " + newValue);
            componentOut.getInputModulator().setValue((double) newValue);
        });
        amplitude.setValue(0);
    }

    @FXML
    public void connectIn(){
        ConnectionManager.makeDestination(circleEvent, componentOut.getInput());
    }

    private class GetIdWithClick implements EventHandler<Event> {
        @Override
        public void handle(Event event){
            circleEvent = (Circle)event.getSource();
        }
    }
}
