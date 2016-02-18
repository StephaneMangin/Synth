package org.istic.synthlab.core.modules.oscillators;

import org.istic.synthlab.core.Resource;
import org.istic.synthlab.core.modules.IModule;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;

public interface IOscillator extends IModule, Resource {

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
