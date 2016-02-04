package org.istic.synthlab.core.modules.algorithms;

import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.parametrization.Potentiometer;

/**
 *
 */
public interface IFrequencyModulator {

    IInput getInput();

    IOutput getOutput();

    /**
     * Returns a frequency.
     *
     * @return double
     */
    Potentiometer getFrequency();

    void setFrequency(double value);
}
