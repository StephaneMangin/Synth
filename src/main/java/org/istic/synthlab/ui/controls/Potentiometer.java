package org.istic.synthlab.ui.controls;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import javax.xml.bind.annotation.XmlAttribute;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by blacknight on 09/02/16.
 */
public class Potentiometer extends Pane implements MouseWheelListener {

    @FXML
    public Button rotatorDial;

    @FXML
    public Button rotatorHandle;

    private final DoubleProperty value = new SimpleDoubleProperty(0);

    // JavaFX has a weird behavior, see docs/knob.png to check how the angles look like on the circle
    // The MIN_ANGLE is bigger than the MAX_ANGLE because the upper part of the circle is negative
    public static final double MIN_ANGLE = 120;
    public static final double MAX_ANGLE = 60;

    public Potentiometer() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/controls/potentiometer.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        rotatorHandle.setRotate(MIN_ANGLE);
        value.set(angleToValue(MIN_ANGLE));
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
    public double angleToValue(double degree) {
        if (degree >= 120 && degree < 180) {
            degree -= 120;
        }
        else {
            degree += 240;
        }
        return degree / 300;
    }

    public double valueToAngle(double value) {
        if (value < 0 || value > 1) {
            throw new IllegalArgumentException("The value must be in the range [0 ; 1]");
        }

        value *= 300;
        if (value > 60) {
            value -= 240;
        }
        else {
            value += 120;
        }

        return value;
    }

    public double getValue() {
        return value.get();
    }

    public DoubleProperty valueProperty() {
        return value;
    }

    public void setValue(double newValue) {
        value.set(newValue);
        rotatorHandle.setRotate(valueToAngle(newValue));
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        System.out.println("Wheel detected");
    }

}
