package org.istic.synthlab.core.services;

import com.jsyn.Synthesizer;
import com.jsyn.engine.SynthesisEngine;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import org.istic.synthlab.components.out.Out;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.envelope.EnvelopeDAHDSR;
import org.istic.synthlab.core.modules.envelope.IEnvelopeDAHDSR;
import org.istic.synthlab.core.modules.filters.*;
import org.istic.synthlab.core.modules.functions.*;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.io.Input;
import org.istic.synthlab.core.modules.io.Output;
import org.istic.synthlab.core.modules.lineOuts.ILineOut;
import org.istic.synthlab.core.modules.lineOuts.LineOut;
import org.istic.synthlab.core.modules.lineOuts.LineType;
import org.istic.synthlab.core.modules.modulators.*;
import org.istic.synthlab.core.modules.oscillators.*;
import org.istic.synthlab.core.modules.passThrough.IPassThrough;
import org.istic.synthlab.core.modules.passThrough.PassThrough;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;


/**
 * Helper class to manage adapters creation.
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 *
 */
public class Factory {

    // A singleton for the synthetizer
    private static Synthesizer synthesizer = null;

    /**
     * Return a IInput instance.
     *
     * @param component IComponent
     * @param unitInputPort UnitInputPort
     * @return IInput
     */
    public static IInput createInput(String name, IComponent component, UnitInputPort unitInputPort) {
        return new Input(name, component, unitInputPort);
    }

    /**
     * Return an IOutput instance.
     *
     * @param component IComponent
     * @param unitOutputPort UnitOutputPort
     * @return IOutput
     */
    public static IOutput createOutput(String name, IComponent component, UnitOutputPort unitOutputPort) {
        return new Output(name, component, unitOutputPort);
    }

    /**
     * Return an IOscillator instance.
     *
     * @param component IComponent
     * @param type OscillatorType
     * @return IOscillator
     */
    public static IOscillator createOscillator(IComponent component, OscillatorType type) {
        switch (type) {
            case SINE:
                return new SineOscillator(component);
            case TRIANGLE:
                return new TriangleOscillator(component);
            case SQUARE:
                return new SquareOscillator(component);
            case SAWTOOTH:
                return new SawtoothOscillator(component);
            case PULSE:
                return new PulseOscillator(component);
            case IMPULSE:
                return new ImpulseOscillator(component);
            case REDNOISE:
                return new RedNoiseOscillator(component);
            default:
                return new SineOscillator(component);
        }
    }

    /**
     * Returns an IFilter instance.
     *
     * @param component IComponent
     * @param type FilterType
     * @return IFilter
     */
    public static IFilter createFilter(IComponent component, FilterType type) {
        switch (type) {
            case ALLPASS:
                return new BandPassFilter(component);
            case LOWPASS:
                return new LowPassFilter(component);
            case HIGHPASS:
                return new HighPassFilter(component);
            default:
                return new BandPassFilter(component);
        }
    }

    /**
     * Returns an ILineOut instance.
     *
     * @param component IComponent
     * @param type LineType
     * @return ILineOut
     */
    public static ILineOut createLineOut(IComponent component, LineType type) {
        switch(type) {
            case OUT:
                return new LineOut(component);
            default:
                return new LineOut(component);
        }
    }

    /**
     * Return the synthetizer singleton
     *
     * @return Synthesizer
     */
    public static Synthesizer createSynthesizer() {
        if (synthesizer == null) {
            synthesizer = new SynthesisEngine();
            synthesizer.setRealTime(true);
            // Parametrization of the synthetizer
        }
        return synthesizer;
    }

    /**
     * Create modular based on potentiometer
     *
     * @param component IComponent
     * @param type ModulatorType
     * @return IModulator
     */
    public static IModulator createModulator(String name, IComponent component, ModulatorType type, PotentiometerType potentiometerType) {
        switch(type) {
            case AMPLITUDE:
                return new AmplitudeModulator(name, component, potentiometerType);
            case FREQUENCY:
                return new FrequencyModulator(name, component, potentiometerType);
            case GAIN:
                return new GainModulator(name, component, potentiometerType);
            case VCOA:
                return new VcoaFrequencyModulator(name, component, potentiometerType);
            case VCA:
                return new VcaAmplitudeModulator(name, component, potentiometerType);
            default:
                return new AmplitudeModulator(name, component, potentiometerType);
        }
    }

    /**
     * Create a simple passThrough module for the Replicator component
     *
     * @param component IComponent
     * @return IPassThrough
     */
    public static IPassThrough createPassThrough(IComponent component){
        return new PassThrough(component);
    }

    public static IEnvelopeDAHDSR createEnvelopeDAHDSR(IComponent component) { return new EnvelopeDAHDSR(component); }

    /**
     * Returns a IFunction instance
     *
     * @param component IComponent
     * @param type FunctionType
     * @return IFunction
     */
    public static IFunction createFunction(IComponent component, FunctionType type) {
        switch(type) {
            case MULTIPLY:
                return new Multiply(component);
            case SUBSTRACT:
                return new Substract(component);
            case ADD:
                return new Add(component);
            case DIVIDE:
                return new Divide(component);
            default:
                return null;
        }
    }
}
