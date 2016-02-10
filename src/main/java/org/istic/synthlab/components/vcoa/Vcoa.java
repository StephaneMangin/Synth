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

    private IOscillator sineOscillator;
    private IOscillator pulseOscillator;
    private IOscillator squareOscillator;
    private IOscillator impulseOscillator;
    private IOscillator sawToothOscillator;
    private IOscillator triangleOscillator;
    private IOscillator redNoiseOscillator;
    private IModulator exponentialModulator;
    private IModulator linearModulator;

    public Vcoa(String name) {
        super(name);
        sineOscillator = Factory.createOscillator(this, OscillatorType.SINE);
        pulseOscillator = Factory.createOscillator(this, OscillatorType.PULSE);
        squareOscillator = Factory.createOscillator(this, OscillatorType.SQUARE);
        impulseOscillator = Factory.createOscillator(this, OscillatorType.IMPULSE);
        sawToothOscillator = Factory.createOscillator(this, OscillatorType.SAWTOOTH);
        triangleOscillator = Factory.createOscillator(this, OscillatorType.TRIANGLE);
        redNoiseOscillator = Factory.createOscillator(this, OscillatorType.REDNOISE);

        // Mix all oscillator's output to the sink port
        // squareOscillator.getOutput().connect(getSink());
        sineOscillator.getOutput().connect(getSink());
        // pulseOscillator.getOutput().connect(getSink());
        // impulseOscillator.getOutput().connect(getSink());
        // sawToothOscillator.getOutput().connect(getSink());
        // triangleOscillator.getOutput().connect(getSink());
        // redNoiseOscillator.getOutput().connect(getSink());

        exponentialModulator = Factory.createModulator("Expl Freq", this, ModulatorType.VCOA, PotentiometerType.EXPONENTIAL);
        linearModulator = Factory.createModulator("Lin Freq", this, ModulatorType.FREQUENCY, PotentiometerType.LINEAR);


        getSourceFm().connect(exponentialModulator.getInput());
        exponentialModulator.getOutput().connect(linearModulator.getInput());
        //linearModulator.getOutput().connect(squareOscillator.getFm());
        linearModulator.getOutput().connect(sineOscillator.getFm());
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

    public void setLinearFrequency(double value) {
        linearModulator.setValue(value);
    }

    public void setAmplitudeSine(double value) {
        sineOscillator.setAmplitude(value);
    }

    public void setAmplitudePulse(double value) {
        pulseOscillator.setAmplitude(value);
    }

    public void setAmplitudeSquare(double value) {
        squareOscillator.setAmplitude(value);
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

    public IOutput getImpulsesineOutput() {
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
