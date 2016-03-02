package org.istic.synthlab.components;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

/**
 * The interface Component
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public interface IController extends Initializable {

    /**
     * Method called to activate a connection manager
     */
    @FXML
    void connectInput(final MouseEvent event);

    /**
     * Method called to activate a connection manager
     */
    @FXML
    void connectInputAm(final MouseEvent event);

    /**
     * Method called to activate a connection manager
     */
    @FXML
    void connectInputFm(final MouseEvent event);

    /**
     * Method called to activate a connection manager
     */
    @FXML
    void connectInputGate(final MouseEvent event);

    /**
     * Method called to activate a connection manager
     */
    @FXML
    void connectOutput(final MouseEvent event);

    /**
     * Method called to activate a connection manager
     */
    @FXML
    void connectOutputGate(final MouseEvent event);

    /**
     * Method call when the close button is clicked.
     */
    @FXML
    void close();

    void activate();

    void deactivate();

    boolean isActivated();

    void init();

    void run();

    /**
     * Gets component.
     *
     * @return the component
     */
    IComponent getComponent();
}
