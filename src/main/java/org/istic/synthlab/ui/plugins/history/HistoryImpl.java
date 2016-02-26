package org.istic.synthlab.ui.plugins.history;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;
import net.minidev.json.parser.JSONParser;
import org.istic.synthlab.ui.ConnectionManager;
import org.istic.synthlab.ui.CoreController;
import org.istic.synthlab.ui.plugins.ComponentPane;
import org.istic.synthlab.ui.plugins.WorkspacePane;

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
            JSONObject localContent = state.getContent();
            System.out.println(localContent);
            localContent.put("id", state.getOrigin().getId());
            list.add(localContent);
        });
        content.put(String.valueOf(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)), list);
        System.out.println(content.toJSONString(JSONStyle.MAX_COMPRESS));
        writer.write(content.toJSONString(JSONStyle.NO_COMPRESS));
        writer.flush();
        writer.close();
    }

    @Override
    public void load(File file, WorkspacePane workspacePane) throws Exception {
        JSONParser parser = new JSONParser();
        Reader reader = new FileReader(file);
        // TODO: create the algorythm to recreate all objects from json content
        ((JSONObject) parser.parse(reader)).forEach((s, o) -> {
            if (o instanceof JSONArray) {
                JSONArray array = (JSONArray) o;
                array.forEach(o1 -> {
                    JSONObject obj = (JSONObject) o1;
                    if (obj.get("type").equals("component")) {
                        //TODO: maybe it would be better to pas sthrought events to regenerate component
                        System.out.println(obj.toJSONString(JSONStyle.NO_COMPRESS));
                        ComponentPane componentPane = CoreController.loadComponent((String) obj.get("component"));
                        componentPane.setJson(obj);
                        componentPane.setId((String) obj.get("id"));
                        workspacePane.addWithDragging(componentPane);
                    }
                });
            }
        });;
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
        System.out.println("HISTORY: " + origin + " with " + type + " type");
        State state = origin.getState();
        state.setTime(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
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
