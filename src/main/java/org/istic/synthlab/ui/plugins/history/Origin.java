package org.istic.synthlab.ui.plugins.history;

import net.minidev.json.JSONObject;

/**
 * Manages states origins, originator from memento pattern
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public interface Origin {

    /**
     * Returns the reference named
     *
     * @return
     */
    String getName();

    /**
     * Set the reference named
     *
     * @return
     */
    void setName(String name);

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
    State getState();

    /**
     * Restore state for State
     *
     * @param state
     */
    void restoreState(State state);

    /**
     * Returns the unique id of this origin
     *
     * @return
     */
    String getId();
}
