package org.istic.synthlab.ui.plugins.workspace;

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
import org.istic.synthlab.ui.history.Origin;
import org.istic.synthlab.ui.history.State;
import org.istic.synthlab.ui.plugins.plug.InputPlug;
import org.istic.synthlab.ui.plugins.plug.OutputPlug;

import java.util.UUID;

/**
 * Manage plug insertion and linking.
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
    private PlugState plugState = PlugState.UNPLUGGED; // Default is not connected right :D

    @Override
    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }

    /**
     * Return true if the plug is both inputPlug and ouput connected
     *
     * @return
     *
     * @implSpec PLUGGED state
     */
    public boolean isPlugged() {
        return plugState == PlugState.PLUGGED;
    }

    /**
     * Indicates if this plug is currently being drawn by user
     *
     * @return true if any of the inputPlug plug or outputPlug plug is null
     *
     * @implSpec any states but PLUGGED or UNPLUGGED ones
     */
    public boolean isBeingPlugged() {
        return plugState != PlugState.PLUGGED && plugState != PlugState.UNPLUGGED;
    }

    public CurveCable() {
        super();
        setId(UUID.randomUUID().toString());
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

        // Add a context menu to the plug
        setOnMouseClicked(new ContextMenuHandler(this));

        setStrokeWidth(7.5);
        setStrokeLineCap(StrokeLineCap.ROUND);
        setFill(Color.TRANSPARENT);
        setColor(Color.RED);
        setEffect(new InnerShadow());
        autosize();
    }

    /**
     * Manage the different internal connection related states of this plug
     *
     * LEGEND :
     *      inputPlug = true when connected, false otherwize
     *      outputPlug = true when connected, false otherwize
     *                                                                                      !inputPlug & !ouput
     *              +--------------------------------------------------------------------------------------+
     *              |                                                                                      |
     *              |                        !inputPlug & !ouput         outputPlug                                |
     *              |    +----------------------------------+       +------------------------------+       |
     *              |    |                                  |       |                              |       |
     *              |    |                            +-----+-------+-----+                        |       |
     *              |    |            inputPlug & !outputPlug |State              | !inputPlug & ouput         |       |
     *              |    |    +----------------------->        IN_PLUGGED +--------------------+   |       |
     *              |    |    |                       |                   |                    |   |       |
     *              |    |    |                       +-----+-------^-----+                    |   |       |
     *         +----v----v----+---+       !inputPlug & !outputPlug  |       | !inputPlug & outputPlug +--------v---v----+  |
     *         |State             <-------------------------+       +-----------------+ State           |  |
     * O+------>        UNPLUGGED |                                                   |         PLUGGED +--+
     *         |                  <-------------------------+       +-----------------+                 |
     *         +---------^----+---+       !inputPlug & !outputPlug  |       | !outputPlug & inputPlug +--------^---^----+
     *                   |    |                       +-----+-------v-----+                    |   |
     *                   |    |       outputPlug & !inputPlug |State              |                    |   |
     *                   |    +----------------------->       OUT_PLUGGED +--------------------+   |
     *                   |                            |                   | !outputPlug & inputPlug        |
     *                   |                            +-----+-------+-----+                        |
     *                   |                  !inputPlug & !ouput |       | outputPlug & inputPlug               |
     *                   +----------------------------------+       +------------------------------+
     *
     *                                                                  made with : http://www.asciiflow.com
     */
    public enum PlugState {

        UNPLUGGED {
            @Override
            public PlugState nextState(CurveCable cable) {
                // inputPlug connected
                if (cable.inputPlug != null && cable.outputPlug != null) {
                    return PLUGGED;
                    // outputPlug connected
                } else if (cable.inputPlug == null && cable.outputPlug != null) {
                    return OUT_PLUGGED;
                    // no connections
                } else if (cable.inputPlug != null) { // && plug.outputPlug == null
                    return IN_PLUGGED;
                }
                // default, stay in the state
                return UNPLUGGED;
            }
        },

        IN_PLUGGED {
            @Override
            public PlugState nextState(CurveCable cable) {
                // outputPlug is connected and intput still connected
                if (cable.outputPlug != null && cable.inputPlug != null) {
                    return PLUGGED;
                    // if inputPlug is deconnected
                } else if (cable.inputPlug == null) {
                    return UNPLUGGED;
                }
                // default, stay in the state
                return IN_PLUGGED;
            }
        },

        OUT_PLUGGED {
            @Override
            public PlugState nextState(CurveCable cable) {
                // intput is connected and outputPlug still connected
                if (cable.inputPlug != null && cable.outputPlug != null) {
                    return PLUGGED;
                    // if outputPlug is deconnected
                } else if (cable.outputPlug == null) {
                    return UNPLUGGED;
                }
                // default, stay in the state
                return OUT_PLUGGED;
            }
        },

        PLUGGED {
            @Override
            public PlugState nextState(CurveCable cable) {
                // fully deconnected
                if (cable.inputPlug == null && cable.outputPlug == null) {
                    return UNPLUGGED;
                    // only inputPlug is deconnected
                } else if (cable.inputPlug == null) { // && plug.outputPlug != null
                    return OUT_PLUGGED;
                    // only outputPlug is deconnected
                } else if (cable.outputPlug == null) { // && plug.inputPlug != null
                    return IN_PLUGGED;
                }
                // default, stay in the state
                return PLUGGED;
            }
        };

        public PlugState nextState(CurveCable cable) {
            return UNPLUGGED;
        }
    }

    /**
     * Helper method to call next state
     *
     */
    private void nextState() {
        System.out.println(this.getId() + "\tLeaving => " + plugState);
        plugState = plugState.nextState(this);
        System.out.println(this.getId() + "\tEntering => " + plugState);
    }

    /**
     * Return the current inputPlug plug
     *
     * @return
     *
     * @implSpec previously in UNPLUGGED or INPUT_CHANGING states only
     */
    public InputPlug getInputPlug() {
        return inputPlug;
    }

    /**
     * Connect to an inputPlug
     * The plug manage itself its connections, so this method MUST be call without controls
     *
     * @param inputPlug
     */
    public void connectInputPlug(final InputPlug inputPlug) {
        System.out.println("CONNECT INPUT PLUG");
        //System.out.println(this + "::connectInputPlug");
        deactivateMouseTrackingHandlers();
        //if (plugState == PlugState.PLUGGED) {
        //    disconnectInputPlug();
        //}
        if (plugState == PlugState.UNPLUGGED || plugState == PlugState.OUT_PLUGGED) {
            this.inputPlug = inputPlug;
            inputPlug.setCable(this);
            setStartX(computeCoordinates(this.inputPlug).getX());
            setStartY(computeCoordinates(this.inputPlug).getY());

            // Modify the coordinates of the curve as the node moves
            inputPlug.getParent().layoutXProperty().addListener((observable, oldValue, newValue) -> {
                setStartX(computeCoordinates(this.inputPlug).getX());
            });

            inputPlug.getParent().layoutYProperty().addListener((observable, oldValue, newValue) -> {
                setStartY(computeCoordinates(this.inputPlug).getY());
            });
            nextState();
        }

        if (plugState == PlugState.IN_PLUGGED) {
            activateMouseTrackingHandlers();
        }
    }

    /**
     * Deconnect the inputPlug plug and activates mouse traking handlers if changing
     *
     * @implSpec previously in PLUGGED state only
     *
     */
    public void disconnectInputPlug() {
        System.out.println("DECONNECT INPUT PLUG");
        //System.out.println(this + "::deconnectInputPlug");
        inputPlug.setCable(null);
        inputPlug = null;
        nextState();
        // If still outputPlug connected
        if (plugState == PlugState.OUT_PLUGGED) {
            activateMouseTrackingHandlers();
        }
    }

    /**
     * Returns the outputPlug plug
     *
     * @return
     *
     * @implSpec previously in UNPLUGGED or OUTPUT_CHANGING states only
     */
    public OutputPlug getOutputPlug() {
        return outputPlug;
    }

    /**
     * Connect to an outputPlug
     * The plug manage itself its connections, so this method MUST be call without controls
     *
     * @param outputPlug
     */
    public void connectOutputPlug(OutputPlug outputPlug) {
        //System.out.println(this + "::connectOutputPlug");
        deactivateMouseTrackingHandlers();
        //if (plugState == PlugState.PLUGGED) {
        //    disconnectOutputPlug();
        //}
        if (plugState == PlugState.UNPLUGGED || plugState == PlugState.IN_PLUGGED) {
            this.outputPlug = outputPlug;
            outputPlug.setCable(this);
            setEndX(computeCoordinates(outputPlug).getX());
            setEndY(computeCoordinates(outputPlug).getY());

            // Modify the coordinates of the curve as the node moves
            outputPlug.getParent().layoutXProperty().addListener((observable, oldValue, newValue) -> {
                setEndX(computeCoordinates(this.outputPlug).getX());
            });

            outputPlug.getParent().layoutYProperty().addListener((observable, oldValue, newValue) -> {
                setEndY(computeCoordinates(this.outputPlug).getY());
            });
            nextState();
        }

        if (plugState == PlugState.OUT_PLUGGED) {
            activateMouseTrackingHandlers();
        }
    }

    /**
     * Deconnect the outputPlug plug and activates mouse traking handlers if changing
     *
     * @implSpec previously in PLUGGED state only
     */
    public void disconnectOutputPlug() {
        System.out.println("DECONNECT OUTPUT PLUG");
        //System.out.println(this + "::deconnectOutputPlug");
        outputPlug.setCable(null);
        outputPlug = null;
        nextState();
        // If still inputPlug connected
        if (plugState == PlugState.IN_PLUGGED) {
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

    public Color getColor(){
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
        strokeProperty().set(color);
    }

    /**
     * Event handler when right clicking on a plug
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

                // Entry to delete a plug
                final MenuItem deleteMenu = new MenuItem(null, new Label("Delete"));
                deleteMenu.setOnAction(e -> {
                    CoreController.getConnectionManager().deleteCable(cable);
                });

                // Entry to change the color of a plug
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
            // FIXME: when managed inside the curvecable, the moving of a cable does not work properly, it creates a new cable after reconnection
            // because of the connection manager which has finished the first connection, so no more current cable and no more cable attached to the plug yet !!!
//            if (event.getButton() == MouseButton.PRIMARY) {
//                if (plugState == PlugState.PLUGGED) {
//                    Point2D clickPoint = new Point2D(event.getSceneX(), event.getSceneY());
//                    Point2D inputPoint = computeCoordinates(inputPlug);
//                    Point2D outputPoint = computeCoordinates(outputPlug);
//                    if (inputPoint.distance(clickPoint) < outputPoint.distance(clickPoint)) {
//                        disconnectInputPlug();
//                    } else {
//                        disconnectOutputPlug();
//                    }
//                    // Then call the connectionManager to give this plug
//                    //CoreController.getConnectionManager().setCurrentCable((CurveCable) event.getSource());
//                    event.consume();
//                }
//            }
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

            if (plugState == PlugState.IN_PLUGGED) {
                setEndX(x);
                setEndY(y);
            } else if (plugState == PlugState.OUT_PLUGGED) {
                setStartX(x);
                setStartY(y);
            }
            event.consume();
        }
    }


    /**
     * Make the plug follow the pointer
     * Set mouse transparent
     * Invalidate the plug if clicked inside the workspacePane while changing or linking
     *
     */
    private void activateMouseTrackingHandlers() {
        // Make the plug follow the cursor
        setMouseTransparent(true);
        CoreController.getWorkspace().setOnMouseMoved(new FollowCursor());
        if (plugState == PlugState.IN_PLUGGED) {
            final Point2D inputPosition = computeCoordinates(inputPlug);
            setEndX(inputPosition.getX());
            setEndY(inputPosition.getY());
        }
        else if (plugState == PlugState.OUT_PLUGGED) {
            final Point2D outputPosition = computeCoordinates(outputPlug);
            setStartX(outputPosition.getX());
            setStartY(outputPosition.getY());
        }
        // Cancel the drawing if we click on the void
        CoreController.getWorkspace().setOnMouseClicked(event -> {
            CoreController.getConnectionManager().deleteCable(this);
        });
    }

    /**
     * Make the plug unfollow the pointer
     * Unset mouse transparent
     *
     */
    private void deactivateMouseTrackingHandlers() {
        // Make the plug follow the cursor
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
        obj.put("fill", getFill().toString());
        obj.put("stroke", getStroke().toString());
        obj.put("type", "cable");
        obj.put("state", plugState.name());
        obj.put("name", Math.random() );
        if (plugState == PlugState.PLUGGED || plugState == PlugState.IN_PLUGGED) {
            obj.put("inComponentId", inputPlug.getParent().getId());
            obj.put("inputPlug", inputPlug.getId());
            obj.put("name", inputPlug.getParent().getId() + "-" + inputPlug.getId());
        }
        if (plugState == PlugState.PLUGGED || plugState == PlugState.OUT_PLUGGED) {
            obj.put("outComponentId", outputPlug.getParent().getId());
            obj.put("outputPlug", outputPlug.getId());
            obj.put("name", outputPlug.getParent().getId() + "-" + outputPlug.getId());
        }
        if (plugState == PlugState.PLUGGED) {
            obj.put("name",
                    inputPlug.getParent().getId() + "-" + inputPlug.getId() +
                            "|" + outputPlug.getParent().getId() + "-" + outputPlug.getId());
        }
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
