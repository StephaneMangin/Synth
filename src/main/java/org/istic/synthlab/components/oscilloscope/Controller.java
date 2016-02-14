package org.istic.synthlab.components.oscilloscope;

import com.jsyn.scope.swing.AudioScopeView;
import javafx.embed.swing.SwingNode;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import org.istic.synthlab.ui.ConnectionManager;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Sebastien
 */
public class Controller implements Initializable {

    @FXML
    private AnchorPane oscilloscopePane;
    @FXML
    private Pane pane;
    @FXML
    public Group swingNodeGroup;
    @FXML
    private Circle input;
    @FXML
    private Circle output;
    @FXML
    private Circle circleEvent;
    @FXML
    private LineChart chart;

    private static int numInstance      = 0;
    private Oscilloscope oscilloscope   = new Oscilloscope("Visualizer"+numInstance++);
    /**
     * When the component is created, it initialize the component representation adding listener and MouseEvent
     * @param location type URL
     * @param resources type ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        oscilloscopePane.setId("oscilloscopePane"+numInstance);
        input.addEventHandler(MouseEvent.MOUSE_CLICKED, new GetIdWithClick());
        output.addEventHandler(MouseEvent.MOUSE_CLICKED, new GetIdWithClick());

        AudioScopeView byuu = (AudioScopeView) this.oscilloscope.getView();
        byuu.setSize(new Dimension(25, 25));

        //this.oscilloscope.activate();
        final SwingNode swingNode = new SwingNode();
        swingNode.setContent(this.oscilloscope.getView());
        pane.getChildren().add(swingNode);
    }

    /**
     * Method called in view component file and start a connection manager calling the makeDestination method
     * with the output variable
     */
    @FXML
    public void connectOut(){
        ConnectionManager.makeOrigin(oscilloscope, circleEvent, oscilloscope.getOutput());
    }

    /**
     * Method called in view component file and start a connection manager calling the makeDestination method
     * with the input variable
     */
    @FXML
    public void connectIn(){
        ConnectionManager.makeDestination(oscilloscope, circleEvent, oscilloscope.getInput());
    }

    /**
     * Get the object clicked in the view and cast it into a Circle object
     */
    private class GetIdWithClick implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            circleEvent = (Circle)event.getSource();
        }
    }

}
