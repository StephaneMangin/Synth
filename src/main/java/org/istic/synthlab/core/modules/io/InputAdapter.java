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
    private IComponent component;
    private String name;

    /**
     * Instantiates a new Input adapter.
     *
     * @param unitInputPort the unit input port
     */
    public InputAdapter(String name, IComponent component, UnitInputPort unitInputPort) {
        this.name = name;
        this.unitInputPort = unitInputPort;
        this.component = component;
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

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.component + "::" + this.getName() + "<" + this.hashCode() + ">";
    }
}
