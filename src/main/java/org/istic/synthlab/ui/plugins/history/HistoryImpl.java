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
import org.istic.synthlab.ui.plugins.cable.CurveCable;
import org.istic.synthlab.ui.plugins.cable.InputPlug;
import org.istic.synthlab.ui.plugins.cable.OutputPlug;
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
            String name = (String) jsonObject.get("name");
            ComponentPane componentPane = workspacePane.getComponent(id);
            switch (StateType.valueOf((String) value.get("state"))) {
                case CREATED:
                    System.out.println("ComponentPane CREATION");
                    if (componentPane != null) {
                        throw new ExceptionInInitializerError("HISTORY: Componant Pane found ?!");
                    }
                    //TODO: maybe it would be better to pas sthrought events to regenerate component
                    componentPane = CoreController.loadComponent(name);
                    componentPane.setJson(jsonObject);
                    workspacePane.addWithDragging(componentPane);
                    System.out.println(componentPane + " CREATED");
                    break;
                case DELETED:
                    System.out.println(componentPane + " DELETION");
                    if (componentPane == null) {
                        throw new ExceptionInInitializerError("HISTORY: Componant Pane not found for deletion !");
                    }
                    CoreController.getConnectionManager().deleteComponent(componentPane);
                    System.out.println(componentPane + " DELETED");
                    break;
                case CHANGED:
                    System.out.println(componentPane + " CHANGES");
                    if (componentPane == null) {
                        throw new ExceptionInInitializerError("HISTORY: Componant Pane not found for changes !");
                    }
                    //workspacePane.getChildren().remove(componentPane);
                    componentPane.setJson(jsonObject);
                    //workspacePane.getChildren().add(componentPane);
                    System.out.println(componentPane + " CHANGED");
                    break;
            }
        });
        cables.forEach((aLong, value) -> {
            // Get local vars
            JSONObject jsonObject = (JSONObject) value.get("content");
            String id = (String) jsonObject.get("id");
            CurveCable cable = null;
            CurveCable.PlugState state = CurveCable.PlugState.valueOf((String) jsonObject.get("state"));

            InputPlug inputPlug = null;
            OutputPlug outputPlug = null;

            if (state == CurveCable.PlugState.PLUGGED || state == CurveCable.PlugState.IN_PLUGGED) {
                final String inComponentId = (String) jsonObject.get("inComponentId");
                final String inputPlugId = (String) jsonObject.get("inputPlug");
                ComponentPane inComponentPane = workspacePane.getComponent(inComponentId);
                inputPlug = (InputPlug) inComponentPane.getChildren().filtered(node -> node.getId().equals(inputPlugId)).get(0);
            }
            if (state == CurveCable.PlugState.PLUGGED || state == CurveCable.PlugState.OUT_PLUGGED) {
                final String outComponentId = (String) jsonObject.get("outComponentId");
                final String outputPlugId = (String) jsonObject.get("outputPlug");
                ComponentPane outComponentPane = workspacePane.getComponent(outComponentId);
                outputPlug = (OutputPlug) outComponentPane.getChildren().filtered(node -> node.getId().equals(outputPlugId)).get(0);
            }
            switch (StateType.valueOf((String) value.get("state"))) {
                case CREATED:
                    if (state == CurveCable.PlugState.IN_PLUGGED) {
                        CoreController.getConnectionManager().plugInput(inputPlug);
                    } else if (state == CurveCable.PlugState.OUT_PLUGGED) {
                        CoreController.getConnectionManager().plugOutput(outputPlug);
                    }
                    break;
                case DELETED:
                    cable = (CurveCable) workspacePane.lookup(id);
                    CoreController.getConnectionManager().deleteCable(cable);
                    break;
                case CHANGED:
                    cable = (CurveCable) workspacePane.lookup(id);
                    cable.setJson(jsonObject);
                    if (state == CurveCable.PlugState.PLUGGED) {
                        cable.deconnectInputPlug();
                        cable.deconnectOutputPlug();
                        cable.connectInputPlug(inputPlug);
                        cable.connectOutputPlug(outputPlug);
                    } else if (state == CurveCable.PlugState.UNPLUGGED) {
                        cable.deconnectInputPlug();
                        cable.deconnectOutputPlug();
                    } else if (state == CurveCable.PlugState.IN_PLUGGED) {
                        cable.deconnectInputPlug();
                        cable.deconnectOutputPlug();
                        cable.connectInputPlug(inputPlug);
                    } else if (state == CurveCable.PlugState.OUT_PLUGGED) {
                        cable.deconnectInputPlug();
                        cable.deconnectOutputPlug();
                        cable.connectOutputPlug(outputPlug);
                    }
                    break;
            }
        });
        workspace.forEach((aLong, value) -> {
            // Get local vars
            JSONObject jsonObject = (JSONObject) value.get("content");
            String id = (String) jsonObject.get("id");
            switch (StateType.valueOf((String) value.get("state"))) {
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
        potentiometers.forEach((aLong, value) -> {
            // Get local vars
            JSONObject jsonObject = (JSONObject) value.get("content");
            String id = (String) jsonObject.get("id");
            String component = (String) jsonObject.get("componentId");
            ComponentPane componentPane = workspacePane.getComponent(component);
            switch (StateType.valueOf((String) value.get("state"))) {
                case CHANGED:
                    if (componentPane == null) {
                        throw new ExceptionInInitializerError("HISTORY: Componant Pane not found for potentiometer changes !");
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
        State state = origin.getState();
        state.setType(type);
        // Add the current state to previous ones
        if (currentState != null) {
            previousStates.add(currentState);
        }
        // Remove previous state and origin identically elements
        //previousStates.removeIf(oldState -> oldState.getType().equals(state.getType()) && oldState.getOrigin().getId().equals(state.getOrigin().getId()));

        // And redefine current state
        currentState = state;
        System.out.println(currentState.getType() + " [" + state.getTime() + "]=> " + currentState.getContent());
        // purge next states became invalid
        nextStates.clear();
    }

    public void purge() {
        previousStates.clear();
        nextStates.clear();
        currentState = null;
    }
}
