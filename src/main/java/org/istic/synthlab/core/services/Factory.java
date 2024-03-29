package org.istic.synthlab.core.services;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.engine.SynthesisEngine;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;
import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.modules.envelope.EnvelopeDAHDSR;
import org.istic.synthlab.core.modules.envelope.IEnvelopeDAHDSR;
import org.istic.synthlab.core.modules.filters.*;
import org.istic.synthlab.core.modules.functions.*;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.io.Input;
import org.istic.synthlab.core.modules.io.Output;
import org.istic.synthlab.core.modules.keyboard.IKeyboard;
import org.istic.synthlab.core.modules.lineOuts.ILineOut;
import org.istic.synthlab.core.modules.lineOuts.LineOut;
import org.istic.synthlab.core.modules.lineOuts.LineType;
import org.istic.synthlab.core.modules.mix.IMix;
import org.istic.synthlab.core.modules.mix.Mix;
import org.istic.synthlab.core.modules.modulators.*;
import org.istic.synthlab.core.modules.keyboard.Keyboard;
import org.istic.synthlab.core.modules.whitenoise.IWhiteNoise;
import org.istic.synthlab.core.modules.whitenoise.WhiteNoise;
import org.istic.synthlab.core.modules.oscillators.*;
import org.istic.synthlab.core.modules.oscilloscope.IOscilloscope;
import org.istic.synthlab.core.modules.oscilloscope.Oscilloscope;
import org.istic.synthlab.core.modules.passThrough.IPassThrough;
import org.istic.synthlab.core.modules.passThrough.PassThrough;
import org.istic.synthlab.core.modules.sequencer.ISequencer;
import org.istic.synthlab.core.modules.sequencer.SequencerModule;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * Helper class to manage adapters creation.
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 *
 */
public class Factory {

    // A singleton for the synthesizer
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
            case WHITENOISE:
                return new WhiteNoiseOscillator(component);
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
     * Return the synthesizer singleton
     *
     * @return Synthesizer
     */
    public static Synthesizer createSynthesizer() {
        if (synthesizer == null) {
            synthesizer = new SynthesisEngine();
            synthesizer.setRealTime(true);
            // Parametrization of the synthesizer
        }
        return synthesizer;
    }

    public static void uglyResetSynthesizer() {

        for(IComponent c : Register.mappingGenerator.keySet()){
            for(UnitGenerator ug : Register.mappingGenerator.get(c)){
                synthesizer.remove(ug);
            }
        }

        Register.mappingGenerator.clear();
        Register.mappingInput.clear();
        Register.mappingOutput.clear();

        ((SynthesisEngine) synthesizer).printConnections();


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
            case BYPASS:
                return new ByPassModulator(name, component, potentiometerType);
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

    public static IWhiteNoise createWhiteNoise(IComponent component) { return new WhiteNoise(component);}

    public static IOscillator createRedNoise(IComponent component) { return new RedNoiseOscillator(component);}

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
            case SUBTRACT:
                return new Substract(component);
            case ADD:
                return new Add(component);
            case DIVIDE:
                return new Divide(component);
            default:
                return null;
        }
    }

    /**
     * Create a simple oscilloscope module for the Oscilloscope component
     *
     * @param component IComponent
     * @return IOscilloscope
     */
    public static IOscilloscope createOscilloscope(IComponent component) {
        return new Oscilloscope(component);
    }

    /**
     * Create a simple mixer module for the Mixer component
     *
     * @param component IComponent
     * @return IMix
     */
    public static IMix createMixer(IComponent component){
        return new Mix(component);
    }

    /**
     * Create a simple sequencer module for the Sequencer component
     *
     * @param component IComponent
     * @return ISequencer
     */
    public static ISequencer createSequencer(IComponent component){
        return new SequencerModule(component);
    }

    /**
     * Create a simple keyboard module for the Keyboard component
     *
     * @param component IComponent
     * @return IKeyboard
     */
    public static IKeyboard createKeyboard(IComponent component){
        return new Keyboard(component);
    }

}
