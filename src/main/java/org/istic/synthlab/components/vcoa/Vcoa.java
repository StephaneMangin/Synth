package org.istic.synthlab.components.vcoa;

import org.istic.synthlab.core.AComponent;
import org.istic.synthlab.core.modules.oscillators.IOscillator;

public class Vcoa extends AComponent {

    private IOscillator oscilllator;
    private double frequency;

    public Vcoa(String name, IOscillator oscillator) {
        super(name);
        this.oscilllator = oscillator;
    }

    public void start() {

    }

    public void stop() {

    }

    public void init() {

    }

    public void run() {

    }
}
