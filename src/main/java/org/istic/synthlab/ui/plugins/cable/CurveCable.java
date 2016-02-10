package org.istic.synthlab.ui.plugins.cable;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableSetValue;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;

/**
 * @author augustin
 */
public class CurveCable extends CubicCurve {

    private Color color;

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
        setFill(Color.TRANSPARENT);
        setColor(color);
        autosize();
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
        strokeProperty().set(color);
    }
}
