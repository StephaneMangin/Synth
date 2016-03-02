package org.istic.synthlab.ui.plugins.workspace;

import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import net.minidev.json.JSONObject;
import org.istic.synthlab.ui.CoreController;
import org.istic.synthlab.ui.history.Origin;
import org.istic.synthlab.ui.history.State;
import org.istic.synthlab.ui.history.StateType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class WorkspacePane extends AnchorPane implements Origin {

    // Be sure there's never a component named like this for this to work
    private static final String DRAG_N_DROP_MOVE_GUARD = "";
    private static final double ZOOM_STEP = 0.01;
    private static final double ZOOM_MAX = 2;
    private static final double ZOOM_MIN = 0.4;
    private String name;

    public WorkspacePane()  {
        super();
        setOnDragOver(event -> {
            if (event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });

        setOnDragDropped(event -> {
            final Dragboard db = event.getDragboard();
            if (db.hasString()) {
                ComponentPane component = null;

                // Move a component
                if (db.getString().equals(DRAG_N_DROP_MOVE_GUARD)) {
                    component = (ComponentPane) event.getGestureSource();
                }
                // Load a component
                else {
                    component = CoreController.loadComponent(db.getString().toLowerCase());
                    addWithDragging(component);
                    event.consume();
                }


                assert component != null;
                double x = event.getX()-(component.getBoundsInLocal().getWidth()/2),
                        y = event.getY()-(component.getBoundsInLocal().getHeight()/2);

                // Prevent the components from being outside the componentPane
                if (x < 0) {
                    x = 0;
                }
                if (y < 0) {
                    y = 0;
                }
                if (x + component.getBoundsInParent().getWidth() > getWidth()) {
                    x = getWidth() - component.getBoundsInParent().getWidth();
                }
                if (y + component.getBoundsInParent().getHeight() > getHeight()) {
                    y = getHeight() - component.getBoundsInParent().getHeight();
                }

                // Place the component
                component.setLayoutX(x);
                component.setLayoutY(y);

                // Detect collisions
                layoutComponents();

                event.setDropCompleted(true);
            }
            event.consume();
        });
        setName("Workspace-1");
    }

    /**
     * Remove a component from the componentPane
     * @param pane the pane we will remove.
     */
    public void removeViewComponent(Pane pane){
        getChildren().remove(pane);
    }

    /**
     * Add a component to the anchorpane and declare the dragging controls handlers
     */
    public void addWithDragging(final ComponentPane component) {

        // Mandage drag and drop
        component.setOnDragDetected(event -> {
            // TODO: add component relocation
            final Dragboard db = component.startDragAndDrop(TransferMode.ANY);
            final ClipboardContent content = new ClipboardContent();

            final SnapshotParameters params = new SnapshotParameters();
            final Scale scale = new Scale();
            scale.setX(getScaleX());
            scale.setY(getScaleY());
            // FIXME: Work fot the minimum scale value but not for the maximum while zooming the anchorpane ?!
            final WritableImage image = component.snapshot(
                    params,
                    new WritableImage(
                            ((Double)(component.getWidth() * getScaleX())).intValue(),
                            ((Double)(component.getHeight() * getScaleY())).intValue()
                    )
            );
            content.putImage(image);

            content.putString(DRAG_N_DROP_MOVE_GUARD);
            db.setContent(content);
            event.consume();
        });
        getChildren().add(component);
        CoreController.getConnectionManager().getHistory().add(component, StateType.CREATED);
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
            switch(s) {
                case "scaleX":
                    setScaleX((double)o);
                    break;
                case "scaleY":
                    setScaleY((double)o);
                    break;
                case "id":
                    setId((String)o);
                    break;
                case "name":
                    setName((String)o);
                    break;
                default:
                    // Do nothing yet
            }
        });
    }

    @Override
    public JSONObject getJson() {
        JSONObject obj = new JSONObject();
        obj.put("scaleX", getScaleX());
        obj.put("scaleY", getScaleY());
        obj.put("type", "workspace");
        obj.put("id", getId());
        obj.put("name", getName());
        return obj;
    }

    @Override
    public State getState() {
        return new State(this);
    }

    @Override
    public void restoreState(State state) {
        setJson(state.getContent());
        notifyAll();
    }

    public void zoomIn() {
        if (getScaleX() < ZOOM_MAX && getScaleY() < ZOOM_MAX) {
            //double oldWidth = componentPane.getPrefWidth();
            //double oldHeight = componentPane.getPrefHeight();
            double newWidth = getMinWidth() * (1 / getScaleX());
            double newHeight = getMinHeight() * (1 / getScaleY());
            setScaleX(getScaleX() + ZOOM_STEP);
            setScaleY(getScaleY() + ZOOM_STEP);
            resize(newWidth, newHeight);
            //scrollpane.setVvalue(scrollpane.getVvalue() + (1 - (oldHeight / newHeight)));
            //scrollpane.setHvalue(scrollpane.getHvalue() + (1 - (oldWidth / newWidth)));
            CoreController.getConnectionManager().getHistory().add(this, StateType.CHANGED);
        }
    }

    public void zoomOut() {
        if (getScaleX() > ZOOM_MIN && getScaleY() > ZOOM_MIN) {

            //double oldWidth = componentPane.getPrefWidth();
            //double oldHeight = componentPane.getPrefHeight();
            double newWidth = getMinWidth() * (1 / getScaleX());
            double newHeight = getMinHeight() * (1 / getScaleY());
            setScaleX(getScaleX() - ZOOM_STEP);
            setScaleY(getScaleY() - ZOOM_STEP);
            resize(newWidth, newHeight);
            //scrollpane.setVvalue(scrollpane.getVvalue() + (1 - (oldHeight / newHeight)));
            //scrollpane.setHvalue(scrollpane.getHvalue() + (1 - (oldWidth / newWidth)));
            CoreController.getConnectionManager().getHistory().add(this, StateType.CHANGED);
        }
    }

    public void defaultZoom() {
        setScaleX(1);
        setScaleY(1);
        setPrefSize(
                getMinWidth(),
                getMinHeight()
        );
        CoreController.getConnectionManager().getHistory().add(this, StateType.CHANGED);
    }

    public ComponentPane getComponent(String id) {
        for (Node node : getChildren()) {
            if (node.getId().equals(id)) {
                return (ComponentPane) node;
            }
        }
        return null;
    }

    /**
     * Move the components so that there is no overlapping
     */
    private void layoutComponents() {
        final List<Node> components = new ArrayList<>(this.getChildren().filtered(node -> !(node instanceof CurveCable)));
        Collections.reverse(components);

        while (components.size() > 0) {
            components.sort(new NodeComparator());

            final Node fixedNode = components.get(0);
            for (int i = 1; i < components.size(); i++) {
                final Node currentNode = components.get(i);
                if (currentNode.getBoundsInParent().intersects(fixedNode.getBoundsInParent())) {
                    if (fixedNode.getLayoutX() + fixedNode.getBoundsInParent().getWidth() - currentNode.getLayoutX() < fixedNode.getLayoutY() + fixedNode.getBoundsInParent().getHeight() - currentNode.getLayoutY()) {
                        //if (currentNode.getLayoutX() - fixedNode.getLayoutX() > currentNode.getLayoutY() - fixedNode.getLayoutY()) {
                        currentNode.setLayoutX(fixedNode.getLayoutX() + fixedNode.getBoundsInParent().getWidth());
                        //currentNode.setLayoutY(fixedNode.getLayoutY());
                    }
                    else {
                        currentNode.setLayoutY(fixedNode.getLayoutY() + fixedNode.getBoundsInParent().getHeight());
                        //currentNode.setLayoutX(fixedNode.getLayoutX());
                    }
                }
            }
            components.remove(0);
        }
    }

    /**
     * Allow to compare two nodes according to the distance from the coordinate (0, 0) of their parent to their top left corner
     */
    private class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(final Node node1, final Node node2) {
            // Sort according to the distance to anchorPane (0, 0)
            /*final Double posNode1 = Math.hypot(node1.getLayoutX(), node1.getLayoutY());
            final Double posNode2 = Math.hypot(node2.getLayoutX(), node2.getLayoutY());
            return posNode1.compareTo(posNode2);*/

            // Sort according to y only
            //return ((Double) node1.getLayoutY()).compareTo(node2.getLayoutY());

            // Sort according to the position of the center of the components
            final Double posNode1 = Math.hypot(node1.getLayoutX() + node1.getBoundsInParent().getWidth()/2, node1.getLayoutY() + node1.getBoundsInParent().getHeight()/2);
            final Double posNode2 = Math.hypot(node2.getLayoutX() + node2.getBoundsInParent().getWidth()/2, node2.getLayoutY() + node2.getBoundsInParent().getHeight()/2);
            return posNode1.compareTo(posNode2);

            // Sort according to y, then to x
            /*if (node1.getLayoutY() > node2.getLayoutY()) {
                return 1;
            }
            else if (node1.getLayoutY() < node2.getLayoutY()) {
                return -1;
            }
            else {
                return ((Double) node1.getLayoutX()).compareTo(node2.getLayoutX());
            }*/
        }
    }
}
