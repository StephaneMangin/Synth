package org.istic.synthlab.components.rednoise;

import org.istic.synthlab.components.AbstractComponent;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.oscillators.IOscillator;
import org.istic.synthlab.core.services.Factory;

/**
 * @author  Ngassam Noumi Paola npaolita[at]yahoo[dot]fr
 */

/**
 * The model of White noise component
 * It creates a  white noise module to produce a signal conforming to the definition of white noise.
 * */
public class RedNoise extends AbstractComponent  {

    private IOscillator noise;

    /**
     * Instantiates a new component.
     *
     * @param name the name
     */
    public RedNoise(String name) {
        super(name);
        noise = Factory.createRedNoise(this);
        noise.getAmplitudePotentiometer().setValue(1);
        noise.getOutput().connect(getSink());
    }

    /**
     * Set the value of the exponential frequency parameter and update linear modulator
     *
     * @param value
     */
    public void setFrequency(double value) {
        double range = Math.log(noise.getFrequencyPotentiometer().getMax()- noise.getFrequencyPotentiometer().getMin())/Math.log(2);
        double octave = Math.round(range*value);
        double roundedValue = octave/Math.round(range);
        noise.getFrequencyPotentiometer().setValue(roundedValue);
    }

    /**
     * Get the value of the exponential frequency paremeter
     *
     *
     * @return double
     */
    public double getFrequency() {
        return noise.getFrequencyPotentiometer().getValue();
    }

    /**
     * Get the maximum value of the exponential frequency paremeter
     *
     * @return double
     */
    public double getFrequencyMax() {
        return noise.getFrequencyPotentiometer().getMax();
    }

    /**
     * Get the minimal value of the exponential frequency paremeter
     *
     * @return double
     */
    public double getFrequencyMin() {
        return noise.getFrequencyPotentiometer().getMin();
    }

    @Override
    public boolean isActivated(){
        return noise.isActivated();
    }

    @Override
    public void activate() {
        noise.activate();
    }

    @Override
    public void deactivate() {
        noise.deactivate();
    }

    @Override
    public IOutput getOutput(){
        return noise.getOutput();
    }
}
