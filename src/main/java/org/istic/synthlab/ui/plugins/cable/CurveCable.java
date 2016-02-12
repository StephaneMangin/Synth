package org.istic.synthlab.ui.plugins.cable;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;

/**
 * Manage cable insertion and linking.
 *
 * @author Augustion Bardou <>
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class CurveCable extends CubicCurve {

    // Keep the color to override setter
    private Color color;

    public CurveCable(final Point2D start, final Point2D end) {
        this(start.getX(), start.getY(), end.getX(), end.getY());
    }

    /**
     * Returns an instance of CubicCurve
     *
     * @param startX    position X of the starting point
     * @param startY    position Y of the starting point
     * @param endX      position X of the ending point
     * @param endY      position Y of the ending point
     */
    public CurveCable(final double startX, final double startY, final double endX, final double endY) {
        this(startX, startY, endX, endY, Color.BLACK);
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
    public CurveCable(final double startX, final double startY, final double endX, final double endY, final Color color) {
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

    public void reCenter(double x, double y){
        this.setStartX(this.getStartX() - x);
        this.setEndX(this.getEndX() - x);
        this.setStartY(this.getStartY() - y);
        this.setEndY(this.getEndY() - y);
        this.setControlX1(this.getControlX1() - x);
        this.setControlX2(this.getControlX2() - x);
        this.setControlY1(this.getControlY1() - y);
        this.setControlY2(this.getControlY2() - y);
    }

}
