package org.istic.synthlab.core;

import com.jsyn.ports.UnitInputPort;

public class InputAdapter implements IInput {

    private UnitInputPort unitInputPort;

    public InputAdapter(UnitInputPort unitInputPort) {
        this.unitInputPort = unitInputPort;
    }

    public void connect(IOutput out) {
        Channel.connect(this, out);
    }
}
