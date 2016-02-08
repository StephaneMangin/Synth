package org.istic.synthlab.components.vcoa;

import org.istic.synthlab.core.AbstractComponent;
import org.istic.synthlab.core.modules.oscillators.IOscillator;
import org.istic.synthlab.core.modules.oscillators.OscillatorType;
import org.istic.synthlab.core.services.Factory;

/**
 * @author Thibaud Hulin <thibaud[dot]hulin[dot]cl[at]gmail[dot]com>
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class Vcoa extends AbstractComponent {

    private IOscillator sineOscillator;
    private IOscillator pulseOscillator;
    private IOscillator squareOscillator;
    private IOscillator impulseOscillator;
    private IOscillator sawToothOscillator;
    private IOscillator triangleOscillator;
    private IOscillator redNoiseOscillator;
    //private IVcoaFrequencyModulator algorithm;

    public Vcoa(String name) {
        super(name);
        sineOscillator = Factory.createOscillator(this, OscillatorType.SINE);
        pulseOscillator = Factory.createOscillator(this, OscillatorType.PULSE);
        squareOscillator = Factory.createOscillator(this, OscillatorType.SQUARE);
        impulseOscillator = Factory.createOscillator(this, OscillatorType.IMPULSE);
        sawToothOscillator = Factory.createOscillator(this, OscillatorType.SAWTOOTH);
        triangleOscillator = Factory.createOscillator(this, OscillatorType.TRIANGLE);
        redNoiseOscillator = Factory.createOscillator(this, OscillatorType.REDNOISE);

        //algorithm = Factory.createVcoaAlgorithm(this);
        //algorithm.getOutput().connect(sineOscillator.getInput());

        // Connect internally
        //algorithm.getOutput().connect(squareOscillator.getInput());
        // TODO : does not work, how ?
        //getSourceFm().connect(squareOscillator.getFm());
        //getSourceAm().connect(squareOscillator.getAm());
        squareOscillator.getOutput().connect(getSink());
    }

    @Override
    public void activate() {
        sineOscillator.activate();
        pulseOscillator.activate();
        squareOscillator.activate();
        impulseOscillator.activate();
        sawToothOscillator.activate();
        triangleOscillator.activate();
        redNoiseOscillator.activate();
    }

    @Override
    public void desactivate() {
        sineOscillator.desactivate();
        pulseOscillator.desactivate();
        squareOscillator.desactivate();
        impulseOscillator.desactivate();
        sawToothOscillator.desactivate();
        triangleOscillator.desactivate();
        redNoiseOscillator.desactivate();
    }

    @Override
    public void init() {
    }

    @Override
    public void run() {

    }

    public void setFrequencyInput(double value) {
        //algorithm.setFrequency(algorithm.getFrequency().calculateStep(value));
    }

    public void setAmplitudeSine(double value) {
        sineOscillator.setFrequency(value);
    }

    public void setAmplitudePulse(double value) {
        pulseOscillator.setFrequency(value);
    }

    public void setAmplitudeSquare(double value) {
        squareOscillator.setFrequency(value);
    }

    public void setAmplitudeImpulse(double value) {
        impulseOscillator.setFrequency(value);
    }

    public void setAmplitudeSawTooth(double value) {
        sawToothOscillator.setFrequency(value);
    }

    public void setAmplitudeTriangle(double value) {
        triangleOscillator.setFrequency(value);
    }

    public void setAmplitudeRedNoise(double value) {
        redNoiseOscillator.setFrequency(value);
    }

    public IOscillator getPulseOscillator() {
        return this.pulseOscillator;
    }

    public IOscillator getSineOscillator() {
        return this.sineOscillator;
    }

    public IOscillator getSquareOscillator() {
        return this.squareOscillator;
    }

}
