package org.istic.synthlab.components.vcoa;


import org.istic.synthlab.core.IInput;
import org.istic.synthlab.core.IOutput;

public interface Oscillator {
    IInput getInput();

    IOutput getOutput();

    void setFrequency(double value);

    void setAmplitude(double amplitude);

    void setPhase(double phase);

}
