package org.istic.synthlab.components.vcoa;

import javafx.fxml.FXML;
import javafx.scene.shape.Circle;
import org.istic.synthlab.core.AbstractController;
import org.istic.synthlab.core.IHMConnectionManager;

/**
 * Created by stephane on 02/02/16.
 */
public class Controller extends AbstractController {
    @FXML
    Circle in;

    @FXML
    void connectIn(){
        IHMConnectionManager.connect(in.getId());
    }
}
