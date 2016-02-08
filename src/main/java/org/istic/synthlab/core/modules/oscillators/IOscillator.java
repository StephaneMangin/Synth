package org.istic.synthlab.core.modules.oscillators;

import org.istic.synthlab.core.Resource;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

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
     * Set the oscillator frequency in Hertz
     *
     * @param value double
     */
    void setFrequency(double value);

    /**
     * Returns the oscillator frequency in hertz
     *
     * @return double
     */
    double getFrequency();

    /**
     * test if the oscillator is enabled or not
     *
     * @return boolean
     */
    public boolean isActivated();

    /**
     * Set the oscillator amplitude in percentage
     *
     * @param value double
     */
    void setAmplitude(double value);

    /**
     * Returns the oscillator amplitude in percentage
     *
     * @return double
     */
    double getAmplitude();
}
