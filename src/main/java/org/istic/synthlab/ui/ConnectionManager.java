package org.istic.synthlab.ui;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.istic.synthlab.core.IObserver;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.ui.plugins.cable.CurveCable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author Sebastien
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class ConnectionManager {
    private static IOutput output;
    private static IInput input;
    private static HashMap<IOutput,IInput> connectionTab = new HashMap<>();
    private static List<IObserver> observers = new ArrayList<>();
    private static Boolean cableSelected = false;
    private static HashMap<CurveCable, Connection> lineConnection = new HashMap<>();
    private static Node inputNode;
    private static Node outputNode;
    private static Node lastDraw;
    private static Color colorCurrentCable;
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

    public static void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    /**
     * Update all observers
     */
    private static void update() {
        for (IObserver observer : observers) {
            observer.update(connectionTab);
            observer.unDrawLine(lineConnection);
            observer.drawLine(lineConnection);
        }
    }

    /**
     * Delete a CurveCable from the HashMap lineConnection
     * @param line the line we want to remove
     */
    public static void deleteLine(CurveCable line){
        if(lineConnection.containsKey(line)){
            Connection connection = lineConnection.get(line);
            IOutput output = connection.getOutput();
            Register.disconnect(output);

            connectionTab.remove(output);
            lineConnection.remove(line);
            update();
        }
    }

    /**
     * Call makeConnection if an Input as already been clicked and that the connection is authorized by the model
     * otherwise it disconnect the current connection using this output
     * @param node the instance of the node click in the view
     * @param futureConnectionOrigin the output destination for the new connection
     */
    public static void makeOrigin(Node node, IOutput futureConnectionOrigin){
        output = futureConnectionOrigin;
        outputNode = node;
        if(!cableSelected && connectionTab.containsKey(output)){
            cableSelected = true;
            IInput value = connectionTab.get(output);
            CurveCable key_line = getKeyLine(value);

            connectionTab.remove(output);
            lineConnection.remove(key_line);
            colorCurrentCable = key_line.getColor();

            Register.disconnect(output);
            input = value;

            stage.getScene().setOnMouseMoved(event -> {
                coreController.undraw(lastDraw);
                CurveCable curveCable = new CurveCable(
                        event.getX() + 10,
                        event.getY() + 10,
                        computeCoordinates(inputNode).getX(),
                        computeCoordinates(inputNode).getY(),
                        colorCurrentCable
                );
                curveCable.setId("cableDrag");
                curveCable.setOnMouseClicked(null);
                coreController.draw(curveCable);
                lastDraw = curveCable;
            });


            update();
        }
        else{
            coreController.undraw(lastDraw);
            stage.getScene().setOnMouseMoved(null);

            if(input != null && !connectionTab.containsKey(output)){

                makeConnection();
            }
        }
    }

    /**
     * Call makeConnection if an Output as already been clicked and that the connection is authorized by the model
     * otherwise it disconnect the current connection using this input
     * @param node the instance of the node click in the view
     * @param futureConnectionDestination the input destination for the new connection
     */
    public static void makeDestination(Node node, IInput futureConnectionDestination){
        input = futureConnectionDestination;
        inputNode = node;
        if(!cableSelected && connectionTab.containsValue(input)){
            cableSelected = true;

            IOutput key = getKey(input);
            connectionTab.remove(key);

            CurveCable keyLine = getKeyLine(input);
            colorCurrentCable = keyLine.getColor();
            lineConnection.remove(keyLine);

            Register.disconnect(input);
            output = key;
            stage.getScene().setOnMouseMoved(event -> {
                coreController.undraw(lastDraw);
                CurveCable curveCable = new CurveCable(
                        event.getX() + 10,
                        event.getY() + 10,
                        computeCoordinates(outputNode).getX(),
                        computeCoordinates(outputNode).getY(),
                        colorCurrentCable
                );
                curveCable.setId("cableDrag");
                curveCable.setOnMouseClicked(null);
                coreController.draw(curveCable);
                lastDraw = curveCable;
            });
            update();

        }
        else{
            coreController.undraw(lastDraw);
            stage.getScene().setOnMouseMoved(null);
            if(output != null && !connectionTab.containsValue(input)){
                makeConnection();
            }
        }
    }

    /**
     * Create a connection in the model and call the method update to create the connection in the view
     */
    private static void makeConnection(){
        if(drawCable()) {
            connectionTab.put(output, input);
            Register.connect(input, output);
            update();
            input = null;
            output = null;
            cableSelected = false;
        }
    }

    /**
     * Initialize a CurveCable between two points and add a color picker on the representation
     * @return true if the cable is create or false if not
     */
    private static boolean drawCable(){
        Connection connection = new Connection(output, input);
        if((!lineConnection.containsValue(connection))      //Check that the connection is not already existing
                && (!connectionTab.containsValue(input))    //Check if the input destination is not involve with an other connection
                && (!connectionTab.containsKey(output))){   //Check if the output source is not involve with an other connection

            final Point2D point1 = computeCoordinates(inputNode);
            final Point2D point2 = computeCoordinates(outputNode);
            final CurveCable curveCable = new CurveCable(point1, point2);

            if (colorCurrentCable != null) {
                curveCable.setColor(colorCurrentCable);
            }

            lineConnection.put(curveCable, connection);
            return true;
        }
        return false;
    }

    /**
     * Get the CurveCable attached to an input
     * @param value value of the input
     * @return a CurveCable object
     */
    private static CurveCable getKeyLine(IInput value){
        Set keys = lineConnection.keySet();
        for (Object key1 : keys) {
            CurveCable key = (CurveCable) key1;
            Connection co = lineConnection.get(key);
            if (co.getInput() == value) {
                return key;
            }
        }
        return null;
    }

    /**
     * Get the Output attached to an input
     * @param value value of the input
     * @return an Output object
     */
    private static IOutput getKey(IInput value){
        Set keys = connectionTab.keySet();
        for (Object key1 : keys) {
            IOutput key = (IOutput) key1;
            IInput value_key = connectionTab.get(key);
            if (value_key == value) {
                return key;
            }
        }
        return null;
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
