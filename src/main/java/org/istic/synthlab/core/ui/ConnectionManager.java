package org.istic.synthlab.core.ui;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import org.istic.synthlab.core.IObserver;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.plugins.cable.BoundLine;
import org.istic.synthlab.plugins.cable.Center;
import org.istic.synthlab.core.services.Register;

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
    private static List<IObserver> listObservers = new ArrayList<>();

    public static HashMap<Line, HashMap<IOutput, IInput>> lineConnection = new HashMap<>();
    private static Circle circleInput;
    private static Circle circleOutput;
    private static Line line;


    public static void addObserver(IObserver obs){
        listObservers.add(obs);
    }

    public static void deleteObserver(IObserver obs){
        listObservers.remove(obs);
    }

    private static void update(){
        for(IObserver obj : listObservers){
            obj.update(connectionTab);
            obj.drawLine(lineConnection);
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
        connectionTab.put(output, input);
        Register.connect(input, output);
        IOMappingService.connect(input, output);
        drawCable();
        update();
        input = null;
        output = null;
    }

    private static void drawCable(){
        Center endCenter = new Center(circleInput);
        Center startCenter = new Center(circleOutput);
        //endCenter.setBounds(circleOutput.localToScreen(circleInput.getBoundsInLocal()));
        //startCenter.setBounds(circleInput.localToScreen(circleInput.getBoundsInLocal()));
        System.out.println("GLOBAL POSITION: "+circleInput.localToScreen(circleInput.getBoundsInLocal()));
        System.out.println("GLOBAL POSITION: "+circleOutput.localToScreen(circleOutput.getBoundsInLocal()));
        //System.out.println("CIRCLE INPUT: "+circleInput.getRadius());
        //System.out.println("CIRCLE INPUT: "+circleInput.getLayoutX()+" --- "+circleInput.getLayoutY());
        //System.out.println("CIRCLE OUTPUT: "+circleOutput.getLayoutX()+" ---- "+circleOutput.getLayoutY());
        //System.out.println("CENTER INPUT: "+startCenter.centerXProperty()+" ---- "+startCenter.centerYProperty());
        //System.out.println("CENTER OUTPUT: "+endCenter.centerXProperty()+" ---- "+endCenter.centerYProperty());
        Line line = new BoundLine(
                startCenter.centerXProperty(),
                startCenter.centerYProperty(),
                endCenter.centerXProperty(),
                endCenter.centerYProperty()
        );

        HashMap <IOutput, IInput> map = new HashMap<>();
        map.put(output, input);
        lineConnection.put(line, map);
    }
}
