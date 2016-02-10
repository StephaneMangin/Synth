package org.istic.synthlab.components.out;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import org.istic.synthlab.core.AbstractController;
import org.istic.synthlab.ui.ConnectionManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author stephane
 */
public class Controller extends AbstractController implements Initializable {

    @FXML
    Circle input;
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
        public void handle(Event event){
            circleEvent = (Circle)event.getSource();
            System.out.println("Out BoundsInLocal: "+circleEvent.localToScene(circleEvent.getCenterX(), circleEvent.getCenterY()));
            System.out.println("Out BoundsInParent: "+circleEvent.localToScene(circleEvent.getCenterX(), circleEvent.getCenterY()));
            System.out.println("Out BoundsInLocal: "+circleEvent.localToScreen(circleEvent.getCenterX(), circleEvent.getCenterY()));
            System.out.println("Out BoundsInParent: "+circleEvent.localToScreen(circleEvent.getCenterX(), circleEvent.getCenterY()));
        }
    }
}
