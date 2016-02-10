package org.istic.synthlab.ui.plugins.cable;

import javafx.scene.paint.Color;
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
                startX + (startX % 100),    //controlX1 variable
                startY + (startY % 100),    //controlY1 variable
                endX - (endX % 100),        //controlX2 variable
                endY - (endY % 100),        //controlY2 variable
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
