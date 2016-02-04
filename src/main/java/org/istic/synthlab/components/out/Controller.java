package org.istic.synthlab.components.out;

import javafx.fxml.FXML;
import org.istic.synthlab.core.AbstractController;
import org.istic.synthlab.core.ui.ConnectionManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by stephane on 02/02/16.
 */
public class Controller extends AbstractController {

    private Out componentOut = new Out("Out"+numInstance++);
    private static int numInstance = 0;

    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void connectIn(){
        ConnectionManager.makeDestination(componentOut.getIInput());
    }


}
