package org.istic.synthlab.components.vcoa;

import org.istic.synthlab.core.AComponent;
import org.istic.synthlab.core.AdapterFactory;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.oscillators.IOscillator;
import org.istic.synthlab.core.modules.oscillators.OscillatorType;

public class Vcoa extends AComponent {

    private IOscillator sineOscillator;
    private IOscillator sawToothOscillator;

    public Vcoa(String name) {
        super(name);
        sineOscillator = AdapterFactory.createOscillator(this, OscillatorType.SINE);
        sawToothOscillator = AdapterFactory.createOscillator(this, OscillatorType.SAWTOOTH);
    }

    @Override
    public void activate() {
        this.sineOscillator.setActive(true);
    }

    @Override
    public void deactivate() {
        sineOscillator.setActive(false);
    }

    @Override
    public void init() {
    }

    @Override
    public void run() {
    }
}
