package org.istic.synthlab.core.modules.algorithms;

import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.parametrization.Potentiometer;

/**
 *
 */
public interface IVcoaAlgorithm {

    IInput getInput();

    IOutput getOutput();

    /**
     * Returns a frequency potentiometer.
     *
     * @return Potentiometer
     */
    Potentiometer getFrequencyPotentiometer();

    void setPotentiometerFrequency(double value);
}
