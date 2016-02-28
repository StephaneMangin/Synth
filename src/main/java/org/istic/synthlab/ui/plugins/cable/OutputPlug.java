package org.istic.synthlab.ui.plugins.cable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.ui.plugins.ComponentPane;

import java.io.IOException;

/**
 * Allow direct insertion of a output plug inside a node
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class OutputPlug extends Pane {

    @FXML
    private Label title;

    private IOutput output;

    private CurveCable cable;
    private ComponentPane componentPane;

    /**
     * This constructor loads the FXML associated to the potentiometer, binds the event handlers and initializes the view
     */
    public OutputPlug() {
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/plugs/output.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
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
    public IOutput getOutput() {
        return output;
    }

    public void setOutput(IOutput output) {
        this.output = output;
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
