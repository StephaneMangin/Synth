package org.istic.synthlab.ui.plugins.cable;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.StrokeLineCap;
import net.minidev.json.JSONObject;
import org.istic.synthlab.ui.CoreController;
import org.istic.synthlab.ui.plugins.ComponentPane;
import org.istic.synthlab.ui.plugins.WorkspacePane;
import org.istic.synthlab.ui.plugins.history.State;
import org.istic.synthlab.ui.plugins.history.Origin;

import java.util.EnumSet;
import java.util.Set;

/**
 * Manage cable insertion and linking.
 *
 * This class uses a FSM to manage its connection states.
 *
 * @author Augustion Bardou <>
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 * @author Thibaut Rousseau <thibaut.rousseau@outlook.com>
 */
public class CurveCable extends CubicCurve implements Origin, Comparable {

    private String name;

    // Keep the color to override setter
    private Color color;

    private InputPlug inputPlug = null;
    private OutputPlug outputPlug = null;
    private InternalState internalState = InternalState.NOT_CONNECTED; // Default is not connected right :D

    @Override
    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }

    /**
     * Return true if the cable is both input and ouput connected
     *
     * @return
     *
     * @implSpec CONNECTED state
     */
    public boolean isFullyConnected() {
        return internalState == InternalState.CONNECTED;
    }

    /**
     * Indicates if this cable is currently being drawn by user
     *
     * @return true if any of the input plug or output plug is null
     *
     * @implSpec any states but CONNECTED or NOT_CONNECTED ones
     */
    public boolean isBeingConnected() {
        return internalState != InternalState.CONNECTED && internalState != InternalState.NOT_CONNECTED;
    }

    /**
     * Manage the different internal connection related states of this cable
     *
     * LEGEND :
     *      input = true when connected, false otherwize
     *      output = true when connected, false otherwize
     *                                                                                                                       !input & !ouput
     *              +-----------------------------------------------------------------------------------------------------------------------+
     *              |                                                                                                                       |
     *              |                      !input & !ouput              output                                                              |
     *              |  +----------------------------------+            +------------------------------------------------------------+       |
     *              |  |                                  |            |                                                            |       |
     *              |  |                               +--+------------+---+    +-------------------+                               |       |
     *              |  |               input & !output |                   |    |                   | !input & ouput                |       |
     *              |  | +-----------------------------> INPUT CONNECTED   |    | INPUT CHANGING    +-------------------------+     |       |
     *              |  | |                             |                   |    |                   |                         |     |       |
     *              |  | |                             +-------------------+    +-----+---^---------+                         |     |       |
     *         +----v--v-+---------+                                 !input & !output |   |           !input & output+--------v-----v----+  |
     *         |                   <--------------------------------------------------+   +--------------------------+                   |  |
     * O-------> NOT_CONNECTED     |                                                                                 | CONNECTED         +--+
     *         |                   <--------------------------------------------------+   +--------------------------+                   |
     *         +-------^-+---------+                                 !input & !output |   |           !output & input+--------+-----^----+
     *                 | |                             +-------------------+    +-----+---v---------+                         ^     |
     *                 | |             output & !input |                   |    |                   |                         |     |
     *                 | +-----------------------------> OUTPUT CONNECTED  |    | OUTPUT CHANGING   +-------------------------+     |
     *                 |                               |                   |    |                   | !output & input               |
     *                 |                               +--+------------+---+    +-------------------+                               |
     *                 |                  !input & !ouput |            | output & input                                             |
     *                 +----------------------------------+            +------------------------------------------------------------+
     *
     *                                                                                                   made with : http://www.asciiflow.com
     */
    public enum InternalState {

        NOT_CONNECTED {
            @Override
            public InternalState nextState(CurveCable cable) {
                // input connected
                if (cable.inputPlug != null && cable.outputPlug != null) {
                    return CONNECTED;
                    // output connected
                } else if (cable.inputPlug == null && cable.outputPlug != null) {
                    return OUTPUT_CONNECTED;
                    // no connections
                } else if (cable.inputPlug != null) { // && cable.outputPlug == null
                    return INPUT_CONNECTED;
                }
                // default, stay in the state
                return NOT_CONNECTED;
            }
            @Override
            public Set<InternalState> availableStates(CurveCable cable) {
                return EnumSet.of(CONNECTED, OUTPUT_CONNECTED, INPUT_CONNECTED, NOT_CONNECTED);
            }
        },

        INPUT_CONNECTED {
            @Override
            public InternalState nextState(CurveCable cable) {
                // output is connected and intput still connected
                if (cable.outputPlug != null && cable.inputPlug != null) {
                    return CONNECTED;
                    // if input is deconnected
                } else if (cable.inputPlug == null) {
                    return NOT_CONNECTED;
                }
                // default, stay in the state
                return INPUT_CONNECTED;
            }
            public Set<InternalState> availableStates(CurveCable cable) {
                return EnumSet.of(CONNECTED, INPUT_CONNECTED, NOT_CONNECTED);
            }
        },

        INPUT_CHANGING {
            @Override
            public InternalState nextState(CurveCable cable) {
                // input reconnected
                if (cable.inputPlug != null && cable.outputPlug != null) {
                    return CONNECTED;
                    // output deconnected
                } else if (cable.outputPlug == null) {
                    return NOT_CONNECTED;
                }
                // default, stay in the state
                return INPUT_CHANGING;
            }
            public Set<InternalState> availableStates(CurveCable cable) {
                return EnumSet.of(CONNECTED, INPUT_CHANGING, NOT_CONNECTED);
            }
        },

        OUTPUT_CONNECTED {
            @Override
            public InternalState nextState(CurveCable cable) {
                // intput is connected and output still connected
                if (cable.inputPlug != null && cable.outputPlug != null) {
                    return CONNECTED;
                    // if output is deconnected
                } else if (cable.outputPlug == null) {
                    return NOT_CONNECTED;
                }
                // default, stay in the state
                return OUTPUT_CONNECTED;
            }
            public Set<InternalState> availableStates(CurveCable cable) {
                return EnumSet.of(CONNECTED, OUTPUT_CONNECTED, NOT_CONNECTED);
            }
        },

        OUTPUT_CHANGING {
            @Override
            public InternalState nextState(CurveCable cable) {
                // output reconnected
                if (cable.outputPlug != null && cable.inputPlug != null) {
                    return CONNECTED;
                    // output deconnected
                } else if (cable.inputPlug == null) {
                    return NOT_CONNECTED;
                }
                // default, stay in the state
                return OUTPUT_CHANGING;
            }
            public Set<InternalState> availableStates(CurveCable cable) {
                return EnumSet.of(CONNECTED, OUTPUT_CHANGING, NOT_CONNECTED);
            }
        },

        CONNECTED {
            @Override
            public InternalState nextState(CurveCable cable) {
                // fully deconnected
                if (cable.inputPlug == null && cable.outputPlug == null) {
                    return NOT_CONNECTED;
                    // only input is deconnected
                } else if (cable.inputPlug == null) { // && cable.outputPlug != null
                    return INPUT_CHANGING;
                    // only output is deconnected
                } else if (cable.outputPlug == null) { // && cable.inputPlug != null
                    return OUTPUT_CHANGING;
                }
                // default, stay in the state
                return CONNECTED;
            }
            public Set<InternalState> availableStates(CurveCable cable) {
                return EnumSet.of(CONNECTED, INPUT_CHANGING, OUTPUT_CHANGING, NOT_CONNECTED);
            }
        };

        public InternalState nextState(CurveCable cable) {
            return NOT_CONNECTED;
        }

        public Set<InternalState> availableStates(CurveCable cable) {
            return EnumSet.of(CONNECTED, INPUT_CONNECTED, OUTPUT_CONNECTED, INPUT_CHANGING, OUTPUT_CHANGING, NOT_CONNECTED);
        }

    }

    /**
     * Helper method to call next state
     *
     */
    private void nextState() {
        System.out.println(this + "\tLeaving => " + internalState);
        internalState = internalState.nextState(this);
        System.out.println(this + "\tEntering => " + internalState);
    }

    /**
     * Return the current input plug
     *
     * @return
     *
     * @implSpec previously in NOT_CONNECTED or INPUT_CHANGING states only
     */
    public InputPlug getInputPlug() {
        return inputPlug;
    }

    public void connectInputPlug(final InputPlug inputPlug) {
        deactivateControls();
        if (internalState == InternalState.CONNECTED) {
            deconnectInputPlug();
        }
        if (internalState == InternalState.NOT_CONNECTED || internalState == InternalState.OUTPUT_CONNECTED) {
            this.inputPlug = inputPlug;
            inputPlug.setCable(this);

            // Modify the coordinates of the curve as the node moves
            inputPlug.getParent().layoutXProperty().addListener((observable, oldValue, newValue) -> {
                setStartX(computeCoordinates(inputPlug).getX());
            });

            inputPlug.getParent().layoutYProperty().addListener((observable, oldValue, newValue) -> {
                setStartY(computeCoordinates(inputPlug).getY());
            });
            nextState();
        }

        if (internalState == InternalState.INPUT_CONNECTED) {
            setStartX(computeCoordinates(inputPlug).getX());
            setStartY(computeCoordinates(inputPlug).getY());
            activateMouseTrackingHandlers();
        }
    }

    /**
     * Deconnect the input plug and activates mouse traking handlers if changing
     *
     * @implSpec previously in CONNECTED state only
     *
     */
    public void deconnectInputPlug() {
        System.out.println(this + "::deconnectInputPlug");
        inputPlug.setCable(null);
        inputPlug = null;
        nextState();
        if (internalState == InternalState.INPUT_CHANGING) {
            activateMouseTrackingHandlers();
        }
    }

    /**
     * Returns the output plug
     *
     * @return
     *
     * @implSpec previously in NOT_CONNECTED or OUTPUT_CHANGING states only
     */
    public OutputPlug getOutputPlug() {
        return outputPlug;
    }

    public void connectOutputPlug(OutputPlug outputPlug) {
        System.out.println(this + "::connectOutputPlug");
        deactivateControls();
        if (internalState == InternalState.CONNECTED) {
            deconnectOutputPlug();
        }
        if (internalState == InternalState.NOT_CONNECTED || internalState == InternalState.INPUT_CONNECTED) {
            this.outputPlug = outputPlug;
            outputPlug.setCable(this);

            // Modify the coordinates of the curve as the node moves
            outputPlug.getParent().layoutXProperty().addListener((observable, oldValue, newValue) -> {
                setEndX(computeCoordinates(inputPlug).getX());
            });

            outputPlug.getParent().layoutYProperty().addListener((observable, oldValue, newValue) -> {
                setEndY(computeCoordinates(inputPlug).getY());
            });
            nextState();
        }

        if (internalState == InternalState.OUTPUT_CONNECTED) {
            setEndX(computeCoordinates(outputPlug).getX());
            setEndY(computeCoordinates(outputPlug).getY());
            activateMouseTrackingHandlers();
        }
    }

    /**
     * Deconnect the output plug and activates mouse traking handlers if changing
     *
     * @implSpec previously in CONNECTED state only
     */
    public void deconnectOutputPlug() {
        System.out.println(this + "::deconnectOutputPlug");
        outputPlug.setCable(null);
        outputPlug = null;
        nextState();
        if (internalState == InternalState.OUTPUT_CHANGING) {
            activateMouseTrackingHandlers();
        }
    }

    /**
     * Helper method to retrieve coordinates of a node
     *
     * @param node
     * @return
     */
    private static Point2D computeCoordinates(final Node node) {
        double x = node.getParent().getBoundsInParent().getMinX() + node.getBoundsInParent().getMinX(),
               y = node.getParent().getBoundsInParent().getMinY() + node.getBoundsInParent().getMinY();

        x += node.getBoundsInParent().getWidth()/2;
        y += node.getBoundsInParent().getHeight()/2;

        return new Point2D(x, y);
    }

    public CurveCable() {
        super();

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

        // Add a context menu to the cable
        setOnMouseClicked(new ContextMenuHandler(this));

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

    /**
     * Recalulates position relatively to custom coordinates
     *
     * @param x
     * @param y
     */
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

    /**
     * Event handler when right clicking on a cable
     */
    private class ContextMenuHandler implements EventHandler<MouseEvent> {

        private CurveCable cable;

        public ContextMenuHandler(CurveCable cable) {
            this.cable = cable;
        }

        @Override
        public void handle(final MouseEvent event) {
            // On right click
            if (event.getButton() == MouseButton.SECONDARY) {

                // Create a context menu
                final ContextMenu contextMenu = new ContextMenu();

                // Entry to delete a cable
                final MenuItem deleteMenu = new MenuItem(null, new Label("Delete"));
                deleteMenu.setOnAction(e -> {
                    CoreController.getConnectionManager().deleteCable(cable);
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

    private class FollowCursor implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            double x = event.getX(),
                    y = event.getY();

            final Pane pane = (Pane) event.getSource();

            // Ensure the cables aren't dragged outside the anchorPane
            if (x < 0) {
                x = 0;
            } else if (x > pane.getWidth()) {
                x = pane.getWidth();
            }

            if (y < 0) {
                y = 0;
            } else if (y > pane.getHeight()) {
                y = pane.getHeight();
            }

            if (internalState == InternalState.OUTPUT_CHANGING || internalState == InternalState.INPUT_CONNECTED) {
                setEndX(x);
                setEndY(y);
            } else if (internalState == InternalState.INPUT_CHANGING || internalState == InternalState.OUTPUT_CONNECTED) {
                setStartX(x);
                setStartY(y);
            }
            event.consume();
        }
    }


    /**
     * Make the cable follow the pointer
     * Set mouse transparent
     * Invalidate the cable if clicked inside the workspacePane while changing or linking
     *
     */
    private void activateMouseTrackingHandlers() {
        // Make the cable follow the cursor
        setMouseTransparent(true);
        CoreController.getWorkspace().setOnMouseMoved(new FollowCursor());
        // Cancel the drawing if we click on the void
        CoreController.getWorkspace().setOnMouseClicked(event -> {
            if (event.getSource() instanceof WorkspacePane) {
                CoreController.getConnectionManager().deleteCable(this);
            }
        });
    }

    /**
     * Make the cable unfollow the pointer
     * Unset mouse transparent
     *
     */
    private void deactivateControls() {
        // Make the cable follow the cursor
        setMouseTransparent(false);
        CoreController.getWorkspace().setOnMouseMoved(null);
        // Cancel the drawing if we click on the void
        CoreController.getWorkspace().setOnMouseClicked(null);
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
        nextState();
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
//        obj.put("startMethod", inputPlug != null ? inputPlug.getProperties().get("onMouseClicked") : null);
//        obj.put("endMethod", outputPlug != null ? outputPlug.getProperties().get("onMouseClicked") : null);
//        obj.put("startComponantId", inputPlug != null ? inputPlug.getParent().getId() : outputPlug.getParent().getId());
//        obj.put("endComponantId", outputPlug != null ? outputPlug.getParent().getId() : outputPlug.getParent().getId());
//        obj.put("name", inputPlug != null ? inputPlug.getId() : outputPlug.getId());
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
        nextState();
    }

}
