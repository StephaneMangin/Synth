package org.istic.synthlab.core.utils.parametrization;

import com.jsyn.ports.UnitInputPort;

/**
 * Manager for boolean value inside views.
 *
 * @author Stéphane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 * @author Cyprien Gottstein <gottstein[dot]cyprien[at]gmail[dot]com>
 *
 */
public class Switch extends GenericsParam<Boolean> {

    private boolean defaultValue = false;
    private UnitInputPort port;

    /**
     * Instantiates a new Switch.
     *
     * @param label the label
     * @param port   the port
     * @param defaultValue the value
     */
    public Switch(String label, UnitInputPort port, Boolean defaultValue) {
        super(label, defaultValue);
        this.port = port;
    }

}
