package org.istic.synthlab.core.modules.io;

import com.jsyn.ports.UnitInputPort;
import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.services.Register;

/**
 *
 * The Input adapter class
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class Input implements IInput {

    private UnitInputPort unitInputPort;
    private IComponent component;
    private String name;

    /**
     * Instantiates a new Input adapter.
     *
     * @param unitInputPort the unit input port
     */
    public Input(String name, IComponent component, UnitInputPort unitInputPort) {
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
    public String getName() {
        return this.name;
    }

    @Override
    public IComponent getComponent() {
        return this.component;
    }

    @Override
    public UnitInputPort getUnitInputPort() {
        return unitInputPort;
    }

    @Override
    public String toString() {
        return this.component + "::" + this.getName() + "<" + this.hashCode() + ">";
    }

    @Override
    public void activate() {
    }

    @Override
    public void deactivate() {
    }

    @Override
    public boolean isActivated() {
        return true;
    }
}
