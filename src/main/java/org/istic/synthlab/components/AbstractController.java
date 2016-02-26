package org.istic.synthlab.components;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.istic.synthlab.ui.ConnectionManager;
import org.istic.synthlab.ui.CoreController;
import org.istic.synthlab.ui.plugins.ComponentPane;
import org.istic.synthlab.ui.plugins.cable.InputPlug;
import org.istic.synthlab.ui.plugins.cable.OutputPlug;
import org.istic.synthlab.ui.plugins.history.StateType;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The Abstract controller which abstracts components connections
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public abstract class AbstractController implements IController {

    protected IComponent component;
    protected ConnectionManager manager;

    @FXML
    protected Label title;
    @FXML
    protected ComponentPane componentPane;
    @FXML
    protected InputPlug input;
    @FXML
    protected InputPlug inputAm;
    @FXML
    protected InputPlug inputFm;
    @FXML
    protected InputPlug inputGate;
    @FXML
    protected OutputPlug output;
    @FXML
    protected OutputPlug outputGate;

    private static int numInstance = 0;

    /**
     * Controller.
     *
     * @param component the component
     */
    public void configure(IComponent component) {
        this.component = component;
        this.manager = CoreController.getConnectionManager();
        numInstance++;
        component.setId(numInstance);
        componentPane.setId(component.toString());
        title.setText(component.getName());
    }

    public void initialize(URL location, ResourceBundle resources) {
        // Need to test null beforesetting the text. In case of a module that does not use any of these plugs
        if (input != null) {
            input.setText("Input");
        }
        if (inputAm != null) {
            inputAm.setText("AM");
        }
        if (inputFm != null) {
            inputFm.setText("FM.");
        }
        if (inputGate != null) {
            inputGate.setText("Gate in");
        }
        if (output != null) {
            output.setText("Output");
        }
        if (outputGate != null) {
            outputGate.setText("Gate out");
        }
    }

    @Override
    public IComponent getComponent() {
        return component;
    }

    /**
     * Method call when the close button is clicked.
     * Send the instance and the main pane to the deleteComponent method of the ConnectionManager
     */
    public void close() {
        manager.deleteComponent(componentPane);
    }

    public void connectInput(final MouseEvent event) {
        manager.plug((Node) event.getSource(), component.getInput());
        event.consume();
    }

    public void connectInputFm(final MouseEvent event) {
        manager.plug((Node) event.getSource(), component.getFm());
        event.consume();
    }

    public void connectInputAm(final MouseEvent event) {
        manager.plug((Node) event.getSource(), component.getAm());
        event.consume();
    }

    public void connectInputGate(final MouseEvent event) {
        manager.plug((Node) event.getSource(), component.getInputGate());
        event.consume();
    }

    /**
     * Method called in view component file and activate a connection manager calling the plug method
     * with the output variable
     */
    public void connectOutput(final MouseEvent event) {
        manager.plug((Node) event.getSource(), component.getOutput());
        event.consume();
    }

    public void connectOutputGate(final MouseEvent event) {
        manager.plug((Node) event.getSource(), component.getOutputGate());
        event.consume();
    }

    @Override
    public void activate() {
        component.activate();
    }

    @Override
    public void deactivate() {
        component.deactivate();
    }

    @Override
    public boolean isActivated() {
        return component.isActivated();
    }

    @Override
    public void init() {
    }

    @Override
    public void run() {
    }
}
