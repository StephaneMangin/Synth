package org.istic.synthlab.core.modules.io;

import com.jsyn.ports.UnitInputPort;
import org.istic.synthlab.core.services.IOMapping;

/**
 * @author Group1
 * The type Input adapter
 */
public class InputAdapter implements IInput {

    private UnitInputPort unitInputPort;

    /**
     * Instantiates a new Input adapter.
     *
     * @param unitInputPort the unit input port
     */
    public InputAdapter(UnitInputPort unitInputPort) {
        this.unitInputPort = unitInputPort;
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
