package org.istic.synthlab.ui.plugins;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.layout.AnchorPane;
import net.minidev.json.JSONObject;
import org.istic.synthlab.ui.plugins.history.IOrigin;
import org.istic.synthlab.ui.plugins.history.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by blacknight on 25/02/16.
 */
public class Component extends AnchorPane implements IOrigin {

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
                default:
                    // Do nothing yet
            }
        });
    }

    @Override
    public JSONObject getJson() {
        StringBuffer buffer = new StringBuffer();
        JSONObject obj = new JSONObject();
        obj.put("layoutX", getLayoutX());
        obj.put("layoutY", getLayoutY());
        return obj;
    }

    @Override
    public State save() {
        return new State(this);
    }

    @Override
    public void restore(State state) {
        setJson(state.getContent());
        notifyAll();
    }

}
