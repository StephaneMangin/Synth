package org.istic.synthlab.core.modules.io;

import com.jsyn.ports.UnitInputPort;
import org.istic.synthlab.core.services.IOMapping;

public class InputAdapter implements IInput {

    private UnitInputPort unitInputPort;

    public InputAdapter(UnitInputPort unitInputPort) {
        this.unitInputPort = unitInputPort;
    }

    public void connect(IOutput out) {
        IOMapping.connect(this, out);
    }

    @Override
    public void deconnect() {
        IOMapping.disconnect(this);
    }

}
