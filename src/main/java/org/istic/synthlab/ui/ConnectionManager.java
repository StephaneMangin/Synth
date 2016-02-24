package org.istic.synthlab.ui;


import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.ui.plugins.cable.CurveCable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static javafx.scene.paint.Color.*;

/**
 * @author Sebastien
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class ConnectionManager {
    private static HashMap<IOutput,IInput> connectionTab = new HashMap<>();
    private static List<IObserver> observers = new ArrayList<>();
    private static Boolean cableSelected = false;
    private static HashMap<CurveCable, Connection> lineConnection = new HashMap<>();    //Each CurveCable has an IInput and an IOutput
    private static HashMap<IComponent, List<CurveCable>> componentCurveCableHashMap = new HashMap<>();  //Each IComponent has a list of CurveCable
    private static List <IComponent> componentList = new ArrayList<>(); //List of two IComponent which will used for create a connection
    private static Node inputNode;
    private static Node outputNode;
    private static Node lastDraw;
    private static Color colorCurrentCable = RED;
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

    public static void addObserver(IObserver observer) {
        observers.add(observer);
    }

    private static Node originNode;
    private static Node destinationNode;
    private static IOutput output;
    private static IInput input;
    private static CurveCable currentlyDrawnCable;

    private static final HashMap<Node, CurveCable> nodeToCableBinding = new HashMap<>();
    private static final HashMap<CurveCable, IInput> cableToInputBinding = new HashMap<>();
    private static final HashMap<CurveCable, IOutput> cableToOutputBinding = new HashMap<>();

    private static void deleteCable(final CurveCable cable) {
        nodeToCableBinding.remove(cable.getStartNode());
        nodeToCableBinding.remove(cable.getEndNode());
        Register.disconnect(cableToInputBinding.get(cable));
        cableToInputBinding.remove(cable);
        cableToOutputBinding.remove(cable);
        coreController.anchorPane.getChildren().remove(cable);
        originNode = destinationNode = null;
    }

    /**
     * Call makeConnection if an Input as already been clicked and that the connection is authorized by the model
     * otherwise it disconnect the current connection using this output
     * @param node the instance of the node click in the view
     * @param futureConnectionOrigin the output destination for the new connection
     */
    public static void makeOrigin(IComponent abstractComponent, final Node node, final IOutput futureConnectionOrigin) {
        // Handle the case when the user clicks on an input and then on another input
        if (originNode != null) {
            coreController.anchorPane.getChildren().remove(currentlyDrawnCable);
        }

        originNode = node;
        output = futureConnectionOrigin;

        // Start drawing the cable
        if (destinationNode == null) {
            // If the socket already has a cable plugged in
            if (nodeToCableBinding.containsKey(node)) {
                final CurveCable cable = nodeToCableBinding.get(node);
                final IInput in = cableToInputBinding.get(cable);
                deleteCable(cable);
                makeDestination(null, cable.getEndNode(), in);
            }
            else {
                currentlyDrawnCable = new CurveCable(originNode, originNode);

                // Make the cable follow the cursor
                currentlyDrawnCable.setMouseTransparent(true);
                coreController.anchorPane.setOnMouseMoved(event -> {
                    currentlyDrawnCable.setEndX(event.getX());
                    currentlyDrawnCable.setEndY(event.getY());
                });

                coreController.anchorPane.getChildren().add(currentlyDrawnCable);
            }
        }
        // Finalize the drawing
        else {
            // Check the second port we click is free
            if (!nodeToCableBinding.containsKey(node)) {
                finalizeDrawing();
            }
            else {
                originNode = null;
            }
        }
    }

    /**
     * Call makeConnection if an Output as already been clicked and that the connection is authorized by the model
     * otherwise it disconnect the current connection using this input
     * @param node the instance of the node click in the view
     * @param futureConnectionDestination the input destination for the new connection
     */
    public static void makeDestination(IComponent abstractComponent, final Node node, final IInput futureConnectionDestination) {
        // Handle the case when the user clicks on an output and then on another output
        if (destinationNode != null) {
            coreController.anchorPane.getChildren().remove(currentlyDrawnCable);
        }

        destinationNode = node;
        input = futureConnectionDestination;

        // Start drawing the cable
        if (originNode == null) {
            if (nodeToCableBinding.containsKey(node)) {
                final CurveCable cable = nodeToCableBinding.get(node);
                final IOutput out = cableToOutputBinding.get(cable);
                deleteCable(cable);
                makeOrigin(null, cable.getStartNode(), out);
            }
            else {
                currentlyDrawnCable = new CurveCable(destinationNode, destinationNode);

                // Make the cable follow the cursor
                currentlyDrawnCable.setMouseTransparent(true);
                coreController.anchorPane.setOnMouseMoved(event -> {
                    currentlyDrawnCable.setStartX(event.getX());
                    currentlyDrawnCable.setStartY(event.getY());
                });

                coreController.anchorPane.getChildren().add(currentlyDrawnCable);
            }
        }
        // Finalize the drawing
        else {
            // Check the second port we click is free
            if (!nodeToCableBinding.containsKey(node)) {
                finalizeDrawing();
            }
            else {
                destinationNode = null;
            }
        }
    }

    private static void finalizeDrawing() {
        // VIEW
        coreController.anchorPane.setOnMouseMoved(null);

        // Redraw the cable to its final coordinates
        coreController.anchorPane.getChildren().remove(currentlyDrawnCable);
        currentlyDrawnCable = new CurveCable(originNode, destinationNode);
        coreController.anchorPane.getChildren().add(currentlyDrawnCable);

        // Add a context menu to the cable
        currentlyDrawnCable.addEventHandler(MouseEvent.MOUSE_CLICKED, new CableContextMenu(currentlyDrawnCable));

        // Reinitialize the drawing system
        nodeToCableBinding.put(originNode, currentlyDrawnCable);
        nodeToCableBinding.put(destinationNode, currentlyDrawnCable);
        originNode = destinationNode = null;

        // MODEL
        cableToInputBinding.put(currentlyDrawnCable, input);
        cableToOutputBinding.put(currentlyDrawnCable, output);
        Register.connect(input, output);
    }

    private static class CableContextMenu implements EventHandler<MouseEvent> {
        private final CurveCable cable;
        private final ContextMenu contextMenu = new ContextMenu();

        public CableContextMenu(final CurveCable cable) {
            this.cable = cable;
            final MenuItem deleteMenu = new MenuItem(null, new Label("Delete"));
            deleteMenu.setOnAction(event -> deleteCable(cable));

            final ColorPicker colorPicker = new ColorPicker();
            colorPicker.setValue(cable.getColor());
            colorPicker.getStyleClass().add("button");
            colorPicker.setStyle("-fx-background-color: transparent;");

            final MenuItem colorMenu = new MenuItem(null, colorPicker);
            colorMenu.setOnAction(event -> cable.setColor(colorPicker.getValue()));

            contextMenu.getItems().addAll(deleteMenu, colorMenu);
        }

        @Override
        public void handle(final MouseEvent event) {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show((Node) event.getSource() , event.getScreenX(), event.getScreenY());
            }
        }
    }

    /**
     * Return the coordinates relative to the scene for the center of a node
     * @param node The node to which convert the coordinates
     * @return A Point2D containing the scene coordinates of the center of node.
     */
    private static Point2D computeCoordinates(final Node node) {
        double x = node.getParent().getBoundsInParent().getMinX() + node.getBoundsInParent().getMinX(),
               y = node.getParent().getBoundsInParent().getMinY() + node.getBoundsInParent().getMinY();

        x += node.getBoundsInParent().getWidth()/2;
        y += node.getBoundsInParent().getHeight()/2;

        return new Point2D(x, y);
    }
}
