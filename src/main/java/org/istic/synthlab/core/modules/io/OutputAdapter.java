package org.istic.synthlab.core.modules.io;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import org.istic.synthlab.core.services.IOMapping;


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
    public OutputAdapter(UnitOutputPort unitOutputPort) {
        this.unitOutputPort = unitOutputPort;
    }

    /**
     * Connect the OutputAdapter to the input
     *
     * @param in:IInput
     */
    public void connect(IInput in) {
        IOMapping.connect(in, this);
    }

    @Override
    public void deconnect() {
        IOMapping.disconnect(this);
    }

    @Override
    public UnitOutputPort getUnitInputPort() {
        return unitOutputPort;
    }

}
