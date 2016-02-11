package org.istic.synthlab.components.out;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import org.istic.synthlab.components.AbstractController;
import org.istic.synthlab.ui.ConnectionManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class Controller extends AbstractController implements Initializable {

    @FXML
    Circle input;
    @FXML
    private Circle circleEvent;

    private Out componentOut = new Out("Out"+numInstance++);

    private static int numInstance = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        input.addEventHandler(MouseEvent.MOUSE_CLICKED, new getIdWithClick());
        componentOut.start();
    }

    @FXML
    void connectIn(){
        ConnectionManager.makeDestination(circleEvent, componentOut.getInput());
    }

    private class getIdWithClick implements EventHandler<Event> {
        @Override
        public void handle(Event event) {
            circleEvent = (Circle)event.getSource();
        }
    }

    public void setAmplitude(double value) {
        componentOut.getAmModulator().setValue(value);
    }

    public double getAmplitude() {
        return componentOut.getAmModulator().getValue();
    }
}
