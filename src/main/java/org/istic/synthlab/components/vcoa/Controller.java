package org.istic.synthlab.components.vcoa;

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
 * @author stephane
 */
public class Controller extends AbstractController implements Initializable {
    @FXML
    Circle output;
    @FXML
    Circle circleEvent;
    private static int numInstance = 0;
    private Vcoa composantVcoa = new Vcoa("VCOA"+numInstance++);

    public void initialize(URL location, ResourceBundle resources) {
        output.addEventHandler(MouseEvent.MOUSE_CLICKED, new getIdWithClick());
        composantVcoa.setAmplitudeSquare(1);
        composantVcoa.setAmplitudeImpulse(1);
        composantVcoa.setAmplitudePulse(1);
        composantVcoa.setAmplitudeTriangle(1);
        composantVcoa.setAmplitudeSawTooth(1);
        composantVcoa.setAmplitudeSine(1);
        composantVcoa.setExponentialFrequency(880);
    }

    @FXML
    void connectOut(){
        ConnectionManager.makeOrigin(circleEvent, composantVcoa.getOutput());
    }

    private class getIdWithClick implements EventHandler<Event> {
        @Override
        public void handle(Event event) {
            circleEvent = (Circle)event.getSource();
        }
    }
}
