package org.istic.synthlab.core.modules.algorithms;

import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;

/**
 *
 * @author Thibaud Hulin <thibaud[dot]hulin[dot]cl[at]gmail[dot]com>
 */
public interface IVcoaFrequencyModulator {

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
