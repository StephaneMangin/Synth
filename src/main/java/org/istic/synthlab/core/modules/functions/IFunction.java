package org.istic.synthlab.core.modules.functions;

import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

public interface IFunction {

    IInput getInput();

    IInput getVariableInput();

    IOutput getOutput();

    void setVariableInput(double value);
}
