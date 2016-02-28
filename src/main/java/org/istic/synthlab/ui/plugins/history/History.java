package org.istic.synthlab.ui.plugins.history;

import net.minidev.json.JSONObject;
import org.istic.synthlab.ui.ConnectionManager;
import org.istic.synthlab.ui.plugins.WorkspacePane;

import java.io.File;
import java.io.IOException;

/**
 * Care taker from memento pattern
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public interface History {

    /**
     * Save all states inside a file
     *
     */
    void save(File file) throws IOException;

    /**
     * Load all states from a file
     *
     * @param file
     */
    void load(File file, WorkspacePane workspacePane) throws Exception;

    /**
     * Returns the next state
     *
     */
    State next();

    /**
     * Returns the previous state
     *
     */
    State previous();

    /**
     * Add a new state
     *
     */
    void add(Origin origin, StateType type);

    /**
     * Clean all the history
     *
     */
    void purge();
}
