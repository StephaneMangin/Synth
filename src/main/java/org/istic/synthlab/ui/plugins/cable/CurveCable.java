package org.istic.synthlab.ui.plugins.cable;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.effect.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;
import net.minidev.json.JSONObject;
import org.istic.synthlab.ui.ConnectionManager;
import org.istic.synthlab.ui.plugins.history.State;
import org.istic.synthlab.ui.plugins.history.IOrigin;

import java.util.ArrayList;
import java.util.List;

/**
 * Manage cable insertion and linking.
 *
 * @author Augustion Bardou <>
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class CurveCable extends CubicCurve implements IOrigin, Observable {

    // Keep the color to override setter
    private Color color;

    private Node startNode;
    private Node endNode;
    private final List<InvalidationListener> observers = new ArrayList<>();

    public CurveCable(final Node start, final Node end) {
        this(computeCoordinates(start), computeCoordinates(end));
        setStartNode(start);
        setEndNode(end);
    }

    public Node getStartNode() {
        return startNode;
    }

    public void setStartNode(final Node startNode) {
        this.startNode = startNode;

        startNode.getParent().layoutXProperty().addListener((observable, oldValue, newValue) -> {
            setStartX(computeCoordinates(startNode).getX());
            notifyAll();
        });

        startNode.getParent().layoutYProperty().addListener((observable, oldValue, newValue) -> {
            setStartY(computeCoordinates(startNode).getY());
            notifyAll();
        });
    }

    public Node getEndNode() {
        return endNode;
    }

    public void setEndNode(Node endNode) {
        this.endNode = endNode;

        endNode.getParent().layoutXProperty().addListener((observable, oldValue, newValue) -> {
            setEndX(computeCoordinates(endNode).getX());
            notifyAll();
        });

        endNode.getParent().layoutYProperty().addListener((observable, oldValue, newValue) -> {
            setEndY(computeCoordinates(endNode).getY());
            notifyAll();
        });
    }

    private static Point2D computeCoordinates(final Node node) {
        double x = node.getParent().getBoundsInParent().getMinX() + node.getBoundsInParent().getMinX(),
                y = node.getParent().getBoundsInParent().getMinY() + node.getBoundsInParent().getMinY();

        x += node.getBoundsInParent().getWidth()/2;
        y += node.getBoundsInParent().getHeight()/2;

        return new Point2D(x, y);
    }

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

        startXProperty().addListener((observable, oldValue, newValue) -> {
            setControlX1(newValue.doubleValue() + newValue.doubleValue() % 100);
            notifyAll();
        });

        startYProperty().addListener((observable, oldValue, newValue) -> {
            setControlY1(newValue.doubleValue() + newValue.doubleValue() % 100);
            notifyAll();
        });

        endXProperty().addListener((observable, oldValue, newValue) -> {
            setControlX2(newValue.doubleValue() - newValue.doubleValue() % 100);
            notifyAll();
        });

        endYProperty().addListener((observable, oldValue, newValue) -> {
            setControlY2(newValue.doubleValue() - newValue.doubleValue() % 100);
            notifyAll();
        });

        setStrokeWidth(7.5);
        setStrokeLineCap(StrokeLineCap.ROUND);
        setFill(Color.TRANSPARENT);
        setColor(color);
        autosize();
        this.setEffect(new InnerShadow());
    }

    public Color getColor(){
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

    @Override
    public void setJson(JSONObject state) {
        state.forEach((s, o) -> {
            switch(s) {
                case "startX":
                    setStartX((double)o);
                    break;
                case "startY":
                    setStartX((double)o);
                    break;
                case "endX":
                    setStartX((double)o);
                    break;
                case "endY":
                    setStartX((double)o);
                    break;
                default:
                    // Do nothing yet
            }
        });
    }

    @Override
    public JSONObject getJson() {
        StringBuffer buffer = new StringBuffer();
        JSONObject obj = new JSONObject();
        obj.put("startX", getStartX());
        obj.put("startY", getEndX());
        obj.put("endX", getStartY());
        obj.put("endY", getEndY());
        return obj;
    }

    @Override
    public State save() {
        return new State(this);
    }

    @Override
    public void restore(State state) {
        setJson(state.getContent());
    }

    @Override
    public void addListener(InvalidationListener listener) {
        observers.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        observers.remove(listener);
    }
}
