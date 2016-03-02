package org.istic.synthlab.ui.plugins.plug;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.istic.synthlab.ui.plugins.workspace.ComponentPane;
import org.istic.synthlab.ui.plugins.workspace.CurveCable;

import java.io.IOException;

/**
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public abstract class AbstractPlug extends Pane implements Plug {

    @FXML
    protected Label title;

    protected CurveCable cable;
    protected ComponentPane componentPane;

    /**
     * This constructor loads the FXML associated to the potentiometer, binds the event handlers and initializes the view
     */
    public AbstractPlug(String type) {
        assert type.equals("output") || type.equals("input");
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/plugs/" + type + ".fxml"));
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

    @Override
    public final void setText(String value) {
        textProperty().setValue(value);
        title.setText(value);
    }
    @Override
    public final String getText() { return text == null ? "" : text.getValue(); }

    @Override
    public CurveCable getCable() {
        return cable;
    }

    @Override
    public void setCable(CurveCable cable) {
        this.cable = cable;
    }

    @Override
    public boolean hasCable() {
        return cable != null;
    }

    @Override
    public ComponentPane getComponentPane() {
        return (ComponentPane) getParent();
    }
}
