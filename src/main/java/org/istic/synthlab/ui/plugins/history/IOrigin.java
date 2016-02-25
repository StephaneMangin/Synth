package org.istic.synthlab.ui.plugins.history;

import net.minidev.json.JSONObject;

/**
 * Manages states origins, originator from memento pattern
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public interface IOrigin {

    /**
     * Change current state content
     *
     * @param state
     */
    void setJson(JSONObject state);

    /**
     * Return the current state content
     *
     * @return
     */
    JSONObject getJson();

    /**
     * Returns the current state
     *
     * @return
     */
    State save();

    /**
     * Restore state for State
     *
     * @param state
     */
    void restore(State state);
}
