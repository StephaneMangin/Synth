package org.istic.synthlab.ui.plugins.controls;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import net.minidev.json.JSONObject;
import org.istic.synthlab.ui.CoreController;
import org.istic.synthlab.ui.history.Origin;
import org.istic.synthlab.ui.history.State;
import org.istic.synthlab.ui.history.StateType;

import java.io.IOException;

/**
 * Allow direct insertion into a fxml file
 *
 * Example :
 *      <Potentiometer fx:id="potentiometer" />
 *
 * @author Thibaut Rousseau <thibaut.rousseau@outlook.com>
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class Potentiometer extends Pane implements Origin {

    private static final double MINIMUM_STEP = 2;

    @Override
    public String getName() {
        return title.getText();
    }

    @Override
    public void setName(String name) {
        title.setText(name);
    }

    @FXML
    private Label title;
    @FXML
    private Label maxValue;
    @FXML
    private Label minValue;
    @FXML
    private Button rotatorDial;
    @FXML
    private Button rotatorHandle;

    private static final double HEIGHT = 36;
    private static final double WIDTH = 36;
    private static final double MIN = -230.0D;
    private static final double MAX = 50.0D;

    private final DoubleProperty value = new SimpleDoubleProperty(0);

    public DoubleProperty valueProperty() {
        return value;
    }

    public double getValue() {
        return value.get();
    }

    public void setValue(final double newValue) {
        value.set(newValue);
    }

    /**
     * This constructor loads the FXML associated to the potentiometer, binds the event handlers and initializes the view
     */
    public Potentiometer() {
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/controls/potentiometer.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }

        valueProperty().addListener((observable, oldValue, newValue) -> {
            rotateHandle(convertFromValue(newValue.doubleValue()));
            CoreController.getConnectionManager().getHistory().add(this, StateType.CHANGED);
        });
        setPrefHeight(HEIGHT);
        setPrefWidth(WIDTH);
        rotatorDial.setOnMouseDragged(new DragKnobEventHandler());
        rotatorDial.setOnScroll(new ScrollKnobEventHandler());
        rotateHandle(MIN);
    }

    @Override
    public void setJson(JSONObject state) {
        state.forEach((s, o) -> {
            switch (s) {
                case "rotation":
                    if (o instanceof java.math.BigDecimal) {
                        rotateHandle(((java.math.BigDecimal)o).doubleValue());
                    } else if (o instanceof Double){
                        rotateHandle((Double)o);
                    }
                    break;
                default:
                    // Do nothing yet
            }
        });
    }

    @Override
    public JSONObject getJson() {
        JSONObject obj = new JSONObject();
        obj.put("rotation", getRotationAngle());
        obj.put("id", getId());
        obj.put("name", getName());
        obj.put("componentId", getParent().getId());
        obj.put("type", "potentiometer");
        System.out.println(obj);
        return obj;
    }

    @Override
    public State getState() {
        return new State(this);
    }

    @Override
    public void restoreState(State state) {
        setJson(state.getContent());
    }

    private class ScrollKnobEventHandler implements EventHandler<ScrollEvent> {
        @Override
        public void handle(final ScrollEvent event) {
            rotateHandle(rotatorHandle.getRotate() + MINIMUM_STEP * Math.signum(event.getDeltaY()));
            event.consume();
        }
    }

    private class DragKnobEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(final MouseEvent event) {
            double x = event.getX(), y = event.getY();

            // Out of bounds values are possible
            // They might break something so we normalize them
            if (x < 0) {
                x = 0;
            }
            else if (x > WIDTH) {
                x = WIDTH;
            }

            if (y < 0) {
                y = 0;
            }
            else if (y > HEIGHT) {
                y = HEIGHT;
            }

            // Move the coordinates so that next calculations are easier
            x -= WIDTH/2;
            y -= HEIGHT/2;

            // Calculate the angle between (x ; y) and the center of the circle
            //
            //        |
            //   - -  |  + -
            //        |
            // ------ 0 ------ x
            //        |
            //   - +  |  + +
            //        |
            //        y
            //
            // tan(x, y) = y/x (ie. opposite/adjacent)
            rotateHandle(Math.toDegrees(Math.atan2(y, x)));
            event.consume();
        }
    }

    /**
     * Rotate the handle by taking in consideration the MIN and MAX values
     *
     * @param degrees The angle to which the handle will be rotated, relative to the activate position, not the current one
     */
    private void rotateHandle(final double degrees) {
        if (degrees >= MIN && degrees <= MAX) {
            rotatorHandle.setRotate(degrees);
            setValue(convertFromDegrees(degrees));
        }
    }

    /**
     * Return the angle of the circular rotation of this potentiometer
     *
     * @return
     */
    private double getRotationAngle() {
        return rotatorHandle.getRotate();
    }

    public void setTitle(final String value) {
        title.setText(value);
    }

    public void setMaxValue(final double value) {
        maxValue.setText(Double.toString(value));
    }

    public void setMinValue(final double value) {
        minValue.setText(Double.toString(value));
    }

    public void setMaxValue(final String value) {
        maxValue.setText(value);
    }

    public void setMinValue(final String value) {
        minValue.setText(value);
    }

    /**
     * Return the related value from potentiometer angle
     */
    private double convertFromDegrees(final double degrees) {
        return (degrees - MIN) / (MAX - MIN);
    }

    /**
     * Return the related angle from potentiometer value
     */
    private double convertFromValue(final double value) {
        return (value * (MAX-MIN)) + MIN;
    }
}
