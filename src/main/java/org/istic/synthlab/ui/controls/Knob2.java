package org.istic.synthlab.ui.controls;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Button;

public class Knob2 extends Button {

    private final DoubleProperty value = new SimpleDoubleProperty(0);
    private static final double HEIGHT = 36;
    private static final double WIDTH = 36;

    public Knob2() {
        super();
        setPrefHeight(HEIGHT);
        setPrefWidth(WIDTH);
        getStylesheets().add("/ui/stylesheets/components.css");
        getStyleClass().add("rotation-dial");
        setOnMouseClicked(event -> {
            // Where did we click?
            double x = event.getX(), y = event.getY();

            // Out of bounds values are possible
            // They might break so we normlize them
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
            final double degrees = Math.toDegrees(Math.atan2(y, x));
            System.out.println(degrees);
            setRotate(degrees);
        });
    }

    @SuppressWarnings("unused")
    public DoubleProperty valueProperty() {
        return value;
    }

    public double getValue() {
        return value.get();
    }

    public void setValue(final double newValue) {
        value.set(newValue);
    }
}
