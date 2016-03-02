package org.istic.synthlab.core.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.istic.synthlab.ui.ConnectionManager;
import org.istic.synthlab.ui.CoreController;
import org.istic.synthlab.ui.plugins.workspace.ComponentPane;
import org.istic.synthlab.ui.plugins.plug.InputPlug;
import org.istic.synthlab.ui.plugins.plug.OutputPlug;

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

    /**
     * Controller main module configuration
     *
     * @param component the component
     */
    public void configure(IComponent component) {
        this.component = component;
        this.manager = CoreController.getConnectionManager();
    }

    public void initialize(URL location, ResourceBundle resources) {
        // Declare Input and output into plugs
        // Need to test null beforesetting the text. In case of a module that does not use any of these plugs
        title.setText(component.getName());
        componentPane.setId(component.getId());
        if (input != null) {
            input.setText("Input");
            input.setInput(component.getInput());
        }
        if (inputAm != null) {
            inputAm.setText("AM");
            inputAm.setInput(component.getAm());
        }
        if (inputFm != null) {
            inputFm.setText("FM.");
            inputFm.setInput(component.getFm());
        }
        if (inputGate != null) {
            inputGate.setText("Gate in");
            inputGate.setInput(component.getInputGate());
        }
        if (output != null) {
            output.setText("Output");
            output.setOutput(component.getOutput());
        }
        if (outputGate != null) {
            outputGate.setText("Gate out");
            outputGate.setOutput(component.getOutputGate());
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
        component.close();
    }

    public void connectInput(final MouseEvent event) {
        manager.plugInput((InputPlug) event.getSource());
        event.consume();
    }

    public void connectInputFm(final MouseEvent event) {
        manager.plugInput((InputPlug) event.getSource());
        event.consume();
    }

    public void connectInputAm(final MouseEvent event) {
        manager.plugInput((InputPlug) event.getSource());
        event.consume();
    }

    public void connectInputGate(final MouseEvent event) {
        manager.plugInput((InputPlug) event.getSource());
        event.consume();
    }

    /**
     * Method called in view component file and activate a connection manager calling the plug method
     * with the output variable
     */
    public void connectOutput(final MouseEvent event) {
        manager.plugOutput((OutputPlug) event.getSource());
        event.consume();
    }

    public void connectOutputGate(final MouseEvent event) {
        manager.plugOutput((OutputPlug) event.getSource());
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
