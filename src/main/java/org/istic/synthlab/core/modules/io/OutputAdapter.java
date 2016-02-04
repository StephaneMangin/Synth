package org.istic.synthlab.core.modules.io;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import org.istic.synthlab.core.IOMappingService;


public class OutputAdapter implements IOutput {

    private UnitOutputPort unitOutputPort;

    public OutputAdapter(UnitOutputPort unitOutputPort) {
        this.unitOutputPort = unitOutputPort;
    }

    public void connect(IInput in) {
        IOMappingService.connect(in, this);
    }

    @Override
    public UnitOutputPort getUnitInputPort() {
        return unitOutputPort;
    }

}
