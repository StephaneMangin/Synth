package org.istic.synthlab.ui.plugins.history;

import java.io.File;
import java.io.IOException;

/**
 * Care taker from memento pattern
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public interface ICareTaker {

    /**
     * Save all states inside a file
     *
     */
    void save() throws IOException;

    /**
     * Load all states from a file
     *
     * @param file
     */
    void load(File file) throws Exception;

    /**
     * Returns the next state
     *
     */
    void next();

    /**
     * Returns the previous state
     *
     */
    void previous();

    /**
     * Add a new state
     *
     */
    void saveState(State state, StateType type);
}
