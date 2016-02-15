package org.istic.synthlab.core.modules.functions;

import org.istic.synthlab.core.modules.IModule;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

public interface IFunction extends IModule {

    IInput getInput();

    IInput getVariableInput();

    IOutput getOutput();

    void setVariableInput(double value);
}
