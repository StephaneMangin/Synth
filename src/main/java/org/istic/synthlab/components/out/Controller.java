package org.istic.synthlab.components.out;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.istic.synthlab.components.AbstractController;
import org.istic.synthlab.ui.ConnectionManager;
import org.istic.synthlab.ui.controls.Potentiometer;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class Controller extends AbstractController implements Initializable {
    @FXML
    private AnchorPane outPane;
    @FXML
    private Node input;
    @FXML
    private Potentiometer amplitude;
    @FXML
    private Button close;
    private Out componentOut = new Out("Out " + numInstance++);
    private static int numInstance = 0;

    /**
     * When the component is created, it initialize the component representation adding listener and MouseEvent
     * @param location type URL
     * @param resources type ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        outPane.setId("outPane"+numInstance);
        //componentOut.start();
        amplitude.valueProperty().addListener((observable, oldValue, newValue) -> {
            componentOut.getAmModulator().setValue(newValue.doubleValue());
        });
        amplitude.setValue(0);
        close.setStyle("-fx-background-image: url('/ui/images/closeIconMin.png');-fx-background-color: white;");
    }

    /**
     * Method called in view component file and start a connection manager calling the makeDestination method
     * with the input variable
     */
    @FXML
    public void connectInput(final Event event) {
        ConnectionManager.makeDestination(componentOut, (Node) event.getSource(), componentOut.getInput());
    }

    /**
     * Method call when the close button is clicked.
     * Send the instance and the main pane to the deleteComponent method of the ConnectionManager
     */
    @FXML
    public void closeIt() {
        ConnectionManager.deleteComponent(componentOut, outPane);
    }
}
