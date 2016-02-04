package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.SineOscillator;
import org.istic.synthlab.core.AdapterFactory;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;


/**
 * @author Group1
 * The type Sine oscillator adapter.
 */
public class SineOscillatorAdapter implements IOscillator {

    private SineOscillator oscillator;
    private IOutput output;

    /**
     * Instantiates a new Sine oscillator adapter.
     */
    public SineOscillatorAdapter() {
        this.oscillator = new SineOscillator();
        this.output = AdapterFactory.createOutput(oscillator.output);
    }

    @Override
    public IInput getInput() {
        return null;
    }

    @Override
    public IOutput getOutput() {
        return this.output;
    }

    @Override
    public void setFrequency(double frequency) {
        this.oscillator.frequency.set(frequency);
    }

    @Override
    public void setAmplitude(double amplitude) {
        this.oscillator.amplitude.set(amplitude);
    }

    @Override
    public void setPhase(double phase) {
        this.oscillator.phase.set(phase);
    }

    @Override
    public void setActive(boolean b) {
        this.oscillator.setEnabled(b);
    }

    /**
     * Gets oscillator.
     *
     * @return the oscillator
     */
    public SineOscillator getOscillator() {
        return oscillator;
    }


}
