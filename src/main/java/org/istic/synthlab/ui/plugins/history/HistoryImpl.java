package org.istic.synthlab.ui.plugins.history;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;
import net.minidev.json.parser.JSONParser;
import org.istic.synthlab.ui.ConnectionManager;
import org.istic.synthlab.ui.CoreController;
import org.istic.synthlab.ui.plugins.ComponentPane;
import org.istic.synthlab.ui.plugins.WorkspacePane;
import org.istic.synthlab.ui.plugins.cable.CurveCable;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
            values.put("type", state.getType());
            values.put("time", state.getTime());
            JSONObject stateContent = state.getContent();
            stateContent.put("id", state.getOrigin().getId());
            values.put("content", stateContent);
            list.add(values);
        });
        content.put(String.valueOf(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)), list);
        writer.write(content.toJSONString(JSONStyle.NO_COMPRESS));
        writer.flush();
        writer.close();
    }

    @Override
    @SuppressWarnings("deprecated")
    public void load(File file, WorkspacePane workspacePane) throws Exception {
        JSONParser parser = new JSONParser();
        Reader reader = new FileReader(file);
        // Cut content in the different functional types of states time ordered with a TreeMap
        TreeMap<Long, JSONObject> components = new TreeMap<>();
        TreeMap<Long, JSONObject> cables = new TreeMap<>();
        TreeMap<Long, JSONObject> workspace = new TreeMap<>();
        ((JSONObject) parser.parse(reader)).forEach((s, o) -> {
            if (o instanceof JSONArray) {
                JSONArray array = (JSONArray) o;
                array.forEach(o1 -> {
                    // Get global vars
                    JSONObject obj = (JSONObject) o1;
                    long time = Long.getLong((String) obj.get("type"));
                    JSONObject content = (JSONObject) obj.get("content");
                    String type = (String) content.get("type");

                    switch(type) {
                        case "component":
                            components.put(time, content);
                        case "cable":
                            components.put(time, content);
                        case "workspace":
                            components.put(time, content);
                    }
                });
            }
        });
        // Check for each functionnal types and act dependently state types
        components.forEach((aLong, jsonObject) -> {
            // Get local vars
            StateType type = StateType.valueOf((String) jsonObject.get("type"));
            String id = (String) jsonObject.get("id");
            String component = (String) jsonObject.get("component");
            ComponentPane componentPane = workspacePane.getComponent(id);
            switch (type) {
                case CREATED:
                    if (componentPane != null) {
                        throw new ExceptionInInitializerError();
                    }
                    //TODO: maybe it would be better to pas sthrought events to regenerate component
                    componentPane = CoreController.loadComponent(component);
                    componentPane.setJson(jsonObject);
                    workspacePane.addWithDragging(componentPane);
                    break;
                case DELETED:
                    if (componentPane != null) {
                        CoreController.getConnectionManager().deleteComponent(componentPane);
                    }
                case CHANGED:
                    if (componentPane != null) {
                        componentPane.setJson(jsonObject);
                    }
            }
        });
        cables.forEach((aLong, jsonObject) -> {
            // Get local vars
            StateType type = StateType.valueOf((String) jsonObject.get("type"));
            String id = (String) jsonObject.get("id");
            switch (type) {
                case CREATED:
                    break;
                case DELETED:
                    break;
                case CHANGED:
                    break;
            }
        });
        workspace.forEach((aLong, jsonObject) -> {
            // Get local vars
            StateType type = StateType.valueOf((String) jsonObject.get("type"));
            String id = (String) jsonObject.get("id");
            switch (type) {
                case CREATED:
                    break;
                case DELETED:
                    break;
                case CHANGED:
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
        // And redifine current state
        currentState = state;
        // purge next states became invalid
        nextStates.clear();
        System.out.println(previousStates);
    }
}
