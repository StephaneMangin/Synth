package org.istic.synthlab.ui.plugins.history;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;
import net.minidev.json.parser.JSONParser;

import java.io.*;
import java.util.*;

/**
 * Implementation of the care taker from memento pattern
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public abstract class ACareTaker extends Observable implements ICareTaker {

    private State currentOrigin;
    private Queue<State> previousStates = new ArrayDeque<>();
    private Queue<State> nextStates = new ArrayDeque<>();

    @Override
    public void save() throws IOException {
        JSONObject content = new JSONObject();
        File file = new File(System.getProperty("java.io.tmpdir"));
        FileWriter writer = new FileWriter(file);
        previousStates.forEach(state -> {
            content.put(state.toString(), state.getContent());
        });

        System.out.println(content.toJSONString(JSONStyle.MAX_COMPRESS));
        writer.write(content.toJSONString(JSONStyle.MAX_COMPRESS));
    }

    @Override
    public void load(File file) throws Exception {
        JSONParser parser = new JSONParser();
        Reader reader = new FileReader(file);
        JSONObject content = (JSONObject) parser.parse(reader);
        // TODO: create the algorythm to recreate all objects from json content
    }

    @Override
    public void next() {
        if (!nextStates.isEmpty()) {
            // Save the current state into previous states
            previousStates.add(currentOrigin);
            // Change curretn state for the first next one
            currentOrigin = nextStates.peek();
            restoreOrigin();
        }
    }

    @Override
    public void previous() {
        if (!previousStates.isEmpty()) {
            // Save the current state into next states
            nextStates.add(currentOrigin);
            // Change current state for the first previous one
            currentOrigin = previousStates.peek();
            restoreOrigin();
        }
    }

    private void restoreOrigin() {
        currentOrigin.getOrigin().restore(currentOrigin);
    }

    @Override
    public void saveState(State state, StateType type) {
        state.setType(type);
        // Add the current state to previous ones
        previousStates.add(currentOrigin);
        // And redifine current state
        currentOrigin = state;
        // purge next states became invalid
        nextStates.clear();
    }
}
