package org.istic.synthlab.ui.plugins.history;

import net.minidev.json.JSONObject;

/**
 * Manages a state from ab origin object, memento class from memento pattern
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class State {

    private JSONObject content;
    private IOrigin origin;
    private StateType type;

    public State(IOrigin origin) {
        content = origin.getJson();
        this.origin = origin;
    }

    public JSONObject getContent() {
        return content;
    }

    public IOrigin getOrigin() {
        return origin;
    }

    public void setType(StateType type) {
        this.type = type;
    }
}
