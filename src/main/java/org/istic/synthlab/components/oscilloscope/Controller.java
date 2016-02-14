package org.istic.synthlab.components.oscilloscope;

import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.istic.synthlab.ui.ConnectionManager;

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
    private LineChart chart;

    private static int numInstance = 0;
    private Oscilloscope oscilloscope = new Oscilloscope("Oscilloscope " + numInstance++);
    /**
     * When the component is created, it initialize the component representation adding listener and MouseEvent
     * @param location type URL
     * @param resources type ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        oscilloscopePane.setId("oscilloscopePane"+numInstance);

        final SwingNode swingNode = new SwingNode();
        swingNode.setContent(this.oscilloscope.getView());
        pane.getChildren().add(swingNode);
    }

    /**
     * Method called in view component file and start a connection manager calling the makeDestination method
     * with the output variable
     */
    @FXML
    public void connectOutput(final MouseEvent event) {
        ConnectionManager.makeOrigin(oscilloscope, (Node) event.getSource(), oscilloscope.getOutput());
    }

    /**
     * Method called in view component file and start a connection manager calling the makeDestination method
     * with the input variable
     */
    @FXML
    public void connectInput(final MouseEvent event) {
        ConnectionManager.makeDestination(oscilloscope, (Node) event.getSource(), oscilloscope.getInput());
    }

    @FXML
    public void close() {
        ConnectionManager.deleteComponent(oscilloscope, oscilloscopePane);
    }
}
