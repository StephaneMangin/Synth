package org.istic.synthlab.ui.plugins.cable;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

/**
 * @author augustin <augustin.bardou@gmail.com>
 */
public class BoundLine extends Line {
    public BoundLine(ReadOnlyDoubleProperty startX, ReadOnlyDoubleProperty startY, ReadOnlyDoubleProperty endX, ReadOnlyDoubleProperty endY) {
        startXProperty().bind(startX);
        startYProperty().bind(startY);
        endXProperty().bind(endX);
        endYProperty().bind(endY);
        setStrokeWidth(2);
        setStroke(Color.BLACK.deriveColor(0, 1, 1, 1));
        setStrokeLineCap(StrokeLineCap.ROUND);
        setMouseTransparent(true);
    }
}
