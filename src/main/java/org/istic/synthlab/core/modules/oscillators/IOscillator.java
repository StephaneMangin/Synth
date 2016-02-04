package org.istic.synthlab.core.modules.oscillators;


import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

/**
 * The interface Oscillator.
 *
 * @author Group1
 */
public interface IOscillator {
    /**
     * Gets input.
     *
     * @return the input
     */
    IInput getInput();

    /**
     * Gets output.
     *
     * @return the output
     */
    IOutput getOutput();

    /**
     * Sets frequency.
     *
     * @param value the value
     */
    void setFrequency(double value);

    /**
     * Sets amplitude.
     *
     * @param amplitude the amplitude
     *
     */
    void setAmplitude(double amplitude);

    /**
     * Sets phase.
     *
     * @param phase:double ,  the phase
     */
    void setPhase(double phase);

    /**
     * Sets active.
     *
     * @param b:boolean
     */
    void setActive(boolean b);
}
