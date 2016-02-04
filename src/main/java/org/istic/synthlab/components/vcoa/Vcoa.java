package org.istic.synthlab.components.vcoa;

import org.istic.synthlab.core.AComponent;
import org.istic.synthlab.core.modules.algorithms.IVcoaAlgorithm;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.oscillators.IOscillator;
import org.istic.synthlab.core.modules.oscillators.OscillatorType;
import org.istic.synthlab.core.services.ModulesFactory;

/**
 * @author Thibaud Hulin thibaud.hulin.cl[ât]gmail.com
 */
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
        this.input = this.algorithm.getInput();
        this.output = this.sineOscillator.getOutput();
        algorithm.getOutput().connect(sineOscillator.getInput());
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
        algorithm.setPotentiometerFrequency(algorithm.getFrequencyPotentiometer().calculateStep(value));
    }

    public void setAmplitudeSine(double value) {
        sineOscillator.setAmplitude(sineOscillator.getAmplitudePotentiometer().calculateStep(value));
    }

    public void setAmplitudePulse(double value) {
        pulseOscillator.setAmplitude(pulseOscillator.getAmplitudePotentiometer().calculateStep(value));
    }

    public void setAmplitudeSquare(double value) {
        squareOscillator.setAmplitude(squareOscillator.getAmplitudePotentiometer().calculateStep(value));
    }
    /*
        public void setAmplitudeTriangle(double value) {
            triangleOscillator.setAmplitude(triangleOscillator.getFrequencyPotentiometer().calculateStep(value));
        }
    */
    public IInput getInput() {
        return this.input;
    }
    public IOutput getOutput() {
        return this.output;
    }
}
