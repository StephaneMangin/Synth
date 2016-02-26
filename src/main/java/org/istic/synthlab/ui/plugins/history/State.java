package org.istic.synthlab.ui.plugins.history;

import net.minidev.json.JSONObject;

/**
 * Manages a state from ab origin object, memento class from memento pattern
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class State {

    private JSONObject content;
    private Origin origin;
    private StateType type;
    private long time;

    public State(Origin origin) {
        content = origin.getJson();
        this.origin = origin;
    }

    public JSONObject getContent() {
        return content;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setType(StateType type) {
        this.type = type;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public StateType getType() {
        return type;
    }
}
