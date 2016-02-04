package org.istic.synthlab.components.vcoa;

import org.istic.synthlab.core.AComponent;
import org.istic.synthlab.core.modules.algorithms.IVcoaAlgorithm;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.oscillators.IOscillator;
import org.istic.synthlab.core.modules.oscillators.OscillatorType;
import org.istic.synthlab.core.services.ModulesFactory;

public class Vcoa extends AComponent {

    private IOscillator sineOscillator;
    private IOscillator pulseOscillator;
    private IOscillator squareOscillator;
    private IOutput output;
    private IVcoaAlgorithm algorithm;
    private IInput input;

    public Vcoa(String name) {
        super(name);
        this.sineOscillator = ModulesFactory.createOscillator(this, OscillatorType.SINE);
        this.pulseOscillator = ModulesFactory.createOscillator(this, OscillatorType.PULSE);
        this.squareOscillator = ModulesFactory.createOscillator(this, OscillatorType.SQUARE);
        this.algorithm = ModulesFactory.createVcoaAlgorithm(this);
        algorithm.getOutput().connect(sineOscillator.getInput());
        this.input = this.algorithm.getInput();
        this.output = this.sineOscillator.getOutput();
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

    }

    public void setFrequencyInput(double value) {
        algorithm.setPotentiometerFrequency(algorithm.getPotentiometer().calculateStep(value));
    }

    public void setAmplitudeSine(double value) {
        sineOscillator.setAmplitude(sineOscillator.getPotentiometer().calculateStep(value));
    }

    public void setAmplitudePulse(double value) {
        pulseOscillator.setAmplitude(pulseOscillator.getPotentiometer().calculateStep(value));
    }

    public void setAmplitudeSquare(double value) {
        squareOscillator.setAmplitude(squareOscillator.getPotentiometer().calculateStep(value));
    }
    /*
        public void setAmplitudeTriangle(double value) {
            triangleOscillator.setAmplitude(triangleOscillator.getPotentiometer().calculateStep(value));
        }
    */
    public IInput getInput() {
        return this.input;
    }
    public IOutput getOutput() {
        return this.output;
    }
}
