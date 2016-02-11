package org.istic.synthlab.core;

/**
 * @author  Group1
 * Created by Dechaud John Marc and Ngassam Paola on 2/3/16.
 */
public interface Resource {

    /**
     * Activate a component.
     */
    void activate();

    /**
     * Deactivate a component.
     */
    void deactivate();

    /**
     * Get the current state of a component.
     *
     * @return boolean
     */
    boolean isActivated();

}
