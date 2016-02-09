package org.istic.synthlab.ui.controls;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
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
public class Potentiometer extends Pane {

    @FXML
    public Button rotatorDial;

    @FXML
    public Button rotatorHandle;

    private static final double HEIGHT = 36;
    private static final double WIDTH = 36;

    private double rotation;

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

    // JavaFX has a weird behavior, see docs/knob.png to check how the angles look like on the circle
    // The MIN_ANGLE is bigger than the MAX_ANGLE because the upper part of the circle is negative
    public static final double MIN_ANGLE = 120;
    public static final double MAX_ANGLE = 420;

    public Potentiometer() {
        super();
        setPrefHeight(HEIGHT);
        setPrefWidth(WIDTH);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/controls/potentiometer.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        rotatorDial.setOnMouseEntered(new MaximiseEventHandler());
        rotatorHandle.setOnMouseEntered(new MaximiseEventHandler());
        rotatorDial.setOnMouseDragged(new ClickKnobEventHandler());
        rotatorDial.setOnScroll(new ScrollKnobEventHandler());
    }

    private class MaximiseEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(final MouseEvent event) {
            rotatorDial.setScaleX(1.8);
            rotatorDial.setScaleY(1.8);
            rotatorHandle.setScaleX(1.8);
            rotatorHandle.setScaleY(1.8);
            rotatorDial.setOnMouseExited(new MinimizeEventHandler());
            rotatorHandle.setOnMouseExited(new MinimizeEventHandler());
            event.consume();
        }
    }

    private class MinimizeEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(final MouseEvent event) {
            rotatorDial.setScaleX(1);
            rotatorDial.setScaleY(1);
            rotatorHandle.setScaleX(1);
            rotatorHandle.setScaleY(1);
            event.consume();
        }
    }

    private class ScrollKnobEventHandler implements EventHandler<ScrollEvent> {
        @Override
        public void handle(final ScrollEvent event) {
            if ((int) Math.signum(event.getDeltaY()) > 0) {
                rotation += 5;
            } else {
                rotation -= 5;
            }
            setRotation(rotation);
            event.consume();
        }
    }

    private class ClickKnobEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(final MouseEvent event) {
            // Where did we click?
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

            System.out.println("(" + x + " ; " + y + ")");

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
            setRotation(Math.toDegrees(Math.atan2(y, x)));
            event.consume();
        }
    }

    private void setRotation(double value) {
        if (rotation <= MIN_ANGLE) {
            rotation = MIN_ANGLE;
        } else if (rotation >= MAX_ANGLE) {
            rotation = MAX_ANGLE;
        }
        rotatorHandle.setRotate(rotation);
    }
}
