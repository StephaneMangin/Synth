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
    private static HashMap<String,String> connectionTab = new HashMap<String,String>();
    private static List<IObserver> listObservers = new ArrayList<>();

    public static void addObserver(IObserver obs){
        System.out.println("plug "+ origin);
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
            make_destination(port);
        }
        else{
            make_origin(port);
        }
    }

    private static void make_origin(String future_connection_origin){
        origin = future_connection_origin;
    }

    private static void make_destination(String future_connection_destination){
        destination = future_connection_destination;
        if (isValid()){
            if(connectionTab.containsKey(future_connection_destination) || connectionTab.containsValue(future_connection_destination)) {
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
