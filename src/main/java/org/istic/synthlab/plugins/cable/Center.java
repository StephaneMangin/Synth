package org.istic.synthlab.plugins.cable;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.geometry.Bounds;
import javafx.scene.Node;

/**
 * @author augustin <augustin.bardou@gmail.com>
 */
public class Center {
    private ReadOnlyDoubleWrapper centerX = new ReadOnlyDoubleWrapper();
    private ReadOnlyDoubleWrapper centerY = new ReadOnlyDoubleWrapper();

    public Center(Node node) {
        calcCenter(node.getBoundsInParent());
        node.boundsInParentProperty().addListener((observableValue, oldBounds, bounds) -> {
            calcCenter(bounds);
        });
    }

    private void calcCenter(Bounds bounds) {
        centerX.set(bounds.getMinX() + bounds.getWidth()  / 2);
        centerY.set(bounds.getMinY() + bounds.getHeight() / 2);
    }

    ReadOnlyDoubleProperty centerXProperty() {
        return centerX.getReadOnlyProperty();
    }

    ReadOnlyDoubleProperty centerYProperty() {
        return centerY.getReadOnlyProperty();
    }
}
