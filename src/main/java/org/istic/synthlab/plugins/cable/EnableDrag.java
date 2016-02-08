package org.istic.synthlab.plugins.cable;

import javafx.scene.Cursor;
import javafx.scene.shape.Circle;

/**
 * @author augustin
 */
public class EnableDrag {
    private class Delta { double x, y; }
    public void enableDrag(Circle circle) {
        final Delta dragDelta = new Delta();
        circle.setOnMousePressed(mouseEvent -> {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = circle.getCenterX() - mouseEvent.getX();
            dragDelta.y = circle.getCenterY() - mouseEvent.getY();
            circle.getScene().setCursor(Cursor.MOVE);
        });
        circle.setOnMouseReleased(mouseEvent -> circle.getScene().setCursor(Cursor.HAND));
        circle.setOnMouseDragged(mouseEvent -> {
            double newX = mouseEvent.getX() + dragDelta.x;
            if (newX > 0 && newX < circle.getScene().getWidth()) {
                circle.setCenterX(newX);
            }
            double newY = mouseEvent.getY() + dragDelta.y;
            if (newY > 0 && newY < circle.getScene().getHeight()) {
                circle.setCenterY(newY);
            }
        });
        circle.setOnMouseEntered(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                circle.getScene().setCursor(Cursor.HAND);
            }
        });
        circle.setOnMouseExited(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                circle.getScene().setCursor(Cursor.DEFAULT);
            }
        });
    }
}
