package org.istic.synthlab.core;

/**
 * Created by stephane on 02/02/16.
 */
public class InputAdapter implements IOutput {

    private UnitInputPort unitInputPort;

    public InputAdapter(UnitInputPort unitInputPort) {
        this.unitInputPort = unitInputPort;
    }

    public void connect(IOutput in) {

    }
}
