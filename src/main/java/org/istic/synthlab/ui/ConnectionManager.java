package org.istic.synthlab.ui;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
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
 */
public class ConnectionManager {
    private static IOutput output;
    private static IInput input;
    private static HashMap<IOutput,IInput> connectionTab = new HashMap<>();
    private static List<IObserver> observers = new ArrayList<>();
    private static Boolean cable_selected = false;
    private static HashMap<CurveCable, Connection> lineConnection = new HashMap<>();
    private static Circle circleInput;
    private static Circle circleOutput;
    private static Node root;
    private static Stage stage;

    public static void setNode(Node node) {
        root = node;
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
     * @param circle the instance of the circle click in the view
     * @param futureConnectionOrigin the output destination for the new connection
     */
    public static void makeOrigin(Circle circle, IOutput futureConnectionOrigin){
        output = futureConnectionOrigin;
        circleOutput = circle;
        if(!cable_selected && connectionTab.containsKey(output)){
            cable_selected = true;
            IInput value = connectionTab.get(output);
            CurveCable key_line = getKeyLine(value);

            connectionTab.remove(output);
            lineConnection.remove(key_line);

            Register.disconnect(output);
            input = value;
            update();
        }
        else{
            if(input != null){
                makeConnection();
            }
        }
    }

    /**
     * Call makeConnection if an Output as already been clicked and that the connection is authorized by the model
     * otherwise it disconnect the current connection using this input
     * @param circle the instance of the circle click in the view
     * @param futureConnectionDestination the input destination for the new connection
     */
    public static void makeDestination(Circle circle, IInput futureConnectionDestination){
        input = futureConnectionDestination;
        circleInput = circle;
        if(!cable_selected && connectionTab.containsValue(input)){
            cable_selected = true;

            IOutput key = getKey(input);
            connectionTab.remove(key);

            CurveCable key_line = getKeyLine(input);
            lineConnection.remove(key_line);

            Register.disconnect(input);

            update();
            output = key;
        }
        else{
            if(output != null){
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
            cable_selected = false;
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
            Color color = Color.FORESTGREEN;
            CurveCable curveCable = new CurveCable(
                    getLocalScene(circleInput).getX(),
                    getLocalScene(circleInput).getY(),
                    getLocalScene(circleOutput).getX(),
                    getLocalScene(circleOutput).getY(),
                    color
            );

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

    private static Point2D getLocalScene(Circle circle) {
        return circle.localToScene(circleInput.getCenterX(), circleInput.getCenterY());
    }
}
