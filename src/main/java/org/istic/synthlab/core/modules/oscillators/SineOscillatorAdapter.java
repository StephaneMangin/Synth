package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.SineOscillator;
import org.istic.synthlab.core.AdapterFactory;
import org.istic.synthlab.core.Potentiometer;
import org.istic.synthlab.core.PotentiometerType;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;


public class SineOscillatorAdapter implements IOscillator {

    private SineOscillator oscillator;
    private IOutput output;
    private Potentiometer potentiometer;

    public SineOscillatorAdapter() {
        this.oscillator = new SineOscillator();
        this.output = AdapterFactory.createOutput(oscillator.output);
        this.potentiometer = new Potentiometer("Frequency", PotentiometerType.EXPONENTIAL, 20000.0, 20.0, 320.0);
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
    public Potentiometer gePotentiometer() {
        return potentiometer;
    }

    @Override
    public void activate() {
        this.oscillator.setEnabled(true);
    }

    @Override
    public void desactivate() {
        this.oscillator.setEnabled(false);
    }
}
