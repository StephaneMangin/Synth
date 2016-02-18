package org.istic.synthlab.core.modules.io;

import com.jsyn.ports.UnitInputPort;
import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.modules.IModule;

/**
 *
 * The interface Input port.
 *
 */
public interface IInput extends IModule {

    /**
     * Connect.
     * It connects the input port to the output port
     *
     * @param out the out
     */
    void connect(IOutput out);

    /**
     * Deconnect.
     * It disconnects the input port to the output port
     *
     */
    void deconnect();


    /**
     * Returns the name of the input port.
     *
     * @return String
     */
    String getName();

    /**
     * Returns the component of the input port.
     *
     * @return IComponent
     */
    IComponent getComponent();
    UnitInputPort getUnitInputPort();
}
