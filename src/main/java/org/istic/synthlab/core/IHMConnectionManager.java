package org.istic.synthlab.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by seb on 03/02/16.
 */
public class IHMConnectionManager {
    private static String origin;
    private static String destination;
    private static HashMap<String,String> connectionTab = new HashMap<>();
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
    
    public static void connect(String port){
        if(origin != null){
            makeDestination(port);
        }
        else{
            makeOrigin(port);
        }
    }

    private static void makeOrigin(String futureConnectionOrigin){
        origin = futureConnectionOrigin;
    }

    private static void makeDestination(String futureConnectionDestination){
        destination = futureConnectionDestination;
        if (isValid()){
            if(connectionTab.containsKey(futureConnectionDestination) || connectionTab.containsValue(futureConnectionDestination)) {
                System.out.println("Connection impossible : ports occupés");

            }
            else{
                connectionTab.put(origin, destination);
            }

        }
        else{
            System.out.println("Tentative de connection multiple sur le même port");
        }
        update();
        origin = null;
        destination = null;

    }

    private static Boolean isValid(){
        return !origin.equals(destination);
    }
}
