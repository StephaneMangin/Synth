package org.istic.synthlab.ui;


import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.ui.plugins.ComponentPane;
import org.istic.synthlab.ui.plugins.WorkspacePane;
import org.istic.synthlab.ui.plugins.cable.CurveCable;
import org.istic.synthlab.ui.plugins.history.HistoryImpl;
import org.istic.synthlab.ui.plugins.history.History;
import org.istic.synthlab.ui.plugins.history.StateType;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Sebastien
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 * @author Thibaut Rousseau <thibaut.rousseau@outlook.com>
 */
public class ConnectionManager {
    private Stage stage;
    private CoreController coreController;
    private History history = new HistoryImpl();
    public void setCoreController(CoreController coreController) {
        this.coreController = coreController;
    }

    public void setStage(Stage node) {
        stage = node;
    }

    public Stage getStage(){
        return stage;
    }

    private Pair<Node, IOutput> output;
    private Pair<Node, IInput> input;
    private CurveCable currentlyDrawnCable;

    /**
     * Used to remember the cables associated to a node
     */
    private final HashMap<Node, CurveCable> nodeToCableBinding = new HashMap<>();

    private boolean isNodeFree(final Node node) {
        return !nodeToCableBinding.containsKey(node);
    }

    /**
     * Used to maintain a list of cables associated to a component
     */
    private HashMap<IComponent, List<CurveCable>> componentToCablesBinding = new HashMap<>();

    /**
     * Utility method to add a component/cable binding to the componentToCablesBinding attribute
     */
    private void addComponentToCableBinding(final IComponent component, final CurveCable cable) {
        if (!componentToCablesBinding.containsKey(component)) {
            componentToCablesBinding.put(component, new CopyOnWriteArrayList<>());
        }
        componentToCablesBinding.get(component).add(cable);
    }

    /**
     * Utility method to remove a component/cable binding to the componentToCablesBinding attribute
     */
    private void removeComponentToCableBinding(final IComponent component, final CurveCable cable) {
        if (componentToCablesBinding.containsKey(component)) {
            componentToCablesBinding.get(component).remove(cable);
            history.add(cable, StateType.DELETED);
        }
    }

    private final HashMap<Node, IInput> nodeToInputBinding = new HashMap<>();
    private final HashMap<Node, IOutput> nodeToOutputBinding = new HashMap<>();

    private boolean isInputNode(final Node node) {
        return nodeToInputBinding.containsKey(node);
    }

    private boolean isOutputNode(final Node node) {
        return nodeToOutputBinding.containsKey(node);
    }

    private void resetDrawingSystem() {
        history.add(currentlyDrawnCable, StateType.CHANGED);
        coreController.getWorkspace().setOnMouseMoved(null);
        coreController.getWorkspace().setOnMouseClicked(null);
        currentlyDrawnCable = null;
        input = null;
        output = null;
    }

    public void plug(final Node node, final IOutput futureConnectionOrigin) {
        // Handle the case when the user clicks on an input and then on another input
        if (output != null) {
            coreController.getWorkspace().getChildren().remove(currentlyDrawnCable);
        }

        output = new Pair<>(node, futureConnectionOrigin);

        plugCable(node);
        history.add(currentlyDrawnCable, StateType.CHANGED);
    }

    public void plug(final Node node, final IInput futureConnectionDestination) {
        // Handle the case when the user clicks on an output and then on another output
        if (input != null) {
            coreController.getWorkspace().getChildren().remove(currentlyDrawnCable);
        }

        input = new Pair<>(node, futureConnectionDestination);

        plugCable(node);
    }

    /**
     * Try to plug a cable in a socket
     */
    private void plugCable(final Node node) {
        if (currentlyDrawnCable == null) {
            if (isNodeFree(node)) {
                plugStartCable(node);
            }
            else {
                moveCable(node);
            }
        }
        // If it's connected on one end
        else {
            plugEndCable(node);
        }
    }

    /**
     * Plug the first end of a cable
     */
    private void plugStartCable(final Node node) {
        currentlyDrawnCable = new CurveCable(node, node);

        // Make the cable follow the cursor
        currentlyDrawnCable.setMouseTransparent(true);
        coreController.getWorkspace().setOnMouseMoved(event -> {
            currentlyDrawnCable.setStartX(event.getX());
            currentlyDrawnCable.setStartY(event.getY());
        });

        // Cancel the drawing if we click on the void
        coreController.getWorkspace().setOnMouseClicked(event -> cancelCable());

        // Actually draw the cable
        coreController.getWorkspace().getChildren().add(currentlyDrawnCable);
    }

