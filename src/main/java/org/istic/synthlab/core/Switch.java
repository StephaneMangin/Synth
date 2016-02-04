package org.istic.synthlab.core;

/**
 * @author  Group1
 * The class Switch.
 */
public class Switch extends Params<Boolean> {

    private boolean defaultValue = false;

    /**
     * Instantiates a new Switch.
     *
     * @param label the label
     * @param max   the max value
     * @param min   the min value
     * @param value the value
     */
    public Switch(String label, Boolean max, Boolean min, Boolean value) {
        super(label, max, min, value);
    }

}
