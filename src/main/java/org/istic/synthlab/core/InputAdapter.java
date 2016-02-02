package org.istic.synthlab.core;

import com.jsyn.ports.UnitInputPort;

/**
 * Created by stephane on 02/02/16.
 */
public class InputAdapter implements IInput {

    private UnitInputPort unitInputPort;

    public InputAdapter(UnitInputPort unitInputPort) {
        this.unitInputPort = unitInputPort;
    }

    public void connect(IOutput out) {

    }
}