    /**
     * Plug the second end of a cable
     */
    private void plugEndCable(final Node node) {
        if (!isNodeFree(node)) {
            return;
        }

        // Redraw the cable to its final coordinates
        // FIXME: should be simply `currentlyDrawnCable.setEndNode(node);`
        coreController.getWorkspace().getChildren().remove(currentlyDrawnCable);
        currentlyDrawnCable = new CurveCable(currentlyDrawnCable.getStartNode(), node);
        coreController.getWorkspace().getChildren().add(currentlyDrawnCable);

        // Add a context menu to the cable
        currentlyDrawnCable.setOnMouseClicked(new CableContextMenu());

        // Save some state data
        nodeToCableBinding.put(currentlyDrawnCable.getStartNode(), currentlyDrawnCable);
        nodeToCableBinding.put(currentlyDrawnCable.getEndNode(), currentlyDrawnCable);
        nodeToInputBinding.put(input.getKey(), input.getValue());
        nodeToOutputBinding.put(output.getKey(), output.getValue());
        addComponentToCableBinding(input.getValue().getComponent(), currentlyDrawnCable);
        addComponentToCableBinding(output.getValue().getComponent(), currentlyDrawnCable);

        // Make the model connection
        Register.connect(input.getValue(), output.getValue());

        resetDrawingSystem();
    }

    private void moveCable(final Node node) {
        final CurveCable cable = nodeToCableBinding.get(node);
        final Node toKeepPlugged = cable.getStartNode() != node ? cable.getStartNode() : cable.getEndNode();

        if (isInputNode(toKeepPlugged)) {
            final IInput in = nodeToInputBinding.get(toKeepPlugged);
            deleteCable(cable);
            plug(toKeepPlugged, in);
        }
        else if (isOutputNode(toKeepPlugged)) {
            final IOutput out = nodeToOutputBinding.get(toKeepPlugged);
            deleteCable(cable);
            plug(toKeepPlugged, out);
        }
        else {
            // This should never happen if the state data are correctly managed
            throw new RuntimeException("This should not happen...");
        }
    }

    private void cancelCable() {
        if (currentlyDrawnCable != null) {
            deleteCable(currentlyDrawnCable);
            resetDrawingSystem();
        }
    }

    private void deleteCable(final CurveCable cable) {
        nodeToCableBinding.remove(cable.getStartNode());
        nodeToCableBinding.remove(cable.getEndNode());

        // We need this verification for the case when the cable is currently being drawn
        if (cable.getStartNode() != cable.getEndNode()) {
            IInput in;
            IOutput out;

            if (isInputNode(cable.getStartNode())) {
                in = nodeToInputBinding.get(cable.getStartNode());
                out = nodeToOutputBinding.get(cable.getEndNode());
            }
            else if (isOutputNode(cable.getStartNode())) {
                in = nodeToInputBinding.get(cable.getEndNode());
                out = nodeToOutputBinding.get(cable.getStartNode());
            }
            else {
                // This should never happen if the state data are correctly managed
                throw new RuntimeException("This should not happen...");
            }

            assert in != null;
            assert out != null;

            Register.disconnect(in);

            // Maintain the state data up to date
            removeComponentToCableBinding(in.getComponent(), cable);
            removeComponentToCableBinding(out.getComponent(), cable);
            nodeToInputBinding.remove(cable.getEndNode());
            nodeToOutputBinding.remove(cable.getStartNode());
        }

        coreController.getWorkspace().getChildren().remove(cable);

        history.add(cable, StateType.DELETED);
    }

    public void deleteComponent(final IComponent component, final ComponentPane node) {
        cancelCable();

        coreController.getWorkspace().getChildren().remove(node);
        componentToCablesBinding.get(component).forEach(curveCable -> deleteCable(curveCable));
        componentToCablesBinding.remove(component);

        history.add(node, StateType.DELETED);
    }

    /**
     * Event handler when right clicking on a cable
     */
    private class CableContextMenu implements EventHandler<MouseEvent> {
        @Override
        public void handle(final MouseEvent event) {
            // On right click
            if (event.getButton() == MouseButton.SECONDARY) {
                cancelCable();

                final CurveCable cable = (CurveCable) event.getSource();

                // Create a context menu
                final ContextMenu contextMenu = new ContextMenu();

                // Entry to delete a cable
                final MenuItem deleteMenu = new MenuItem(null, new Label("Delete"));
                deleteMenu.setOnAction(e -> {
                    deleteCable(cable);
                });

                // Entry to change the color of a cable
                final ColorPicker colorPicker = new ColorPicker();
                colorPicker.setValue(cable.getColor());
                colorPicker.getStyleClass().add("button");
                colorPicker.setStyle("-fx-background-color: transparent;");

                final MenuItem colorMenu = new MenuItem(null, colorPicker);
                colorMenu.setOnAction(e -> cable.setColor(colorPicker.getValue()));

                // Show the context menu
                contextMenu.getItems().addAll(deleteMenu, colorMenu);
                contextMenu.show(cable , event.getScreenX(), event.getScreenY());

                event.consume();
            }
        }
    }

    public History getHistory() {
        return history;
    }

}
