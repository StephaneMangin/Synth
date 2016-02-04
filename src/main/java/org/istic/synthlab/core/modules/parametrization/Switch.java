package org.istic.synthlab.core.modules.parametrization;

/**
 * Manager for boolean value inside views.
 *
 * @author Stéphane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 * @author Cyprien Gottstein <gottstein[dot]cyprien[at]gmail[dot]com>
 *
 */
public class Switch extends GenericsParam<Boolean> {

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
