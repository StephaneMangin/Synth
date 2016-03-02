package org.istic.synthlab.core.modules.io;

import com.jsyn.ports.UnitGatePort;
import com.jsyn.ports.UnitInputPort;
import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.services.Register;

/**
 * @author Dechaud John Marc johnmarcdechaud[at]gmail[dot]com on 2/24/16.
 */
public class InputGate implements IInput{

    private UnitGatePort gatePort;
    private String name;
    private IComponent component;

    public InputGate(String name, IComponent component, UnitInputPort unitInputPort) {
        this.gatePort = (UnitGatePort) unitInputPort;
        this.name = name;
        this.component = component;
        // Declare this association to the register
        Register.declare(component, this, gatePort);
    }


    /**

     * Connect.
     * It connects the input port to the output port
     *
     * @param out the out
     */
    @Override
    public void connect(IOutput out) {
        Register.connect(this, out);
    }

    /**
     * Disconnect.
     * It disconnects the input port to the output port
     */
    @Override
    public void disconnect() {

    }

    /**
     * Returns the name of the input port.
     *
     * @return String
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns the component of the input port.
     *
     * @return IComponent
     */
    @Override
    public IComponent getComponent() {
        return this.component;
    }

    @Override
    public UnitInputPort getUnitInputPort() {
        return this.gatePort;
    }

    @Override
    public void activate() {
        this.gatePort.on();
    }

    @Override
    public void deactivate() {
        this.gatePort.off();
    }

    @Override
    public boolean isActivated() {
        if (this.gatePort.isOff())
            return false;
        else
            return true;
    }

}
