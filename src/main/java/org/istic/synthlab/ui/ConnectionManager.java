package org.istic.synthlab.ui;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.istic.synthlab.core.IComponent;
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
    private static HashMap<CurveCable, Connection> lineConnection = new HashMap<>();    //Each CurveCable has an IInput and an IOutput
    private static HashMap<IComponent, List<CurveCable>> componentCurveCableHashMap = new HashMap<>();  //Each IComponent has a list of CurveCable
    private static List <IComponent> componentList = new ArrayList<>(); //List of two IComponent which will used for create a connection
    private static Node inputNode;
    private static Node outputNode;
    private static Node lastDraw;
    private static Color colorCurrentCable = Color.RED;
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
    public static void makeOrigin(IComponent abstractComponent, Node node, IOutput futureConnectionOrigin){
        output = futureConnectionOrigin;
        outputNode = node;
        componentList.add(abstractComponent);
        if(!cableSelected && connectionTab.containsKey(output)){
            cableSelected = true;
            IInput value = connectionTab.get(output);
            CurveCable keyLine = getKeyLine(value);

            connectionTab.remove(output);
            lineConnection.remove(keyLine);
            colorCurrentCable = keyLine.getColor();

            Register.disconnect(output);
            input = value;

            // FIXME: access the anchorPane in a cleaner way + factorize
            coreController.anchorPane.setOnMouseMoved(event -> {
                coreController.undraw(lastDraw);
                CurveCable curveCable = new CurveCable(
                        event.getX(),
                        event.getY(),
                        computeCoordinates(inputNode).getX(),
                        computeCoordinates(inputNode).getY(),
                        colorCurrentCable
                );
                curveCable.setMouseTransparent(true);
                curveCable.setId("cableDrag");
                curveCable.setOnMouseClicked(null);
                coreController.draw(curveCable);
                lastDraw = curveCable;
            });


            update();
        }
        else{
            if(!connectionTab.containsKey(output)) {
                // FIXME: access the anchorPane in a cleaner way + factorize
                coreController.anchorPane.setOnMouseMoved(event -> {
                    coreController.undraw(lastDraw);
                    CurveCable curveCable = new CurveCable(
                            event.getX(),
                            event.getY(),
                            computeCoordinates(outputNode).getX(),
                            computeCoordinates(outputNode).getY(),
                            colorCurrentCable
                    );
                    curveCable.setId("cableDrag");
                    curveCable.setMouseTransparent(true);
                    curveCable.setOnMouseClicked(null);
                    coreController.draw(curveCable);
                    lastDraw = curveCable;
                });
            }
            if(input != null && !connectionTab.containsKey(output)){
                coreController.undraw(lastDraw);
                coreController.anchorPane.setOnMouseMoved(null);
                if(!makeConnection()){
                    componentList.remove(abstractComponent);
                }
            }

        }
    }

    /**
     * Call makeConnection if an Output as already been clicked and that the connection is authorized by the model
     * otherwise it disconnect the current connection using this input
     * @param node the instance of the node click in the view
     * @param futureConnectionDestination the input destination for the new connection
     */
    public static void makeDestination(IComponent abstractComponent, Node node, IInput futureConnectionDestination){
        input = futureConnectionDestination;
        inputNode = node;
        componentList.add(abstractComponent);
        if(!cableSelected && connectionTab.containsValue(input)){
            cableSelected = true;

            IOutput key = getKey(input);
            connectionTab.remove(key);

            CurveCable keyLine = getKeyLine(input);
            colorCurrentCable = keyLine.getColor();
            lineConnection.remove(keyLine);

            Register.disconnect(input);
            output = key;
            // FIXME: access the anchorPane in a cleaner way + factorize
            coreController.anchorPane.setOnMouseMoved(event -> {
                coreController.undraw(lastDraw);
                CurveCable curveCable = new CurveCable(
                        event.getX(),
                        event.getY(),
                        computeCoordinates(outputNode).getX(),
                        computeCoordinates(outputNode).getY(),
                        colorCurrentCable
                );
                curveCable.setMouseTransparent(true);
                curveCable.setId("cableDrag");
                curveCable.setOnMouseClicked(null);
                coreController.draw(curveCable);
                lastDraw = curveCable;
            });
            update();

        }
        else{
            if(!connectionTab.containsValue(input)){
                // FIXME: access the anchorPane in a cleaner way + factorize
                coreController.anchorPane.setOnMouseMoved(event -> {
                    coreController.undraw(lastDraw);
                    CurveCable curveCable = new CurveCable(
                            event.getX(),
                            event.getY(),
                            computeCoordinates(inputNode).getX(),
                            computeCoordinates(inputNode).getY(),
                            colorCurrentCable
                    );
                    curveCable.setMouseTransparent(true);
                    curveCable.setId("cableDrag");
                    curveCable.setOnMouseClicked(null);
                    coreController.draw(curveCable);
                    lastDraw = curveCable;
                });

                if(output != null){
                    coreController.undraw(lastDraw);
                    coreController.anchorPane.setOnMouseMoved(null);
                    if(!makeConnection()){  //Check if the connection is not established
                        componentList.remove(abstractComponent); //Remove the IComponent from the List<IComponent>
                    }
                }
            }
        }
    }

    /**
     * Create a connection in the model and call the method update to create the connection in the view
     */
    private static boolean makeConnection(){
        if(drawCable()) {
            connectionTab.put(output, input);
            Register.connect(input, output);
            update();
            input = null;
            output = null;
            cableSelected = false;
            List<IComponent> tmpComponentList = new ArrayList<>(componentList);
            for(IComponent abs : tmpComponentList){ //Browse the List<IComponent>
                componentList.remove(abs);  //Remove the IComponent from the List<IComponent>
            }
            return true;
        }
        return false;
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
            List <CurveCable> cables = new ArrayList<>();   //Create a List<CurveCable>
            for(IComponent abs : componentList){    //Browse the List<IComponent>
                if(componentCurveCableHashMap.containsKey(abs)){    //Check if the IComponent is already in the HashMap
                    cables = componentCurveCableHashMap.get(abs);   //get the list of CurveCable link to this IComponent
                }
                cables.add(curveCable); //Add the CurveCable to the List<CurveCable>
                componentCurveCableHashMap.put(abs,cables); //Add the new list of CurveCable to this IComponent
            }
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

    /**
     * Method adding a mouse event to disconnect and reconnect a cable
     * @param node the destination node
     */
    private static void addMouseEventFlyingCable(Node node){
        stage.getScene().setOnMouseMoved(event -> {
            coreController.undraw(lastDraw);
            CurveCable curveCable = new CurveCable(
                    event.getX() + 10,
                    event.getY() + 10,
                    computeCoordinates(node).getX(),
                    computeCoordinates(node).getY(),
                    colorCurrentCable
            );
            curveCable.setId("cableDrag");
            curveCable.setOnMouseClicked(null);
            coreController.draw(curveCable);
            lastDraw = curveCable;
        });
    }

    /**
     * Check if there are connection between this pane and an other.
     * If yes, it will delete all curveCable.
     * After that, it will call the method removeViewComponent from the class CoreController to remove the
     * representation of the component in the view.
     * @param abstractComponent the instance of an IComponent
     * @param pane the container
     */
    public static void deleteComponent(IComponent abstractComponent, Pane pane){
        Set keySet = componentCurveCableHashMap.keySet();
        if(componentCurveCableHashMap.containsKey(abstractComponent)){   //if the component is link to something
            List<CurveCable> cables = componentCurveCableHashMap.get(abstractComponent); //get the list of CurveCable
            List<CurveCable> tmpCables = new ArrayList<>(cables);   //Copy the list
            for(Object obj : keySet){   //Browse the HashMap<IComponent, List<CurveCable>>
                for(CurveCable cC : tmpCables){     //Browse the list of CurveCable
                    componentCurveCableHashMap.get(obj).remove(cC); //Try to remove for each IComponent to remove the CurveCable
                    deleteLine(cC); //Remove the CurveCable from the copy
                }
            }
            componentCurveCableHashMap.remove(abstractComponent);   //Remove the IComponent from the HashMap
            //A FAIRER DESTRUCTION DE L'OBJET COTE MODEL
        }
        coreController.removeViewComponent(pane);   //Delete the pane in the view
    }
}