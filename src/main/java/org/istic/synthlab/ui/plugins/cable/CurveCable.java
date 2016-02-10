package org.istic.synthlab.ui.plugins.cable;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ObservableSetValue;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;

/**
 * @author augustin
 */
public class CurveCable extends CubicCurve {

    /**
     * Returns an instance of CubicCurve
     *
     * @param startX    position X of the starting point
     * @param startY    position Y of the starting point
     * @param endX      position X of the ending point
     * @param endY      position Y of the ending point
     */
    public CurveCable(double startX,
                      double startY,
                      double endX,
                      double endY) {
        this(
                startX, startY,
                endX, endY,
                Color.FORESTGREEN);
    }

    /**
     * Returns an instance of CubicCurve with a custom color
     *
     * @param startX    position X of the starting point
     * @param startY    position Y of the starting point
     * @param endX      position X of the ending point
     * @param endY      position Y of the ending point
     * @param color     color of the CubicCurve object
     */
    public CurveCable(double startX,
                      double startY,
                      double endX,
                      double endY,
                      Color color) {
        super(
                startX,
                startY,
                startX + (startX % 100),
                startY + (startY % 100),
                endX - (endX % 100),
                endY - (endY % 100),
                endX,
                endY
        );
        setStrokeWidth(7.5);
        setStrokeLineCap(StrokeLineCap.ROUND);
        setStroke(color);
        setFill(Color.TRANSPARENT);
        autosize();
    }
}
