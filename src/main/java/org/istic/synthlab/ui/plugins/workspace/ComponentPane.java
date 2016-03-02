package org.istic.synthlab.ui.plugins.workspace;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import net.minidev.json.JSONObject;
import org.istic.synthlab.core.components.IController;
import org.istic.synthlab.ui.CoreController;
import org.istic.synthlab.ui.plugins.plug.InputPlug;
import org.istic.synthlab.ui.plugins.plug.OutputPlug;
import org.istic.synthlab.ui.plugins.controls.Potentiometer;
import org.istic.synthlab.ui.history.Origin;
import org.istic.synthlab.ui.history.State;
import org.istic.synthlab.ui.history.StateType;

import java.util.ArrayList;
import java.util.List;

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
        for (Node node: getChildren()) {
            if (node instanceof InputPlug && node.getId().equals(id)) {
                return (InputPlug) node;
            }
        }
        return null;
    }

    public OutputPlug getinOutputPlugFromId(String id) {
        for (Node node: getChildren()) {
            if (node instanceof OutputPlug && node.getId().equals(id)) {
                return (OutputPlug) node;
            }
        }
        return null;
    }

    public Potentiometer getinPotentiometerFromId(String id) {
        System.out.println("ID: " + id);
        for (Node node: getChildren()) {
            if (node instanceof Potentiometer && node.getId().equals(id)) {
                return (Potentiometer) node;
            }
        }
        return null;
    }

    /**
     * Returns all input plugs from this component
     *
     * @return
     */
    public Iterable<InputPlug> getInputPlugs() {
        List<InputPlug> result = new ArrayList<>();
        getChildrenUnmodifiable().forEach(node -> {
            if (node instanceof InputPlug) {
                result.add((InputPlug) node);
            }
        });
        return result;
    }

    /**
     * Returns all output plugs from this component
     *
     * @return
     */
    public Iterable<OutputPlug> getOutputPlugs() {
        List<OutputPlug> result = new ArrayList<>();
        getChildrenUnmodifiable().forEach(node -> {
            if (node instanceof OutputPlug) {
                result.add((OutputPlug)node);
            }
        });
        return result;
    }
}
