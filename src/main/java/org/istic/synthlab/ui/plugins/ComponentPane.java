package org.istic.synthlab.ui.plugins;

import javafx.scene.layout.AnchorPane;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.istic.synthlab.components.IController;
import org.istic.synthlab.ui.CoreController;
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
                    System.out.println("x set");
                    setLayoutX((double)o);
                    break;
                case "layoutY":
                    System.out.println("y set");
                    setLayoutY((double)o);
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
        StringBuffer buffer = new StringBuffer();
        JSONObject obj = new JSONObject();
        System.out.println(getLayoutX());
        System.out.println(getLayoutY());
        obj.put("layoutX", getLayoutX());
        obj.put("layoutY", getLayoutY());
        obj.put("type", "component");
        obj.put("component", getName());
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


}
