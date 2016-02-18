package org.istic.synthlab.components;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.istic.synthlab.ui.ConnectionManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The Abstract controller which abstracts components connections
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public abstract class AbstractController implements IController {

    private IComponent component;

    @FXML
    protected Label title;
    @FXML
    protected AnchorPane anchorPane;

    private static int numInstance = 0;

    /**
     * Controller.
     *
     * @param component the component
     */
    public void configure(IComponent component) {
        this.component = component;
        numInstance++;
        component.setId(numInstance);
        anchorPane.setId(component.toString());
        title.setText(component.getName());
    }

    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Gets component.
     *
     * @return the component
     */
    public IComponent getComponent() {
        return component;
    }

    /**
     * Method call when the close button is clicked.
     * Send the instance and the main pane to the deleteComponent method of the ConnectionManager
     */
    public void close() {
        ConnectionManager.deleteComponent(component, anchorPane);
    }

    public void connectInput(final MouseEvent event) {
        ConnectionManager.makeDestination(component, (Node) event.getSource(), component.getInput());
    }

    public void connectInputFm(final MouseEvent event) {
        ConnectionManager.makeDestination(component, (Node) event.getSource(), component.getFm());
    }

    public void connectInputAm(final MouseEvent event) {
        ConnectionManager.makeDestination(component, (Node) event.getSource(), component.getAm());
    }

    public void connectInputGate(final MouseEvent event) {
        ConnectionManager.makeDestination(component, (Node) event.getSource(), component.getInputGate());
    }

    /**
     * Method called in view component file and activate a connection manager calling the makeDestination method
     * with the output variable
     */
    public void connectOutput(final MouseEvent event) {
        ConnectionManager.makeOrigin(component, (Node) event.getSource(), component.getOutput());
    }

    public void connectOutputGate(final MouseEvent event) {
        ConnectionManager.makeOrigin(component, (Node) event.getSource(), component.getOutputGate());
    }
}
