package org.istic.synthlab.components.vcoa;


import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.Resource;

public interface Oscillator extends Resource {
    IInput getInput();

    IOutput getOutput();

    void setFrequency(double value);

    void setAmplitude(double amplitude);

    void setPhase(double phase);

}
