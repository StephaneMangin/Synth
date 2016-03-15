package org.istic.synthlab.ui.plugins.controls;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Allow direct insertion into a fxml file
 *
 * Example :
 *      <Potentiometer fx-id:="potentiometer" />
 *
 * @author Thibaut Rousseau <thibaut.rousseau@outlook.com>
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class CloseIcon extends Pane {

    /**
     * This constructor loads the FXML associated to the icon
     */
    public CloseIcon() {
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/controls/closeIcon.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
