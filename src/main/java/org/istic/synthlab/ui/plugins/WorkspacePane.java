package org.istic.synthlab.ui.plugins;

import javafx.fxml.FXMLLoader;
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
import org.istic.synthlab.ui.plugins.history.Origin;
import org.istic.synthlab.ui.plugins.history.State;
import org.istic.synthlab.ui.plugins.history.StateType;

import java.io.IOException;

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
                // TODO: modify x and y so that there's no collision
                for (final Node otherComponent : getChildren()) {
                    if (component != otherComponent) {
                        if (component.getBoundsInParent().intersects(otherComponent.getBoundsInParent())) {
                            System.out.println("Collision");
                        }
                    }
                }

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
}
