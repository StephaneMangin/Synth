package org.istic.synthlab.components.generic;

import javafx.embed.swing.SwingNode;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import org.istic.synthlab.ui.ConnectionManager;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by seb on 04/02/16.
 */
public class Controller implements Initializable{

    @FXML
    Circle input;
    @FXML
    Circle output;
    @FXML
    Circle circleEvent;

    private static int numInstance = 0;
    private generic generic = new generic("Generic"+numInstance++);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        input.addEventHandler(MouseEvent.MOUSE_CLICKED, new getIdWithClick());
        output.addEventHandler(MouseEvent.MOUSE_CLICKED, new getIdWithClick());

    }
    @FXML
    void connectOut(){
        ConnectionManager.makeOrigin(circleEvent, generic.getOutput());
    }

    @FXML
    void connectIn(){
        ConnectionManager.makeDestination(circleEvent, generic.getInput());
    }

    private class getIdWithClick implements EventHandler<Event> {
        @Override
        public void handle(Event event) {
            circleEvent = (Circle)event.getSource();
        }
    }


}
