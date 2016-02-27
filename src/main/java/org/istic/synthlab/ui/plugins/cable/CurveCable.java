package org.istic.synthlab.ui.plugins.cable;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;
import net.minidev.json.JSONObject;
import org.istic.synthlab.ui.CoreController;
import org.istic.synthlab.ui.plugins.ComponentPane;
import org.istic.synthlab.ui.plugins.history.State;
import org.istic.synthlab.ui.plugins.history.Origin;

/**
 * Manage cable insertion and linking.
 *
 * @author Augustion Bardou <>
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 * @author Thibaut Rousseau <thibaut.rousseau@outlook.com>
 */
public class CurveCable extends CubicCurve implements Origin {

    // Keep the color to override setter
    private Color color;

    private Node startNode;
    private Node endNode;
    private String name;

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
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setJson(JSONObject state) {

        state.forEach((s, o) -> {
            ComponentPane componentPane;
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
                case "id":
                    setId((String)o);
                    break;
                case "name":
                    setName((String)o);
                    break;
                case "fill":
                    setFill(Color.valueOf((String)o));
                    break;
                case "stroke":
                    setStroke(Color.valueOf((String)o));
                    break;
                default:
                    // Do nothing yet
            }
        });
    }

    @Override
    public JSONObject getJson() {
        JSONObject obj = new JSONObject();
        obj.put("startX", getStartX());
        obj.put("startY", getEndX());
        obj.put("endX", getStartY());
        obj.put("endY", getEndY());
        obj.put("fill", getFill().toString());
        obj.put("stroke", getStroke().toString());
        obj.put("type", "cable");
//        obj.put("startMethod", startNode != null ? startNode.getProperties().get("onMouseClicked") : null);
//        obj.put("endMethod", endNode != null ? endNode.getProperties().get("onMouseClicked") : null);
//        obj.put("startComponantId", startNode != null ? startNode.getParent().getId() : endNode.getParent().getId());
//        obj.put("endComponantId", endNode != null ? endNode.getParent().getId() : endNode.getParent().getId());
//        obj.put("name", startNode != null ? startNode.getId() : endNode.getId());
        obj.put("id", getId());
        return obj;
    }

    @Override
    public State getState() {
        return new State(this);
    }

    @Override
    public void restoreState(State state) {
        setJson(state.getContent());
    }

}
