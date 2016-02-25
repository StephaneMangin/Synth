package org.istic.synthlab.ui.plugins.cable;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;

/**
 * Manage cable insertion and linking.
 *
 * @author Augustion Bardou <>
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 * @author Thibaut Rousseau <thibaut.rousseau@outlook.com>
 */
public class CurveCable extends CubicCurve {
    private Color color;

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
        strokeProperty().set(color);
    }

    private Node startNode;
    private Node endNode;

    public Node getStartNode() {
        return startNode;
    }

    public void setStartNode(final Node startNode) {
        this.startNode = startNode;

        // Modify the coordinates of the curve as the node moves
        startNode.getParent().layoutXProperty().addListener((observable, oldValue, newValue) -> {
            setStartX(computeCoordinates(startNode).getX());
        });

        startNode.getParent().layoutYProperty().addListener((observable, oldValue, newValue) -> {
            setStartY(computeCoordinates(startNode).getY());
        });
    }

    public Node getEndNode() {
        return endNode;
    }

    public void setEndNode(Node endNode) {
        this.endNode = endNode;

        // Modify the coordinates of the curve as the node moves
        endNode.getParent().layoutXProperty().addListener((observable, oldValue, newValue) -> {
            setEndX(computeCoordinates(endNode).getX());
        });

        endNode.getParent().layoutYProperty().addListener((observable, oldValue, newValue) -> {
            setEndY(computeCoordinates(endNode).getY());
        });
    }

    private static Point2D computeCoordinates(final Node node) {
        double x = node.getParent().getBoundsInParent().getMinX() + node.getBoundsInParent().getMinX(),
               y = node.getParent().getBoundsInParent().getMinY() + node.getBoundsInParent().getMinY();

        x += node.getBoundsInParent().getWidth()/2;
        y += node.getBoundsInParent().getHeight()/2;

        return new Point2D(x, y);
    }

    public CurveCable(final Node startNode, final Node endNode) {
        this(computeCoordinates(startNode), computeCoordinates(endNode));
        setStartNode(startNode);
        setEndNode(endNode);
    }

    private CurveCable(final Point2D start, final Point2D end) {
        this(start.getX(), start.getY(), end.getX(), end.getY());
    }

    /**
     * Returns an instance of CubicCurve with a custom color
     *
     * @param startX    position X of the starting point
     * @param startY    position Y of the starting point
     * @param endX      position X of the ending point
     * @param endY      position Y of the ending point
     */
    private CurveCable(final double startX, final double startY, final double endX, final double endY) {
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

        // Modify the control points as the coordinate of the curve change
        startXProperty().addListener((observable, oldValue, newValue) -> {
            setControlX1(newValue.doubleValue() + newValue.doubleValue() % 100);
        });

        startYProperty().addListener((observable, oldValue, newValue) -> {
            setControlY1(newValue.doubleValue() + newValue.doubleValue() % 100);
        });

        endXProperty().addListener((observable, oldValue, newValue) -> {
            setControlX2(newValue.doubleValue() - newValue.doubleValue() % 100);
        });

        endYProperty().addListener((observable, oldValue, newValue) -> {
            setControlY2(newValue.doubleValue() - newValue.doubleValue() % 100);
        });

        setStrokeWidth(7.5);
        setStrokeLineCap(StrokeLineCap.ROUND);
        setFill(Color.TRANSPARENT);
        setColor(Color.RED);
        setEffect(new InnerShadow());
        autosize();
    }
}
