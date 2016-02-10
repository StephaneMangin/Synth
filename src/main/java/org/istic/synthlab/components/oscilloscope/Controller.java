package org.istic.synthlab.components.oscilloscope;

import javafx.embed.swing.SwingNode;
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
    private Pane pane;
    @FXML
    private Circle input;
    @FXML
    private Circle output;
    @FXML
    private Circle circleEvent;

    private static int numInstance = 0;
    private Oscilloscope oscilloscope = new Oscilloscope("Visualizer"+numInstance++);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        input.addEventHandler(MouseEvent.MOUSE_CLICKED, new GetIdWithClick());
        output.addEventHandler(MouseEvent.MOUSE_CLICKED, new GetIdWithClick());

        final SwingNode swingNode = new SwingNode();
        final JPanel panel = oscilloscope.getView();
        //final JButton panel = new JButton("mefer");
        panel.setPreferredSize(new Dimension(120, 80));

        SwingUtilities.invokeLater(() -> swingNode.setContent(panel));
        pane.getChildren().add(swingNode);
    }
    @FXML
    public void connectOut(){
        ConnectionManager.makeOrigin(circleEvent, oscilloscope.getOutput());
    }

    @FXML
    public void connectIn(){
        ConnectionManager.makeDestination(circleEvent, oscilloscope.getInput());
    }

    private class GetIdWithClick implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            circleEvent = (Circle)event.getSource();
        }
    }


}
