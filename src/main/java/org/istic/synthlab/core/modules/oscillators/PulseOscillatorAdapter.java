package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.PulseOscillator;
import org.istic.synthlab.core.AdapterFactory;
import org.istic.synthlab.core.Potentiometer;
import org.istic.synthlab.core.PotentiometerType;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

/**
 * @author Group1
 *
 * The adapter  PulseOscillator
 *
 */
public class PulseOscillatorAdapter implements IOscillator{

    private PulseOscillator pulseOscillator;
    private IOutput output;
    private Potentiometer potentiometer;

    /*
    * the constructor
    */
    public PulseOscillatorAdapter() {
        this.pulseOscillator = new PulseOscillator();
        this.output = AdapterFactory.createOutput(pulseOscillator.output);
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
    public Potentiometer getPotentiometer() {
        return this.potentiometer;
    }

    @Override
    public void activate() {
        this.pulseOscillator.setEnabled(true);
    }

    @Override
    public void desactivate() {
        this.pulseOscillator.setEnabled(false);
    }
}
