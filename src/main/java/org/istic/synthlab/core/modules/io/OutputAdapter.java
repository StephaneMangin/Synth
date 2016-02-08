package org.istic.synthlab.core.modules.io;

import com.jsyn.ports.UnitOutputPort;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.services.Register;


/**
 * @author  Group1
 * The type Output adapter
 */
public class OutputAdapter implements IOutput {

    private UnitOutputPort unitOutputPort;

    /**
     * Instantiates a new Output adapter.
     *
     * @param unitOutputPort the unit output port
     */
    public OutputAdapter(IComponent component, UnitOutputPort unitOutputPort) {
        this.unitOutputPort = unitOutputPort;
        // Declare this association to the register
        Register.declare(component, this, unitOutputPort);
    }

    /**
     * Connect the OutputAdapter to the input
     *
     * @param in:IInput
     */
    public void connect(IInput in) {
        Register.connect(in, this);
    }

    @Override
    public void deconnect() {
        Register.disconnect(this);
    }

    @Override
    public String toString() {
        return unitOutputPort.getName();
    }
}
