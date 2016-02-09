package org.istic.synthlab.ui.controls;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Knob implements Initializable {
    @FXML
    public Button rotatorDial;
    @FXML
    public Button rotatorHandle;

    private DoubleProperty value;

    // JavaFX has a weird behavior, see docs/knob.png to check how the angles look like on the circle
    // The MIN_ANGLE is bigger than the MAX_ANGLE because the upper part of the circle is negative
    private static final double MIN_ANGLE = 120;
    private static final double MAX_ANGLE = 60;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        rotatorHandle.setRotate(MIN_ANGLE);
        value = new SimpleDoubleProperty(angleToValue(MIN_ANGLE));
    }

    @FXML
    public void rotatorDragged(final MouseEvent event) {
        final Parent p = rotatorDial.getParent();
        final Bounds b = rotatorDial.getLayoutBounds();
        final Double centerX = b.getMinX() + (b.getWidth() / 2), centerY = b.getMinY() + (b.getHeight() / 2);
        final Point2D center = p.localToParent(centerX, centerY);
        final Point2D mouse = p.localToParent(event.getX(), event.getY());
        final Double deltaX = mouse.getX() - center.getX(), deltaY = mouse.getY() - center.getY();
        final Double radians = Math.atan2(deltaY, deltaX);
        rotate(Math.toDegrees(radians));
    }

    private void rotate(double degrees) {
        if (degrees >= 90 && degrees < MIN_ANGLE) {
            degrees = MIN_ANGLE;
        }
        if (degrees < 90 && degrees > MAX_ANGLE) {
            degrees = MAX_ANGLE;
        }
        rotatorHandle.setRotate(degrees);
        value.set(angleToValue(degrees));
    }

    /**
     * Return a value between 0 and 1 for a given angle for this button
     * @param degree The angle to convert to a value
     * @return A value between 0 and 1 for a given angle for this button
     */
    private double angleToValue(final double degree) {
        final int totalRange = 300;
        if (degree >= 120 && degree < 180) {
            return (degree - 120) / totalRange;
        }
        else {
            return (degree + 240) / totalRange;
        }
    }

    // TODO
    private double valueToAngle(double value) {
        if (value < 0) {
            value = 0;
        }
        else if (value > 1) {
            value = 1;
        }



        return 0.;
    }

    @FXML
    public void rotatorPressed(MouseEvent event) {
        rotatorDragged(event);
    }

    @FXML
    public void rotatorReleased(Event event) {

    }

    public double getValue() {
        return value.get();
    }

    public DoubleProperty valueProperty() {
        return value;
    }

    public void setValue(double newValue) {
        value.set(newValue);
        // TODO: change the angle
    }
}
