package org.istic.synthlab.ui.plugins.history;

import javafx.event.EventType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;
import net.minidev.json.parser.JSONParser;
import org.istic.synthlab.ui.CoreController;
import org.istic.synthlab.ui.plugins.ComponentPane;
import org.istic.synthlab.ui.plugins.WorkspacePane;
import org.istic.synthlab.ui.plugins.cable.InputPlug;
import org.istic.synthlab.ui.plugins.controls.Potentiometer;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Implementation of the care taker from memento pattern
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class HistoryImpl extends Observable implements History {

    private State currentState;
    private Queue<State> previousStates = new ArrayDeque<>();
    private Queue<State> nextStates = new ArrayDeque<>();

    @Override
    public void save(File file) throws IOException {
        JSONObject content = new JSONObject();
        JSONArray list = new JSONArray();
        FileWriter writer = new FileWriter(file);
        previousStates.forEach(state -> {
            JSONObject values = new JSONObject();
            values.put("state", state.getType());
            values.put("time", state.getTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            values.put("content", state.getContent());
            list.add(values);
        });
        content.put(String.valueOf(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)), list);
        writer.write(content.toJSONString(JSONStyle.NO_COMPRESS));
        writer.flush();
        writer.close();
    }

    /**
     * TODO
     *
     * This treatment should be inserted in each Origin setJson method in the future
     *
     * @param file
     * @param workspacePane
     * @throws Exception
     */
    @Override
    public void load(File file, WorkspacePane workspacePane) throws Exception {
        JSONParser parser = new JSONParser();
        Reader reader = new FileReader(file);
        // Cut content in the different functional types of states time ordered with a TreeMap
        TreeMap<LocalDateTime, JSONObject> components = new TreeMap<>();
        TreeMap<LocalDateTime, JSONObject> cables = new TreeMap<>();
        TreeMap<LocalDateTime, JSONObject> workspace = new TreeMap<>();
        TreeMap<LocalDateTime, JSONObject> potentiometers = new TreeMap<>();
        ((JSONObject) parser.parse(reader)).forEach((s, o) -> {
            if (o instanceof JSONArray) {
                JSONArray array = (JSONArray) o;
                array.forEach(o1 -> {
                    // Get global vars
                    JSONObject obj = (JSONObject) o1;
                    LocalDateTime time = LocalDateTime.parse((String)obj.get("time"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    JSONObject content = (JSONObject) obj.get("content");
                    String originType = (String) content.get("type");

                    switch(originType) {
                        case "component":
                            System.out.println("component saved => " + obj);
                            components.put(time, obj);
                            break;
                        case "cable":
                            System.out.println("cable saved => " + obj);
                            cables.put(time, obj);
                            break;
                        case "workspace":
                            System.out.println("workspace saved => " + obj);
                            workspace.put(time, obj);
                            break;
                        case "potentiometer":
                            System.out.println("potentiometer saved => " + obj);
                            potentiometers.put(time, obj);
                            break;
                    }
                });
            }
        });
        // Check for each functionnal types and act dependently state types
        components.forEach((aLong, value) -> {
            // Get local vars
            JSONObject jsonObject = (JSONObject) value.get("content");
            String id = (String) jsonObject.get("id");
            String component = (String) jsonObject.get("component");
            ComponentPane componentPane = workspacePane.getComponent(id);
            switch (StateType.valueOf((String) jsonObject.get("state"))) {
                case CREATED:
                    if (componentPane != null) {
                        throw new ExceptionInInitializerError("Componant Pane found ?!");
                    }
                    //TODO: maybe it would be better to pas sthrought events to regenerate component
                    componentPane = CoreController.loadComponent(component);
                    componentPane.setJson(jsonObject);
                    workspacePane.addWithDragging(componentPane);
                    System.out.println(componentPane + " CREATED");
                    break;
                case DELETED:
                    if (componentPane != null) {
                        CoreController.getConnectionManager().deleteComponent(componentPane);
                        System.out.println(componentPane + " DELETED");
                    }
                    System.out.println(componentPane + " ERROR DELETED");
                    break;
                case CHANGED:
                    if (componentPane == null) {
                        throw new ExceptionInInitializerError("Componant Pane not found for changes !");
                    }
                    //workspacePane.getChildren().remove(componentPane);
                    componentPane.setJson(jsonObject);
                    //workspacePane.getChildren().add(componentPane);
                    System.out.println(componentPane + " CHANGED");
                    break;
            }
        });
        cables.forEach((aLong, jsonObject) -> {
            // Get local vars
            String id = (String) jsonObject.get("id");
            String startNodeId = (String) jsonObject.get("startNodeId");
            String endNodeId = (String) jsonObject.get("endNodeId");
            double startX = (double) jsonObject.get("startX");
            double startY = (double) jsonObject.get("startY");
            double endX = (double) jsonObject.get("endX");
            double endY = (double) jsonObject.get("endY");
            switch (StateType.valueOf((String) jsonObject.get("state"))) {
                case CREATED:
                    String startComponentId = (String) jsonObject.get("startComponentId");
                    String endComponentId = (String) jsonObject.get("startComponentId");
                    ComponentPane startComponentPane = workspacePane.getComponent(startComponentId);
                    ComponentPane endComponentPane = workspacePane.getComponent(startComponentId);
                    if (startComponentPane == null) {
                        if (endComponentId != null) {
                            MouseEvent mouseEvent = new MouseEvent(
                                    endComponentPane.lookup(endNodeId), // source the source of the event. Can be null.
                                    endComponentPane.lookup(endNodeId), // target the target of the event. Can be null.
                                    MouseEvent.MOUSE_CLICKED, // eventType The type of the event.
                                    startX, // x The x with respect to the source. Should be in scene coordinates if source == null or source is not a Node.
                                    startY, // y The y with respect to the source. Should be in scene coordinates if source == null or source is not a Node.
                                    0, // screenX The x coordinate relative to screen.
                                    0, // screenY The y coordinate relative to screen.
                                    MouseButton.PRIMARY, // button the mouse button used
                                    1, // clickCount number of click counts
                                    false, // shiftDown true if shift modifier was pressed.
                                    false, // controlDown true if control modifier was pressed.
                                    false, // altDown true if alt modifier was pressed.
                                    false, // metaDown true if meta modifier was pressed.
                                    true, // primaryButtonDown true if primary button was pressed.
                                    false, // middleButtonDown true if middle button was pressed.
                                    false, // secondaryButtonDown true if secondary button was pressed.
                                    false, // synthesized if this event was synthesized
                                    false, // popupTrigger whether this event denotes a popup trigger for current platform
                                    false, // stillSincePress see {@link #isStillSincePress() }
                                    null // pickResult pick result. Can be null, in this case a 2D pick result
                            );

                            endComponentPane.getOnMouseClicked().handle(mouseEvent);
                        } else {
                            throw new ExceptionInInitializerError("Componant Pane not found for potentiometer changes !");
                        }
                    } else {
                        MouseEvent mouseEvent = new MouseEvent(
                                startComponentPane.lookup(startNodeId), // source the source of the event. Can be null.
                                startComponentPane.lookup(startNodeId), // target the target of the event. Can be null.
                                MouseEvent.MOUSE_CLICKED, // eventType The type of the event.
                                startX, // x The x with respect to the source. Should be in scene coordinates if source == null or source is not a Node.
                                startY, // y The y with respect to the source. Should be in scene coordinates if source == null or source is not a Node.
                                0, // screenX The x coordinate relative to screen.
                                0, // screenY The y coordinate relative to screen.
                                MouseButton.PRIMARY, // button the mouse button used
                                1, // clickCount number of click counts
                                false, // shiftDown true if shift modifier was pressed.
                                false, // controlDown true if control modifier was pressed.
                                false, // altDown true if alt modifier was pressed.
                                false, // metaDown true if meta modifier was pressed.
                                true, // primaryButtonDown true if primary button was pressed.
                                false, // middleButtonDown true if middle button was pressed.
                                false, // secondaryButtonDown true if secondary button was pressed.
                                false, // synthesized if this event was synthesized
                                false, // popupTrigger whether this event denotes a popup trigger for current platform
                                false, // stillSincePress see {@link #isStillSincePress() }
                                null // pickResult pick result. Can be null, in this case a 2D pick result
                        );
                        startComponentPane.getOnMouseClicked().handle(mouseEvent);
                    }
                    ;
                    break;
                case DELETED:
                    break;
                case CHANGED:
                    break;
            }
        });
        workspace.forEach((aLong, jsonObject) -> {
            // Get local vars
            String id = (String) jsonObject.get("id");
            switch (StateType.valueOf((String) jsonObject.get("state"))) {
                case CREATED:
                    // TODO: need a multiple workspace manageùment
                    break;
                case DELETED:
                    // TODO: need a multiple workspace manageùment
                    break;
                case CHANGED:
                    workspacePane.setJson(jsonObject);
                    break;
            }
        });
        potentiometers.forEach((aLong, jsonObject) -> {
            // Get local vars
            String id = (String) jsonObject.get("id");
            String component = (String) jsonObject.get("componentId");
            ComponentPane componentPane = workspacePane.getComponent(component);
            switch (StateType.valueOf((String) jsonObject.get("state"))) {
                case CHANGED:
                    if (componentPane == null) {
                        throw new ExceptionInInitializerError("Componant Pane not found for potentiometer changes !");
                    }
                    componentPane.getChildren().forEach(node -> {
                        if (node instanceof Potentiometer && node.getId().equals(id)) {
                            Potentiometer potentiometer = (Potentiometer) node;
                            potentiometer.setJson(jsonObject);
                            System.out.println(potentiometer + " CHANGED");
                        }
                    });
                    workspacePane.setJson(jsonObject);
                    break;
            }
        });
    }

    @Override
    public State next() {
        if (!nextStates.isEmpty()) {
            // Save the current state into previous states
            previousStates.add(currentState);
            // Change curretn state for the first next one
            currentState = nextStates.poll();
            restoreOrigin();
        }
        return currentState;
    }

    @Override
    public State previous() {
        if (!previousStates.isEmpty()) {
            // Save the current state into next states
            nextStates.add(currentState);
            // Change current state for the first previous one
            currentState = previousStates.peek();
            restoreOrigin();
        }
        return currentState;
    }

    private void restoreOrigin() {
        if (currentState.getType().equals(StateType.CREATED))
        currentState.getOrigin().restoreState(currentState);
    }

    @Override
    public void add(Origin origin, StateType type) {
        return;
//        State state = origin.getState();
//        state.setType(type);
//        // Add the current state to previous ones
//        if (currentState != null) {
//            previousStates.add(currentState);
//        }
//        // Remove previous state and origin identically elements
//        previousStates.removeIf(oldState -> oldState.getType().equals(state.getType()) && oldState.getOrigin().getId().equals(state.getOrigin().getId()));
//
//        // And redifine current state
//        currentState = state;
//        // purge next states became invalid
//        nextStates.clear();
//        System.out.println(previousStates);
    }
}
