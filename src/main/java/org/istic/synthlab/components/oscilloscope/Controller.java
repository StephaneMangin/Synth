package org.istic.synthlab.components.oscilloscope;

import javafx.embed.swing.SwingNode;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import org.istic.synthlab.components.vcoa.Vcoa;
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
    Pane pane;
    @FXML
    Circle input;
    @FXML
    Circle output;
    @FXML
    Circle circleEvent;

    private static int numInstance = 0;
    private Oscilloscope oscilloscope = new Oscilloscope("Oscilloscope"+numInstance++);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        input.addEventHandler(MouseEvent.MOUSE_CLICKED, new getIdWithClick());
        output.addEventHandler(MouseEvent.MOUSE_CLICKED, new getIdWithClick());
        final SwingNode swingNode = new SwingNode();
        JPanel panel = oscilloscope.getVIew();
        // Force the size by using a dimension instance instead of setSize only
        Dimension dim = new Dimension();
        dim.setSize(80, 120);
        panel.setPreferredSize(dim);
        //swingNode.setContent(panel);
        pane.getChildren().add(swingNode);

    }
    @FXML
    void connectOut(){
        ConnectionManager.makeOrigin(circleEvent, oscilloscope.getOutput());
    }

    @FXML
    void connectIn(){
        ConnectionManager.makeDestination(circleEvent, oscilloscope.getInput());
    }

    private class getIdWithClick implements EventHandler<Event> {
        @Override
        public void handle(Event event) {
            circleEvent = (Circle)event.getSource();
        }
    }


}
