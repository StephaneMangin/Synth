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

    public Vcoa(String name) {
        super(name);

        exponentialModulator = Factory.createModulator("Expl Freq", this, ModulatorType.VCOA, PotentiometerType.EXPONENTIAL);
        linearModulator = Factory.createModulator("Lin Freq", this, ModulatorType.FREQUENCY, PotentiometerType.LINEAR);


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

    public void setExponentialFrequency(double value) {
        exponentialModulator.setValue(value);
    }

    public double getExponentialFrequency() {
        return exponentialModulator.getValue();
    }

    public double getExponentialFrequencyMax() {
        return exponentialModulator.getMax();
    }

    public double getExponentialFrequencyMin() {
        return exponentialModulator.getMin();
    }



    public void setLinearFrequency(double value) {
        linearModulator.setValue(value);
    }

    public double getLinearFrequency() {
        return linearModulator.getValue();
    }

    public double getLinearFrequencyMax() {
        return linearModulator.getMax();
    }

    public double getLinearFrequencyMin() {
        return linearModulator.getMin();
    }



    public void setAmplitudeSine(double value) {
        sineOscillator.setAmplitude(value);
    }

    public double getAmplitudeSine() {
        return sineOscillator.getAmplitude();
    }

    public double getAmplitudeSineMax() {
        return sineOscillator.getAmplitudeMax();
    }

    public double getAmplitudeSineMin() {
        return sineOscillator.getAmplitudeMin();
    }



    public void setAmplitudePulse(double value) {
        pulseOscillator.setAmplitude(value);
    }

    public double getAmplitudePulse() {
        return pulseOscillator.getAmplitude();
    }

    public double getAmplitudePulseMax() {
        return pulseOscillator.getAmplitudeMax();
    }

    public double getAmplitudePulseMin() {
        return pulseOscillator.getAmplitudeMin();
    }



    public void setAmplitudeSquare(double value) {
        squareOscillator.setAmplitude(value);
    }

    public double getAmplitudeSquaree() {
        return squareOscillator.getAmplitude();
    }

    public double getAmplitudeSquareMax() {
        return squareOscillator.getAmplitudeMax();
    }

    public double getAmplitudeSquareMin() {
        return squareOscillator.getAmplitudeMin();
    }



    public void setAmplitudeImpulse(double value) {
        impulseOscillator.setAmplitude(value);
    }

    public void setAmplitudeSawTooth(double value) {
        sawToothOscillator.setAmplitude(value);
    }

    public void setAmplitudeTriangle(double value) {
        triangleOscillator.setAmplitude(value);
    }

    public void setAmplitudeRedNoise(double value) {
        redNoiseOscillator.setAmplitude(value);
    }

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

    private void setDefaultOscillator(IOscillator defaultOscillator) {
        if (this.defaultOscillator != null) {
            this.defaultOscillator.getOutput().deconnect();
            linearModulator.getOutput().deconnect();
        }
        this.defaultOscillator = defaultOscillator;
        this.defaultOscillator.getOutput().connect(getSink());
        linearModulator.getOutput().connect(this.defaultOscillator.getFm());
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

    public IOutput getSineOutput() {
        return sineOscillator.getOutput();
    }

    public IOutput getPulseOutput() {
        return pulseOscillator.getOutput();
    }

    public IOutput getSquareOutput() {
        return squareOscillator.getOutput();
    }

    public IOutput getImpulseOutput() {
        return impulseOscillator.getOutput();
    }

    public IOutput getSawToothOutput() {
        return sawToothOscillator.getOutput();
    }

    public IOutput getTriangleOutput() {
        return triangleOscillator.getOutput();
    }

    public IOutput getRedNoiseOutput() {
        return redNoiseOscillator.getOutput();
    }
}
