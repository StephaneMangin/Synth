package org.istic.synthlab.core.modules.oscillators;

import org.istic.synthlab.core.Resource;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;

public interface IOscillator extends Resource {

    /**
     * Returns an frequency modulation input instance.
     *
     * @return IInput
     */
    IInput getFm();

    /**
     * Returns an amplitude modulation input instance.
     *
     * @return IInput
     */
    IInput getAm();

    /**
     * Returns an output instance.
     *
     * @return IOutput
     */
    IOutput getOutput();

    /**
     * test if the oscillator is enabled or not
     *
     * @return boolean
     */
    boolean isActivated();

    /**
     * Returns the amplitude potentiometer
     *
     * @return Potentiometer
     */
    Potentiometer getAmplitudePotentiometer();

    /**
     * Returns the frequency potentiometer
     *
     * @return Potentiometer
     */
    Potentiometer getFrequencyPotentiometer();

}
