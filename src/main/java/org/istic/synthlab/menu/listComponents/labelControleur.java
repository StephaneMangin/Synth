package org.istic.synthlab.menu.listComponents;

import javafx.fxml.FXML;
import javafx.scene.shape.Circle;
import org.istic.synthlab.core.IHMConnectionManager;

/**
 * @author Sebastien
 */
public class labelControleur {
    @FXML
    Circle in1;

    @FXML
    void connectIn(){
        IHMConnectionManager.connect(in1.getId());
    }
}
