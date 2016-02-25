package org.istic.synthlab.ui;


import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.ui.plugins.cable.CurveCable;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Sebastien
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 * @author Thibaut Rousseau <thibaut.rousseau@outlook.com>
 */
public class ConnectionManager {
    private static Stage stage;
    private static CoreController coreController;
    public static void setCoreController(CoreController coreController) {
        ConnectionManager.coreController = coreController;
    }

    public static void setStage(Stage node) {
        stage = node;
    }

    public static Stage getStage(){
        return stage;
    }

    private static Pair<Node, IOutput> output;
    private static Pair<Node, IInput> input;
    private static CurveCable currentlyDrawnCable;

    /**
     * Used to remember the cables associated to a node
     */
    private static final HashMap<Node, CurveCable> nodeToCableBinding = new HashMap<>();

    private static boolean isNodeFree(final Node node) {
        return !nodeToCableBinding.containsKey(node);
    }

    /**
     * Used to maintain a list of cables associated to a component
     */
    private static HashMap<IComponent, List<CurveCable>> componentToCablesBinding = new HashMap<>();

    /**
     * Utility method to add a component/cable binding to the componentToCablesBinding attribute
     */
    private static void addComponentToCableBinding(final IComponent component, final CurveCable cable) {
        if (!componentToCablesBinding.containsKey(component)) {
            componentToCablesBinding.put(component, new CopyOnWriteArrayList<>());
        }
        componentToCablesBinding.get(component).add(cable);
    }

    /**
     * Utility method to remove a component/cable binding to the componentToCablesBinding attribute
     */
    private static void removeComponentToCableBinding(final IComponent component, final CurveCable cable) {
        if (componentToCablesBinding.containsKey(component)) {
            componentToCablesBinding.get(component).remove(cable);
        }
    }

    private static final HashMap<Node, IInput> nodeToInputBinding = new HashMap<>();
    private static final HashMap<Node, IOutput> nodeToOutputBinding = new HashMap<>();

    private static boolean isInputNode(final Node node) {
        return nodeToInputBinding.containsKey(node);
    }

    private static boolean isOutputNode(final Node node) {
        return nodeToOutputBinding.containsKey(node);
    }

    private static void resetDrawingSystem() {
        coreController.getAnchorPane().setOnMouseMoved(null);
        coreController.getAnchorPane().setOnMouseClicked(null);
        currentlyDrawnCable = null;
        input = null;
        output = null;
    }

    public static void plug(final Node node, final IOutput futureConnectionOrigin) {
        // Handle the case when the user clicks on an input and then on another input
        if (output != null) {
            coreController.getAnchorPane().getChildren().remove(currentlyDrawnCable);
        }

        output = new Pair<>(node, futureConnectionOrigin);

        plugCable(node);
    }

    public static void plug(final Node node, final IInput futureConnectionDestination) {
        // Handle the case when the user clicks on an output and then on another output
        if (input != null) {
            coreController.getAnchorPane().getChildren().remove(currentlyDrawnCable);
        }

        input = new Pair<>(node, futureConnectionDestination);

        plugCable(node);
    }

    /**
     * Try to plug a cable in a socket
     */
    private static void plugCable(final Node node) {
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
    private static void plugStartCable(final Node node) {
        currentlyDrawnCable = new CurveCable(node, node);

        // Make the cable follow the cursor
        currentlyDrawnCable.setMouseTransparent(true);
        coreController.getAnchorPane().setOnMouseMoved(event -> {
            currentlyDrawnCable.setStartX(event.getX());
            currentlyDrawnCable.setStartY(event.getY());
        });

        // Cancel the drawing if we click on the void
        coreController.getAnchorPane().setOnMouseClicked(event -> cancelCable());

        // Actually draw the cable
        coreController.getAnchorPane().getChildren().add(currentlyDrawnCable);
    }

    /**
     * Plug the second end of a cable
     */
    private static void plugEndCable(final Node node) {
        if (!isNodeFree(node)) {
            return;
        }

        // Redraw the cable to its final coordinates
        // FIXME: should be simply `currentlyDrawnCable.setEndNode(node);`
        coreController.getAnchorPane().getChildren().remove(currentlyDrawnCable);
        currentlyDrawnCable = new CurveCable(currentlyDrawnCable.getStartNode(), node);
        coreController.getAnchorPane().getChildren().add(currentlyDrawnCable);

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

    private static void moveCable(final Node node) {
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

    private static void cancelCable() {
        if (currentlyDrawnCable != null) {
            deleteCable(currentlyDrawnCable);
            resetDrawingSystem();
        }
    }

    private static void deleteCable(final CurveCable cable) {
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

        coreController.getAnchorPane().getChildren().remove(cable);
    }

    public static void deleteComponent(final IComponent component, final AnchorPane anchorPane) {
        cancelCable();

        coreController.getAnchorPane().getChildren().remove(anchorPane);
        componentToCablesBinding.get(component).forEach(ConnectionManager::deleteCable);
        componentToCablesBinding.remove(component);
    }

    /**
     * Event handler when right clicking on a cable
     */
    private static class CableContextMenu implements EventHandler<MouseEvent> {
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
                deleteMenu.setOnAction(e -> deleteCable(cable));

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
}
