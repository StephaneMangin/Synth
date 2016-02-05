package org.istic.synthlab.core.modules.io;

import com.jsyn.ports.UnitInputPort;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.services.Register;

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
        // Declare this association to the register
        Register.declare(component, this, unitInputPort);
    }

    /**
     * Connect the inputAdapter to the output
     *
     * @param out:IOutput
     */

    public void connect(IOutput out) {
        Register.connect(this, out);
    }

    @Override
    public void deconnect() {
        Register.disconnect(this);
    }

    @Override
    public UnitInputPort getUnitInputPort() {
        return this.unitInputPort;
    }

}
