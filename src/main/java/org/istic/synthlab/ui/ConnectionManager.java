package org.istic.synthlab.ui;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import org.istic.synthlab.core.IObserver;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.ui.plugins.cable.BoundLine;
import org.istic.synthlab.ui.plugins.cable.Center;

import java.util.*;

/**
 * @author Sebastien
 */
public class ConnectionManager {
    private static IOutput output;
    private static IInput input;
    private static HashMap<IOutput,IInput> connectionTab = new HashMap<>();
    private static List<IObserver> observers = new ArrayList<>();
    private static Boolean cable_selected = false;

    private static HashMap<Line, Connection> lineConnection = new HashMap<>();
    private static Circle circleInput;
    private static Circle circleOutput;

    public static void addObserver(IObserver observer) {
        observers.add(observer);
    }

    public static void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    private static void update() {
        for (IObserver observer : observers) {
            observer.update(connectionTab);
            observer.unDrawLine(lineConnection);
            observer.drawLine(lineConnection);
        }
    }

    public static void deleteLine(Line line){
        if(lineConnection.containsKey(line)){
            Connection connection = lineConnection.get(line);
            IInput input = connection.getInput();
            IOutput output = connection.getOutput();
            Register.disconnect(output);

            connectionTab.remove(output);
            lineConnection.remove(line);
            update();
        }
    }

    public static void makeOrigin(Circle circle, IOutput futureConnectionOrigin){
        output = futureConnectionOrigin;
        circleOutput = circle;


        if(!cable_selected && connectionTab.containsKey(output)){

            cable_selected = true;
            IInput value = connectionTab.get(output);
            Line key_line = getKeyLine(value);

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

    public static void makeDestination(Circle circle, IInput futureConnectionDestination){
        input = futureConnectionDestination;
        circleInput = circle;


        if(!cable_selected && connectionTab.containsValue(input)){
            cable_selected = true;

            IOutput key = getKey(input);
            connectionTab.remove(key);

            Line key_line = getKeyLine(input);
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

    private static boolean drawCable(){
        Connection connection = new Connection(output, input);
        if((!lineConnection.containsValue(connection)) && (!connectionTab.containsValue(input)) && (!connectionTab.containsKey(output))){
            Center endCenter = new Center(circleInput);
            Center startCenter = new Center(circleOutput);
            Line line = new BoundLine(
                    startCenter.centerXProperty(),
                    startCenter.centerYProperty(),
                    endCenter.centerXProperty(),
                    endCenter.centerYProperty()
            );
            //line.setStrokeWidth(10);
            lineConnection.put(line, connection);
            return true;
        }
        return false;
    }

    private static Line getKeyLine(IInput value){
        Set keys = lineConnection.keySet();
        for (Object key1 : keys) {
            Line key = (Line) key1;
            Connection co = lineConnection.get(key);
            if (co.getInput() == value) {
                return key;
            }
        }
        return null;
    }

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
}
