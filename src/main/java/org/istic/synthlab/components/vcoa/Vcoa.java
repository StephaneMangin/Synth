package org.istic.synthlab.components.vcoa;

import org.istic.synthlab.core.AbstractComponent;
import org.istic.synthlab.core.modules.oscillators.IOscillator;
import org.istic.synthlab.core.modules.oscillators.OscillatorType;
import org.istic.synthlab.core.services.Factory;

/**
 * @author Thibaud Hulin thibaud.hulin.cl[ât]gmail.com
 */
public class Vcoa extends AbstractComponent {

    private IOscillator sineOscillator;
    private IOscillator pulseOscillator;
    private IOscillator squareOscillator;
    //private IVcoaFrequencyModulator algorithm;

    public Vcoa(String name) {
        super(name);
        sineOscillator = Factory.createOscillator(this, OscillatorType.SINE);
        pulseOscillator = Factory.createOscillator(this, OscillatorType.PULSE);
        squareOscillator = Factory.createOscillator(this, OscillatorType.SQUARE);
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
    }

    @Override
    public void desactivate() {
        sineOscillator.desactivate();
        pulseOscillator.desactivate();
        squareOscillator.desactivate();
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
        //sineOscillator.setFrequency(sineOscillator.getFrequencyPotentiometer().calculateStep(value));
    }

    public void setAmplitudePulse(double value) {
        //pulseOscillator.setFrequency(pulseOscillator.getFrequencyPotentiometer().calculateStep(value));
    }

    public void setAmplitudeSquare(double value) {
        squareOscillator.setFrequency(squareOscillator.getFrequencyPotentiometer().calculateStep(value));
    }
}
