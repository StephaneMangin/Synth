package org.istic.synthlab.ui.history;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;
import net.minidev.json.parser.JSONParser;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.ui.CoreController;
import org.istic.synthlab.ui.plugins.workspace.ComponentPane;
import org.istic.synthlab.ui.plugins.workspace.WorkspacePane;
import org.istic.synthlab.ui.plugins.workspace.CurveCable;
import org.istic.synthlab.ui.plugins.plug.InputPlug;
import org.istic.synthlab.ui.plugins.plug.OutputPlug;
import org.istic.synthlab.ui.plugins.controls.Potentiometer;

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
            values.put("time", state.getTime());
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
        TreeMap<Long, JSONObject> components = new TreeMap<>();
        TreeMap<Long, JSONObject> cables = new TreeMap<>();
        TreeMap<Long, JSONObject> workspace = new TreeMap<>();
        TreeMap<Long, JSONObject> potentiometers = new TreeMap<>();

        Factory.uglyResetSynthesizer();

        ((JSONObject) parser.parse(reader)).forEach((s, o) -> {
            if (o instanceof JSONArray) {
                JSONArray array = (JSONArray) o;
                array.forEach(o1 -> {
                    // Get global vars
                    JSONObject obj = (JSONObject) o1;
                    Long time = (Long) obj.get("time");
                    JSONObject content = (JSONObject) obj.get("content");
                    String originType = (String) content.get("type");

                    switch(originType) {
                        case "component":
                            components.put(time, obj);
                            break;
                        case "cable":
                            cables.put(time, obj);
                            break;
                        case "workspace":
                            workspace.put(time, obj);
                            break;
                        case "potentiometer":
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
                    //TODO: maybe it would be better to pas sthrought events to regenerate component
                    componentPane = CoreController.loadComponent(name);
                    componentPane.setJson(jsonObject);
                    workspacePane.addWithDragging(componentPane);
                    break;
                case DELETED:
                    if (componentPane == null) {
                        throw new ExceptionInInitializerError("HISTORY: Componant Pane (id:" + id + ") not found !");
                    }
                    CoreController.getConnectionManager().deleteComponent(componentPane);
                    break;
                case CHANGED:
                    if (componentPane == null) {
                        throw new ExceptionInInitializerError("HISTORY: Componant Pane (id:" + id + ") not found !");
                    }
                    componentPane.setJson(jsonObject);
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
            switch (StateType.valueOf((String) value.get("state"))) {
                case DELETED:
                    break;
                case CHANGED:
                    if (state == CurveCable.PlugState.PLUGGED) {
                        // Get all components for component ids
                        final String inComponentId = (String) jsonObject.get("inComponentId");
                        final String inputPlugId = (String) jsonObject.get("inputPlug");
                        ComponentPane inComponentPane = workspacePane.getComponent(inComponentId);
                        final String outComponentId = (String) jsonObject.get("outComponentId");
                        final String outputPlugId = (String) jsonObject.get("outputPlug");
                        ComponentPane outComponentPane = workspacePane.getComponent(outComponentId);
                        if (inComponentPane == null || outComponentPane == null) {
                            throw new ExceptionInInitializerError("HISTORY: Componant Pane not found for plug !");
                        }
                        // Get plugs
                        inputPlug = inComponentPane.getinInputPlugFromId(inputPlugId);
                        outputPlug = outComponentPane.getinOutputPlugFromId(outputPlugId);

                        // Finally plug cables
                        CoreController.getConnectionManager().plugInput(inputPlug);
                        CoreController.getConnectionManager().plugOutput(outputPlug);
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
            String componentId = (String) jsonObject.get("componentId");
            ComponentPane componentPane = workspacePane.getComponent(componentId);
            if (componentPane == null) {
                throw new ExceptionInInitializerError("HISTORY: Componant Pane not found for potentiometer !");
            }
            switch (StateType.valueOf((String) value.get("state"))) {
                case CHANGED:
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
            previousStates.add(nextStates.poll());
            restoreOrigin();
        }
        return previousStates.peek();
    }

    @Override
    public State previous() {
        if (!previousStates.isEmpty()) {
            // Save the current state into next states
            nextStates.add(previousStates.poll());
            restoreOrigin();
        }
        return previousStates.peek();
    }

    private void restoreOrigin() {
        previousStates.peek().getOrigin().restoreState(previousStates.peek());
    }

    @Override
    public void add(Origin origin, StateType type) {
        State state = origin.getState();
        state.setType(type);
        // Remove previous state and origin identically elements
//        previousStates.removeIf(oldState -> {
//                    if (oldState.getOrigin() instanceof Potentiometer && state.getOrigin() instanceof Potentiometer) {
//                        return oldState.getOrigin().getId().equals(state.getOrigin().getId())
//                                && oldState.getType() == state.getType()
//                                && oldState.getContent().get("componentId").equals(state.getContent().get("componentId"));
//                    } else {
//                        return oldState.getOrigin().getId().equals(state.getOrigin().getId())
//                                && oldState.getType() == state.getType();
//                    }
//                }
//        );
        // Add the current state to previous ones
        previousStates.add(state);
        // purge next states became invalid
        nextStates.clear();
    }

    public void purge() {
        previousStates.clear();
        nextStates.clear();
    }
}
