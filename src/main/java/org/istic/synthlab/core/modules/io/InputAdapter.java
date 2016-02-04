package org.istic.synthlab.core.modules.io;

import com.jsyn.ports.UnitInputPort;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.services.IOMapping;

/**
 *
 * The type Input adapter
 */
public class InputAdapter implements IInput {

    private UnitInputPort unitInputPort;

    /**
     * Instantiates a new Input adapter.
     *
     * @param unitInputPort the unit input port
     */
    public InputAdapter(IComponent component, UnitInputPort unitInputPort) {
        this.unitInputPort = unitInputPort;
        IOMapping.declare(component, this, unitInputPort);
    }

    /**
     * Connect the inputAdapter to the output
     *
     * @param out:IOutput
     */

    public void connect(IOutput out) {
        IOMapping.connect(this, out);
    }

    @Override
    public void deconnect() {
        IOMapping.disconnect(this);
    }

    @Override
    public UnitInputPort getUnitInputPort() {
        return unitInputPort;
    }

}
