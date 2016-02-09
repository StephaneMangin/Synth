package org.istic.synthlab.ui.controls;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * @author Thibaut Rousseau <thibaut.rousseau@outlook.com>
 */
public class Knob2 extends Pane {
    @FXML
    private Button rotatorDial;
    @FXML
    private Button rotatorHandle;

    private final DoubleProperty value = new SimpleDoubleProperty(0);
    private static final double HEIGHT = 36;
    private static final double WIDTH = 36;
    private static final double MIN = -140;
    private static final double MAX = 140;

    /**
     * This constructor loads the FXML associated to the potentiometer, binds the event handlers and initializes the view
     */
    public Knob2() {
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/controls/knob2.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }

        setPrefHeight(HEIGHT);
        setPrefWidth(WIDTH);
        setRotate(-90); // Because we want 0 to be at the bottom to simplify some calculations
        rotatorDial.setOnMouseDragged(new DragKnobEventHandler());
        rotatorDial.setOnScroll(new ScrollKnobEventHandler());
        rotateHandle(MIN);
    }

    public DoubleProperty valueProperty() {
        return value;
    }

    public double getValue() {
        return value.get();
    }

    public void setValue(final double newValue) {
        value.set(newValue);
    }

    private class ScrollKnobEventHandler implements EventHandler<ScrollEvent> {
        @Override
        public void handle(final ScrollEvent event) {
            rotateHandle(rotatorHandle.getRotate() + 10 * Math.signum(event.getDeltaY()));
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
            // Actually this schema is virtually rotated but it doesn't change anything
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
            final double degrees = Math.toDegrees(Math.atan2(y, x));
            rotateHandle(degrees);
        }
    }

    /**
     * Rotate the handle by taking in consideration the MIN and MAX values
     * @param degrees The angle to which the handle will be rotated, relative to the start position, not the current one
     */
    private void rotateHandle(final double degrees) {
        if (degrees >= MIN && degrees <= MAX) {
            rotatorHandle.setRotate(degrees);
        }
    }
}
