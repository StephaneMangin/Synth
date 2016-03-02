package org.istic.synthlab.core.modules.io;

import com.jsyn.ports.UnitOutputPort;
import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.modules.IModule;

/**
 *
 *
 * The interface Output port.
 */
public interface IOutput extends IModule {

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
    void disconnect();

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

    /**
     * Return the Jsyn port
     *
     * @return
     */
    // FIXME: we can't do that, retreiving a jsyn inside an adapter !!!
    UnitOutputPort getUnitOutputPort();
}
