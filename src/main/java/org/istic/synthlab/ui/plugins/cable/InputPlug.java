package org.istic.synthlab.ui.plugins.cable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.ui.plugins.ComponentPane;

import java.io.IOException;

/**
 * Allow direct insertion of a input plug inside a node
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class InputPlug extends Pane {

    private IInput input;
    @FXML
    private Label title;

    private CurveCable cable;

    /**
     * This constructor loads the FXML associated to the potentiometer, binds the event handlers and initializes the view
     */
    public InputPlug() {
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/plugs/input.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
        setOnMouseClicked(event -> {
            if (hasCable()) {
                cable.deconnectInputPlug();
            }
        });
    }
    /***************************************************************************
     *                                                                         *
     * Properties                                                              *
     *                                                                         *
     **************************************************************************/
    /**
     * The text to display in the label. The text may be null.
     */
    public final StringProperty textProperty() {
        if (text == null) {
            text = new SimpleStringProperty(this, "text", "");
        }
        return text;
    }
    private StringProperty text;
    public final void setText(String value) {
        textProperty().setValue(value);
        title.setText(value);
    }
    public final String getText() { return text == null ? "" : text.getValue(); }

    public IInput getInput() {
        return input;
    }

    public void setInput(IInput input) {
        this.input = input;
    }

    public CurveCable getCable() {
        return cable;
    }

    public void setCable(CurveCable cable) {
        this.cable = cable;
    }

    /**
     * Returns true if a cable is connected
     *
     * @return
     */
    public boolean hasCable() {
        return cable != null;
    }

    public ComponentPane getComponentPane() {
        return (ComponentPane) getParent();
    }
}
