package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.SineOscillator;
import org.istic.synthlab.core.AdapterFactory;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;


public class SineOscillatorAdapter implements IOscillator {
    private SineOscillator oscillator;
    private double frequency;
    private double amplitude;
    private double phase;
    private IOutput output;

    public SineOscillatorAdapter() {
        this.frequency = 0.0;
        this.amplitude = 0.0;
        this.phase = 0.0;
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
        this.frequency = frequency;
        this.oscillator.frequency.set(frequency);
    }

    @Override
    public void setAmplitude(double amplitude) {
        this.amplitude = amplitude;
        this.oscillator.amplitude.set(amplitude);
    }

    @Override
    public void setPhase(double phase) {
        this.phase = phase;
        this.oscillator.phase.set(phase);
    }

}
