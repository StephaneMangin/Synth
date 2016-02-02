package org.istic.synthlab.core;

import com.jsyn.ports.UnitOutputPort;


public class OutputAdapter implements IOutput {

    private UnitOutputPort unitOutputPort;

    public OutputAdapter(UnitOutputPort unitOutputPort) {
        this.unitOutputPort = unitOutputPort;
    }

    public void connect(IInput in) {
        Channel.connect(in, this);
    }
}
