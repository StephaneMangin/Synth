package org.istic.synthlab.core.modules.oscillators;

import org.istic.synthlab.core.Resource;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.parametrization.Potentiometer;

public interface IOscillator extends Resource {

    /**
     * Returns an input instance.
     *
     * @return IInput
     */
    IInput getInput();

    /**
     * Returns an output instance.
     *
     * @return IOutput
     */
    IOutput getOutput();
    /**
     * Returns an amplitude potentiometer.
     *
     * @return Potentiometer
     */
    Potentiometer getPotentiometer();

    void setAmplitude(double value);
}
