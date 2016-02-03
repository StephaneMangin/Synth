package org.istic.synthlab.core.modules.oscillators;


import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

public interface IOscillator {
    IInput getInput();

    IOutput getOutput();

    void setFrequency(double value);

    void setAmplitude(double amplitude);

    void setPhase(double phase);

}
