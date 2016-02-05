package org.istic.synthlab.components.vcoa;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import org.istic.synthlab.core.AbstractController;
import org.istic.synthlab.core.ui.ConnectionManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by stephane on 02/02/16.
 */
public class Controller extends AbstractController implements Initializable {
    @FXML
    Circle input;
    @FXML
    Circle input1;
    Circle circleEvent;
    Line line;
    private static int numInstance = 0;
    private Vcoa composantVcoa = new Vcoa("VCOA"+numInstance++);

    public void initialize(URL location, ResourceBundle resources) {
        input.addEventHandler(MouseEvent.MOUSE_CLICKED, new getIdWithClick());
        input1.addEventHandler(MouseEvent.MOUSE_CLICKED, new getIdWithClick());
    }

    @FXML
    void connectIn(){
        ConnectionManager.makeOrigin(composantVcoa.getOutput());
        IHMConnectionManager.makeOrigin(circleEvent, composantVcoa.getOutput());
        IHMConnectionManager.makeOrigin(circleEvent, composantVcoa.getOutput());

    }

    private class getIdWithClick implements EventHandler<Event> {
        @Override
        public void handle(Event event) {
            circleEvent = (Circle)event.getSource();
        }
    }
}
