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

    public static HashMap<Line, Connection> lineConnection = new HashMap<>();
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

    public static void makeOrigin(Circle circle, IOutput futureConnectionOrigin){
        output = futureConnectionOrigin;
        circleOutput = circle;

        if(connectionTab.containsKey(output)){
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

        if(connectionTab.containsValue(input)){

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
            lineConnection.put(line, connection);
            return true;
        }
        return false;
    }

    private static Line getKeyLine(IInput value){
        Set keys = lineConnection.keySet();
        Iterator it = keys.iterator();
        while(it.hasNext()) {
            Line key = (Line) it.next();
            Connection co = lineConnection.get(key);
            if(co.getInput() == value){
                return key;
            }
        }
        return null;
    }

    private static IOutput getKey(IInput value){
        Set keys = connectionTab.keySet();
        Iterator it = keys.iterator();
        while(it.hasNext()){
            IOutput key = (IOutput) it.next();
            IInput value_key = connectionTab.get(key);
            if(value_key == value){
                return key;
            }
        }
        return null;
    }
}
