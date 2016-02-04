package org.istic.synthlab.core.modules.io;

import com.jsyn.ports.UnitOutputPort;
import org.istic.synthlab.core.services.IOMapping;


public class OutputAdapter implements IOutput {

    private UnitOutputPort unitOutputPort;

    public OutputAdapter(UnitOutputPort unitOutputPort) {
        this.unitOutputPort = unitOutputPort;
    }

    public void connect(IInput in) {
        IOMapping.connect(in, this);
    }

    @Override
    public void deconnect() {
        IOMapping.disconnect(this);
    }

}
