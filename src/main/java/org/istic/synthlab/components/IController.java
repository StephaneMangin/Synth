package org.istic.synthlab.components;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import org.istic.synthlab.ui.ConnectionManager;

/**
 * @author  Group1
 *
 * The interface ComponentPane
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public interface IController extends Initializable {

    /**
     * Method called to activate a connection manager
     *
     * @param event
     */
    @FXML
    void connectInput(final MouseEvent event);

    /**
     * Method called to activate a connection manager
     *
     * @param event
     */
    @FXML
    void connectInputAm(final MouseEvent event);

    /**
     * Method called to activate a connection manager
     *
     * @param event
     */
    @FXML
    void connectInputFm(final MouseEvent event);

    /**
     * Method called to activate a connection manager
     *
     * @param event
     */
    @FXML
    void connectInputGate(final MouseEvent event);

    /**
     * Method called to activate a connection manager
     *
     * @param event
     */
    @FXML
    void connectOutput(final MouseEvent event);

    /**
     * Method called to activate a connection manager
     *
     * @param event
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
}
