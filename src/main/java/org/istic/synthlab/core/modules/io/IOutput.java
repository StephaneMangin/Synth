package org.istic.synthlab.core.modules.io;

import com.jsyn.ports.UnitOutputPort;

/**
 * @author Group1
 *
 * The interface Output.
 */
public interface IOutput {

    /**
     * Connect the output to an input port
     *
     * @param in:IInput
     */
    void connect(IInput in);
    void deconnect();


    UnitOutputPort getUnitInputPort();
}
