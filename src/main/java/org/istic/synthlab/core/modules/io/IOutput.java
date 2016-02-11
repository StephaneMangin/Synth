package org.istic.synthlab.core.modules.io;

import com.jsyn.ports.UnitOutputPort;
import org.istic.synthlab.components.IComponent;

/**
 *
 *
 * The interface Output port.
 */
public interface IOutput {

    /**
     * Connects the output port to an input port
     *
     * @param in:IInput
     */
    void connect(IInput in);

    /**
     * Disconnects the output port to an input port
     *
     */
    void deconnect();

    /**
     * Returns the name of the output port.
     *
     * @return String
     */
    String getName();

    /**
     * Returns the component of the output port.
     *
     * @return IComponent
     */
    IComponent getComponent();
    UnitOutputPort getUnitOutputPort();
}
