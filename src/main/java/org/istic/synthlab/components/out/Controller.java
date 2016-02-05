package org.istic.synthlab.components.out;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.istic.synthlab.core.AbstractController;
import org.istic.synthlab.core.ui.ConnectionManager;
import org.istic.synthlab.core.ui.CoreController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by stephane on 02/02/16.
 */
public class Controller extends AbstractController implements Initializable {

    public Out componentOut = new Out("Out"+numInstance++);
    private static int numInstance = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        componentOut.getLineOut().start();
    }

    @FXML
    void connectIn(){
        ConnectionManager.makeDestination(componentOut.getIInput());
    }


}
