package org.istic.synthlab.components.vcoa;

import org.istic.synthlab.core.*;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.oscillators.IOscillator;
import org.istic.synthlab.core.modules.oscillators.OscillatorType;

public class Vcoa extends AComponent {

    private IOscillator sineOscillator;
    private IOutput output;
    private Potentiometer potentiometer;

    public Vcoa(String name) {
        super(name);
        this.sineOscillator = AdapterFactory.createOscillator(this, OscillatorType.SINE);
        this.output = this.sineOscillator.getOutput();
        this.potentiometer = new Potentiometer("Frequency", PotentiometerType.EXPONENTIAL, 20000.0, 20.0, 320.0);
    }

    @Override
    public void activate() {
        this.sineOscillator.activate();
    }

    @Override
    public void desactivate() {
        this.sineOscillator.desactivate();
    }

    @Override
    public void init() {
    }

    @Override
    public void run() {
        sineOscillator.setFrequency(potentiometer.getValue()*Math.pow(2, sineOscillator.getFrequencyModulation()));
    }

    public IOutput getOutput() {
        return this.output;
    }

    public IOscillator getSineOscillator() {
        return this.sineOscillator;
    }

    public Potentiometer getPotentiometer() {
        return this.getPotentiometer();
    }
}
