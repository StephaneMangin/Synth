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
     * @param startX
     * @param startY
     * @param controlX1
     * @param controlY1
     * @param controlX2
     * @param controlY2
     * @param endX
     * @param endY
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
        endXProperty().bind(endX);
        startYProperty().bind(startY);
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
     * @param startX
     * @param startY
     * @param controlX1
     * @param controlY1
     * @param controlX2
     * @param controlY2
     * @param endX
     * @param endY
     * @param color
     */
    public OurCubicCurve(double startX, double startY, double controlX1, double controlY1, double controlX2, double controlY2, double endX, double endY, Color color) {
        super(startX, startY, controlX1, controlY1, controlX2, controlY2, endX, endY);
        setStroke(color);
        setStrokeWidth(4);
        setStrokeLineCap(StrokeLineCap.ROUND);
    }
}
