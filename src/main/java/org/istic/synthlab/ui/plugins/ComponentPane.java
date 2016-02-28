package org.istic.synthlab.ui.plugins;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.istic.synthlab.components.IController;
import org.istic.synthlab.ui.CoreController;
import org.istic.synthlab.ui.plugins.cable.InputPlug;
import org.istic.synthlab.ui.plugins.cable.OutputPlug;
import org.istic.synthlab.ui.plugins.history.Origin;
import org.istic.synthlab.ui.plugins.history.State;
import org.istic.synthlab.ui.plugins.history.StateType;

/**
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class ComponentPane extends AnchorPane implements Origin {

    private String name;
    private IController controller;

    public ComponentPane() {
        super();
        layoutXProperty().addListener((observable, oldValue, newValue) -> {
            CoreController.getConnectionManager().getHistory().add(this, StateType.CHANGED);
        });
        layoutYProperty().addListener((observable, oldValue, newValue) -> {
            CoreController.getConnectionManager().getHistory().add(this, StateType.CHANGED);
        });
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setJson(JSONObject state) {

        state.forEach((s, o) -> {
            switch(s) {
                case "layoutX":
                    setLayoutX((double)o);
                    break;
                case "layoutY":
                    setLayoutY((double)o);
                    break;
                case "id":
                    setId((String)o);
                    break;
                case "name":
                    setName((String)o);
                    break;
                default:
                    // Do nothing yet
            }
        });
        relocate(getLayoutX(), getLayoutY());
        // Update the layout in the parent
        requestLayout();
    }

    @Override
    public JSONObject getJson() {
        JSONObject obj = new JSONObject();
        System.out.println(getLayoutX());
        System.out.println(getLayoutY());
        obj.put("layoutX", getLayoutX());
        obj.put("layoutY", getLayoutY());
        obj.put("type", "component");
        obj.put("id", getId());
        obj.put("name", getName());
        return obj;
    }

    @Override
    public State getState() {
        return new State(this);
    }

    @Override
    public void restoreState(State state) {
        setJson(state.getContent());
    }

    public IController getController() {
        return controller;
    }

    public void setController(IController controller) {
        this.controller = controller;
    }

    public InputPlug getinInputPlugFromId(String id) {
        System.out.println("ID: " + id);
        for (Node node: getChildren()) {
            if (node instanceof InputPlug && node.getId().equals(id)) {
                return (InputPlug) node;
            }
        }
        return null;
    }

    public OutputPlug getinOutputPlugFromId(String id) {
        System.out.println("ID: " + id);
        for (Node node: getChildren()) {
            if (node instanceof OutputPlug && node.getId().equals(id)) {
                return (OutputPlug) node;
            }
        }
        return null;
    }
}
