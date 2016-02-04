package org.istic.synthlab.components.vcoa;

import javafx.fxml.FXML;
import javafx.scene.shape.Circle;
import org.istic.synthlab.core.AbstractController;
import org.istic.synthlab.core.IHMConnectionManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by stephane on 02/02/16.
 */
public class Controller extends AbstractController {
    @FXML
    Circle input;
    private static int numInstance = 0;
    private Vcoa composantVcoa = new Vcoa("VCOA"+numInstance++);;

    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    void connectIn(){
        IHMConnectionManager.makeOrigin(composantVcoa.getOutput());
    }
}
