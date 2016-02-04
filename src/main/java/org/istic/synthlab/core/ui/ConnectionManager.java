package org.istic.synthlab.core.ui;

import org.istic.synthlab.core.IObserver;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Register;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by seb on 03/02/16.
 */
public class ConnectionManager {
    private static IOutput output;
    private static IInput input;
    private static HashMap<IOutput,IInput> connectionTab = new HashMap<>();
    private static List<IObserver> listObservers = new ArrayList<>();

    public static void addObserver(IObserver obs){
        listObservers.add(obs);
    }

    public static void deleteObserver(IObserver obs){
        listObservers.remove(obs);
    }

    private static void update(){
        for(IObserver obj : listObservers){
            obj.update(connectionTab);
        }
    }


    public static void makeOrigin(IOutput futureConnectionOrigin){
        output = futureConnectionOrigin;
        if(input != null){
            connectionTab.put(output, input);
            Register.connect(input, output);
            update();
            input = null;
            output = null;
        }

    }

    public static void makeDestination(IInput futureConnectionDestination){
        input = futureConnectionDestination;
        if(output != null){
            connectionTab.put(output, input);
            Register.connect(input, output);
            update();
            input = null;
            output = null;
        }

    }
}
