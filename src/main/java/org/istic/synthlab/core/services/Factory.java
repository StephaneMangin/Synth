package org.istic.synthlab.core.services;

import com.jsyn.Synthesizer;
import com.jsyn.devices.AudioDeviceManager;
import com.jsyn.engine.SynthesisEngine;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.algorithms.IVcoaFrequencyModulator;
import org.istic.synthlab.core.modules.algorithms.VcoaFrequencyModulator;
import org.istic.synthlab.core.modules.filters.*;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.io.Input;
import org.istic.synthlab.core.modules.io.Output;
import org.istic.synthlab.core.modules.lineOuts.ILineOut;
import org.istic.synthlab.core.modules.lineOuts.LineOut;
import org.istic.synthlab.core.modules.lineOuts.LineType;
import org.istic.synthlab.core.modules.modulators.*;
import org.istic.synthlab.core.modules.oscillators.*;


/**
 * Helper class to manage adapters creation.
 *
 * @author Stéphane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
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
        IInput in = new Input(name, component, unitInputPort);
        Register.declare(component, in, unitInputPort);
        return in;
    }

    /**
     * Return an IOutput instance.
     *
     * @param component IComponent
     * @param unitOutputPort UnitOutputPort
     * @return IOutput
     */
    public static IOutput createOutput(String name, IComponent component, UnitOutputPort unitOutputPort) {
        IOutput out = new Output(name, component, unitOutputPort);
        Register.declare(component, out, unitOutputPort);
        return out;
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
    public static IVcoaFrequencyModulator createVcoaAlgorithm(IComponent component) {
        return new VcoaFrequencyModulator(component);
    }

    /**
     * Return the synthetizer singleton
     *
     * @return Synthesizer
     */
    public static Synthesizer createSynthesizer() {
        if (synthesizer == null) {
            synthesizer = new SynthesisEngine();
            //synthesizer.setRealTime(true); // By default
            synthesizer.start(44100, AudioDeviceManager.USE_DEFAULT_DEVICE, 2, AudioDeviceManager.USE_DEFAULT_DEVICE, 2);
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
    public static IModulator createModulator(String name, IComponent component, ModulatorType type) {
        switch(type) {
            case AMPLITUDE:
                return new AmplitudeModulator(name, component);
            case FREQUENCY:
                return new FrequencyModulator(name, component);
            case GAIN:
                return new GainModulator(name, component);
            default:
                return new AmplitudeModulator(name, component);
        }
    }
}