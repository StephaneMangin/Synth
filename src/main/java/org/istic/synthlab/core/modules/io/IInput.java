package org.istic.synthlab.core.modules.io;

import com.jsyn.ports.UnitInputPort;

/**
 *
 * The interface Input.
 *
 */
public interface IInput {

    /**
     * Connect.
     * It connects the input port to the output port
     *
     * @param out the out
     */
    void connect(IOutput out);
    void deconnect();

    UnitInputPort getUnitInputPort();

    String getName();

}
