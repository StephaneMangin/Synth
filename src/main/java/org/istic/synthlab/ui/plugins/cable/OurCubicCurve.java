package org.istic.synthlab.ui.plugins.cable;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;

/**
 * @author augustin
 */
public class OurCubicCurve extends CubicCurve {
    /**
     * Empty constructor
     */
    public OurCubicCurve() {
        super();
        setStroke(Color.FORESTGREEN);
        setStrokeWidth(4);
        setStrokeLineCap(StrokeLineCap.ROUND);
    }

    /**
     * Constructor without the color option
     * @param startX position X of the starting point
     * @param startY position Y of the starting point
     * @param controlX1 position X of the control point
     * @param controlY1 position Y of the control point
     * @param controlX2 position X of the second control point
     * @param controlY2 position Y of the second control point
     * @param endX position X of the ending point
     * @param endY position Y of the ending point
     */
    public OurCubicCurve(ReadOnlyDoubleProperty startX, ReadOnlyDoubleProperty startY, ReadOnlyDoubleProperty controlX1, ReadOnlyDoubleProperty controlY1, ReadOnlyDoubleProperty controlX2, ReadOnlyDoubleProperty controlY2, ReadOnlyDoubleProperty endX, ReadOnlyDoubleProperty endY) {
        startXProperty().bind(startX);
        endXProperty().bind(endX);
        startYProperty().bind(startY);
        endYProperty().bind(endY);
        controlX1Property().bind(controlX1);
        controlY1Property().bind(controlY1);
        controlX2Property().bind(controlX2);
        controlY2Property().bind(controlY2);
        setStroke(Color.FORESTGREEN);
        setStrokeWidth(4);
        setStrokeLineCap(StrokeLineCap.ROUND);
    }

    public OurCubicCurve(ReadOnlyDoubleProperty startX, ReadOnlyDoubleProperty startY, ReadOnlyDoubleProperty endX, ReadOnlyDoubleProperty endY) {
        startXProperty().bind(startX);
        endXProperty().bind(startY);
        startYProperty().bind(endX);
        endYProperty().bind(endY);
        setStroke(Color.FORESTGREEN);
        setStrokeWidth(7.5);
        setStrokeLineCap(StrokeLineCap.ROUND);
        setFill(Color.TRANSPARENT);
        setControlX1(startXProperty().getValue() + startXProperty().getValue()%100);
        setControlY1(startYProperty().getValue() - startYProperty().getValue()%100);
        setControlX2(endXProperty().getValue() + endXProperty().getValue()%150);
        setControlY2(endYProperty().getValue() + endYProperty().getValue()%150);
        autosize();
    }


    /**
     * Constructor with the color option
     * @param startX position X of the starting point
     * @param startY position Y of the starting point
     * @param controlX1 position X of the control point
     * @param controlY1 position Y of the control point
     * @param controlX2 position X of the second control point
     * @param controlY2 position Y of the second control point
     * @param endX position X of the ending point
     * @param endY position Y of the ending point
     * @param color color of the CubicCurve object
     */
    public OurCubicCurve(double startX, double startY, double controlX1, double controlY1, double controlX2, double controlY2, double endX, double endY, Color color) {
        super(startX, startY, controlX1, controlY1, controlX2, controlY2, endX, endY);
        setStroke(color);
        setStrokeWidth(4);
        setStrokeLineCap(StrokeLineCap.ROUND);
    }
}
