package org.istic.synthlab.components.vcoa;

import com.jsyn.unitgen.*;
import org.istic.synthlab.core.IInput;
import org.istic.synthlab.core.IOutput;
import org.istic.synthlab.core.OutputAdapter;


public class SineOscillatorAdapter implements Oscillator {
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
        this.output = new OutputAdapter(oscillator.output);
    }

    @Override
    public IInput getInput() {
        return null;
    }

    @Override
    public IOutput getOutput() {
        return output;
    }

    @Override
    public void setFrequency(double frequency) {
        this.frequency = frequency;
        oscillator.frequency.set(frequency);
    }

    @Override
    public void setAmplitude(double amplitude) {
        this.amplitude = amplitude;
        oscillator.amplitude.set(amplitude);
    }

    @Override
    public void setPhase(double phase) {
        this.phase = phase;
        oscillator.phase.set(phase);
    }
}
