package org.istic.synthlab.components.eg;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import org.istic.synthlab.ui.ConnectionManager;
import org.istic.synthlab.ui.controls.Potentiometer;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Paola
 */
public class Controller implements Initializable{

    @FXML
    private AnchorPane mainPane;
    @FXML
    private Circle output;
    @FXML
    private Circle gate;
    @FXML
    private Circle circleEvent;
    @FXML
    private Potentiometer attack;
    @FXML
    private Potentiometer release;
    @FXML
    private Potentiometer sustain;
    @FXML
    private Potentiometer decay;

    private static int numInstance  = 0;
    private Eg eg = new Eg("EG" + numInstance++);

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        output.addEventHandler(MouseEvent.MOUSE_CLICKED, new GetIdWithClick());
        gate.addEventHandler(MouseEvent.MOUSE_CLICKED, new GetIdWithClick());

        release.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Release time changed from " + oldValue + " to " + newValue);
            eg.setRelease((double) newValue);
        });

        attack.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Attack changed from " + oldValue + " to " + newValue);
            eg.setAttack((double) newValue);
        });
        sustain.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Sustain changed from " + oldValue + " to " + newValue);
            eg.setSustain((double) newValue);
        });
        decay.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Decay changed from " + oldValue + " to " + newValue);
            eg.setDecay((double) newValue);
        });
    }

    /**
     * Method called in view component file and start a connection manager calling the makeDestination method
     * with the output variable
     */
    @FXML
    public void connectOut() {
        ConnectionManager.makeOrigin(eg , circleEvent, eg.getOutput());
    }

    /**
     * Method called in view component file and start a connection manager calling the makeDestination method
     * with the input variable
     */
    @FXML
    public void connectGate() {
        ConnectionManager.makeDestination(eg, circleEvent, eg.getInput());
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
