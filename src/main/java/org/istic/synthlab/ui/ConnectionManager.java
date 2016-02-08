package org.istic.synthlab.ui;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import org.istic.synthlab.core.IObserver;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.ui.plugins.cable.BoundLine;
import org.istic.synthlab.ui.plugins.cable.Center;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Sebastien
 */
public class ConnectionManager {
    private static IOutput output;
    private static IInput input;
    private static HashMap<IOutput,IInput> connectionTab = new HashMap<>();
    private static List<IObserver> observers = new ArrayList<>();

    public static HashMap<Line, HashMap<IOutput, IInput>> lineConnection = new HashMap<>();
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
            observer.drawLine(lineConnection);
        }
    }

    public static void makeOrigin(Circle circle, IOutput futureConnectionOrigin){
        output = futureConnectionOrigin;
        circleOutput = circle;
        if(input != null){
            makeConnection();
        }
    }

    public static void makeDestination(Circle circle, IInput futureConnectionDestination){
        input = futureConnectionDestination;
        circleInput = circle;
        if(output != null){
            makeConnection();
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
        HashMap <IOutput, IInput> map = new HashMap<>();
        map.put(output, input);
        if((!lineConnection.containsValue(map)) && (!connectionTab.containsValue(input)) && (!connectionTab.containsKey(output))){
            Center endCenter = new Center(circleInput);
            Center startCenter = new Center(circleOutput);
            Line line = new BoundLine(
                    startCenter.centerXProperty(),
                    startCenter.centerYProperty(),
                    endCenter.centerXProperty(),
                    endCenter.centerYProperty()
            );
            lineConnection.put(line, map);
            return true;
        }
        return false;
    }
}
