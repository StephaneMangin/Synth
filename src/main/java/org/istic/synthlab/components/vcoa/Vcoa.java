package org.istic.synthlab.components.vcoa;

import org.istic.synthlab.core.AbstractComponent;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.modulators.IModulator;
import org.istic.synthlab.core.modules.modulators.ModulatorType;
import org.istic.synthlab.core.modules.oscillators.IOscillator;
import org.istic.synthlab.core.modules.oscillators.OscillatorType;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * @author Thibaud Hulin <thibaud[dot]hulin[dot]cl[at]gmail[dot]com>
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class Vcoa extends AbstractComponent {

    private IOscillator sineOscillator = Factory.createOscillator(this, OscillatorType.SINE);
    private IOscillator pulseOscillator = Factory.createOscillator(this, OscillatorType.PULSE);
    private IOscillator squareOscillator = Factory.createOscillator(this, OscillatorType.SQUARE);
    private IOscillator impulseOscillator = Factory.createOscillator(this, OscillatorType.IMPULSE);
    private IOscillator sawToothOscillator = Factory.createOscillator(this, OscillatorType.SAWTOOTH);
    private IOscillator triangleOscillator = Factory.createOscillator(this, OscillatorType.TRIANGLE);
    private IOscillator redNoiseOscillator = Factory.createOscillator(this, OscillatorType.REDNOISE);
    private IOscillator defaultOscillator;
    private IModulator exponentialModulator = Factory.createModulator("Expl Freq", this, ModulatorType.VCOA, PotentiometerType.EXPONENTIAL);
    private IModulator linearModulator = Factory.createModulator("Lin Freq", this, ModulatorType.FREQUENCY, PotentiometerType.LINEAR);
    private double amplitudeValue;

    public Vcoa(String name) {
        super(name);

        exponentialModulator = Factory.createModulator("Exp. Freq.", this, ModulatorType.VCOA, PotentiometerType.EXPONENTIAL);
        linearModulator = Factory.createModulator("Linear Freq.", this, ModulatorType.FREQUENCY, PotentiometerType.LINEAR);

        getSourceFm().connect(exponentialModulator.getInput());
        exponentialModulator.getOutput().connect(linearModulator.getInput());
        setDefaultOscillator(squareOscillator);
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
    public void deactivate() {
        sineOscillator.deactivate();
        pulseOscillator.deactivate();
        squareOscillator.deactivate();
        impulseOscillator.deactivate();
        sawToothOscillator.deactivate();
        triangleOscillator.deactivate();
        redNoiseOscillator.deactivate();
    }

    @Override
    public boolean isActivated() {
        return sineOscillator.isActivated() ||
        pulseOscillator.isActivated() ||
        squareOscillator.isActivated() ||
        impulseOscillator.isActivated() ||
        sawToothOscillator.isActivated() ||
        triangleOscillator.isActivated() ||
        redNoiseOscillator.isActivated();
    }

    @Override
    public void init() {
    }

    @Override
    public void run() {
    }

    /**
     * Set the value of the exponential frequency parameter and update linear modulator
     *
     * @param value
     */
    public void setExponentialFrequency(double value) {
        exponentialModulator.setValue(value);
        double octave = (exponentialModulator.getMax()-exponentialModulator.getMin())*exponentialModulator.getOriginalValue();
        linearModulator.setMax(Math.pow(2,octave+1));
        linearModulator.setMin(Math.pow(2,octave-1));
        linearModulator.setValue(linearModulator.getOriginalValue());
    }

    /**
     * Get the value of the exponential frequency paremeter
     *
     *
     * @return double
     */
    public double getExponentialFrequency() {
        return exponentialModulator.getValue();
    }

    /**
     * Get the maximum value of the exponential frequency paremeter
     *
     * @return double
     */
    public double getExponentialFrequencyMax() {
        return exponentialModulator.getMax();
    }

    /**
     * Get the minimal value of the exponential frequency paremeter
     *
     * @return double
     */
    public double getExponentialFrequencyMin() {
        return exponentialModulator.getMin();
    }

    /**
     * Set the value of the linear frequency paremeter
     *
     * @param value
     */
    public void setLinearFrequency(double value) {
        linearModulator.setValue(value);
    }

    /**
     * Get the linear frequency value of the linear frequency paremeter
     *
     * @return double
     */
    public double getLinearFrequency() {
        return linearModulator.getValue();
    }

    /**
     * Get the maximum linear frequency value of the linear frequency paremeter
     *
     * @return double
     */
    public double getLinearFrequencyMax() {
        return linearModulator.getMax();
    }

    /**
     * Get the minimmum linear frequency value of the linear frequency paremeter
     *
     * @return double
     */
    public double getLinearFrequencyMin() {
        return linearModulator.getMin();
    }



    /**
     * Set the amplitude of the sinusoidal oscillator
     *
     */
    public void setAmplitudeSine(double value) {
        sineOscillator.getAmplitudePotentiometer().setValue(value);
    }

    /**
     * Get the amplitude value of the sinusoidal oscillator
     *
     * @return double
     */
    public double getAmplitudeSine() {
        return sineOscillator.getAmplitudePotentiometer().getValue();
    }

    /**
     * Get the maximum amplitude value of the sinusoidal oscillator
     *
     * @return double
     */
    public double getAmplitudeSineMax() {
        return sineOscillator.getAmplitudePotentiometer().getMax();
    }

    /**
     * Get the minimum amplitude value of the sinusoidal oscillator
     *
     * @return double
     */
    public double getAmplitudeSineMin() {
        return sineOscillator.getAmplitudePotentiometer().getMin();
    }



    /**
     * Get the maximum amplitude of the sinusoidal oscillator
     *
     */
    public void setAmplitudePulse(double value) {
        pulseOscillator.getAmplitudePotentiometer().setValue(value);
    }

    /**
     * Get the amplitude value of the sinusoidal oscillator
     *
     * @return double
     */
    public double getAmplitudePulse() {
        return pulseOscillator.getAmplitudePotentiometer().getValue();
    }

    /**
     * Get the maximum amplitude value of the sinusoidal oscillator
     *
     * @return double
     */
    public double getAmplitudePulseMax() {
        return pulseOscillator.getAmplitudePotentiometer().getMax();
    }

    /**
     * Get the minimum amplitude value of the sinusoidal oscillator
     *
     * @return double
     */
    public double getAmplitudePulseMin() {
        return pulseOscillator.getAmplitudePotentiometer().getMin();
    }



    /**
     * Set the amplitude of the square oscillator
     *
     */
    public void setAmplitudeSquare(double value) {
        squareOscillator.getAmplitudePotentiometer().setValue(value);
    }

    /**
     * Get the amplitude value of the square oscillator
     *
     * @return double
     */
    public double getAmplitudeSquare() {
        return squareOscillator.getAmplitudePotentiometer().getValue();
    }

    /**
     * Get the maximum amplitude value of the square oscillator
     *
     * @return double
     */
    public double getAmplitudeSquareMax() {
        return squareOscillator.getAmplitudePotentiometer().getMax();
    }

    /**
     * Get the minimum amplitude value of the square oscillator
     *
     * @return double
     */
    public double getAmplitudeSquareMin() {
        return squareOscillator.getAmplitudePotentiometer().getMin();
    }

    /**
     * Set the amplitude of the impulse oscillator
     *
     * @param value
     */
    public void setAmplitudeImpulse(double value) {
        impulseOscillator.getAmplitudePotentiometer().setValue(value);
    }

    /**
     * Set the amplitude of the saw tooth oscillator
     *
     * @param value
     */
    public void setAmplitudeSawTooth(double value) {
        sawToothOscillator.getAmplitudePotentiometer().setValue(value);
    }

    /**
     * Set the amplitude of the triangle oscillator
     *
     * @param value
     */
    public void setAmplitudeTriangle(double value) {
        triangleOscillator.getAmplitudePotentiometer().setValue(value);
    }

    /**
     * Set the amplitude of the red noise oscillator
     *
     * @param value
     */
    public void setAmplitudeRedNoise(double value) {
        redNoiseOscillator.getAmplitudePotentiometer().setValue(value);
    }

    public void setAmplitudeOscillator(double value) {
        this.amplitudeValue = value;
        defaultOscillator.getAmplitudePotentiometer().setValue(value);
    }

    /**
     * Set the oscillator output type
     *
     * FIXME : Giving a core's enum object breaks encapsulation
     * FIXME : the view should have it's own enum
     *
     * @param type
     */

    public void setOscillatorType(OscillatorType type) {
        switch (type) {
            case SINE:
                setDefaultOscillator(sineOscillator);
                break;
            case TRIANGLE:
                setDefaultOscillator(triangleOscillator);
                break;
            case SAWTOOTH:
                setDefaultOscillator(sawToothOscillator);
                break;
            case PULSE:
                setDefaultOscillator(pulseOscillator);
                break;
            case IMPULSE:
                setDefaultOscillator(impulseOscillator);
                break;
            case REDNOISE:
                setDefaultOscillator(redNoiseOscillator);
                break;
            case SQUARE:
            default:
                setDefaultOscillator(squareOscillator);
        }
    }

    /**
     * Set the default oscillator
     *
     * @param defaultOscillator
     */
    private void setDefaultOscillator(IOscillator defaultOscillator) {
        if (this.defaultOscillator != null) {
            this.defaultOscillator.getOutput().deconnect();
            linearModulator.getOutput().deconnect();
        }
        this.defaultOscillator = defaultOscillator;
        this.defaultOscillator.getOutput().connect(getSink());
        linearModulator.getOutput().connect(this.defaultOscillator.getFm());
        this.defaultOscillator.getAmplitudePotentiometer().setValue(amplitudeValue);
    }

    /**
     * Returns the pulse oscillator
     *
     * @return IOscillator
     */
    public IOscillator getPulseOscillator() {
        return this.pulseOscillator;
    }

    /**
     * Returns the pulse oscillator
     *
     * @return IOscillator
     */
    public IOscillator getSineOscillator() {
        return this.sineOscillator;
    }

    /**
     * Returns the square oscillator
     *
     * @return IOscillator
     */
    public IOscillator getSquareOscillator() {
        return this.squareOscillator;
    }

    /**
     * Returns the sinusoidal oscillator output
     *
     * @return IOutput
     */
    public IOutput getSineOutput() {
        return sineOscillator.getOutput();
    }

    /**
     * Returns the pulse oscillator output
     *
     * @return IOutput
     */
    public IOutput getPulseOutput() {
        return pulseOscillator.getOutput();
    }

    /**
     * Returns the square oscillator output
     *
     * @return IOutput
     */
    public IOutput getSquareOutput() {
        return squareOscillator.getOutput();
    }

    /**
     * Returns the impulse oscillator output
     *
     * @return IOutput
     */
    public IOutput getImpulseOutput() {
        return impulseOscillator.getOutput();
    }

    /**
     * Returns the sawtooth oscillator output
     *
     * @return IOutput
     */
    public IOutput getSawToothOutput() {
        return sawToothOscillator.getOutput();
    }

    /**
     * Returns the triangle oscillator output
     *
     * @return IOutput
     */
    public IOutput getTriangleOutput() {
        return triangleOscillator.getOutput();
    }

    /**
     * Returns the red noise oscillator output
     *
     * @return IOutput
     */
    public IOutput getRedNoiseOutput() {
        return redNoiseOscillator.getOutput();
    }
}
