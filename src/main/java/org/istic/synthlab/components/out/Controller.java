package org.istic.synthlab.components.out;

import javafx.fxml.FXML;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
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
    private Node input;
    @FXML
    private Potentiometer amplitude;

    private Out componentOut        = new Out("Out"+numInstance++);
    private Node circleEvent;
    private static int numInstance  = 0;

    /**
     * When the component is created, it initialize the component representation adding listener and MouseEvent
     * @param location type URL
     * @param resources type ResourceBundle
     */
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

    /**
     * Method called in view component file and start a connection manager calling the makeDestination method
     * with the input variable
     */
    @FXML
    public void connectIn(){
        ConnectionManager.makeDestination(circleEvent, componentOut.getInput());
    }

    /**
     * Get the object clicked in the view and cast it into a Circle object
     */
    private class GetIdWithClick implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event){
            circleEvent = (Node) event.getSource();
        }
    }
}
