package org.istic.synthlab.core;

import com.jsyn.ports.UnitOutputPort;

/**
 * Created by stephane on 02/02/16.
 */
public class OutputAdapter implements IOutput {

    private UnitOutputPort unitOutputPort;

    public OutputAdapter(UnitOutputPort unitOutputPort) {
        this.unitOutputPort = unitOutputPort;
    }

    public void connect(IInput in) {

    }
}