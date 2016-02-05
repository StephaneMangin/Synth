package org.istic.synthlab.core.services;

import com.jsyn.Synthesizer;
import com.jsyn.engine.SynthesisEngine;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.algorithms.IVcoaFrequencyModulator;
import org.istic.synthlab.core.modules.algorithms.VcoaFrequencyModulatorAdapter;
import org.istic.synthlab.core.modules.filters.*;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.io.InputAdapter;
import org.istic.synthlab.core.modules.io.OutputAdapter;
import org.istic.synthlab.core.modules.lineOuts.ILineOut;
import org.istic.synthlab.core.modules.lineOuts.LineAdapter;
import org.istic.synthlab.core.modules.lineOuts.LineType;
import org.istic.synthlab.core.modules.modulators.*;
import org.istic.synthlab.core.modules.oscillators.*;


/**
 * Helper class to manage adapters creation.
 *
 * @author Stéphane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 *
 */
public class ModulesFactory {

    // A singleton for the synthetizer
    private static Synthesizer synthesizer = new SynthesisEngine();

    /**
     * Return a IInput instance.
     *
     * @param component IComponent
     * @param unitInputPort UnitInputPort
     * @return IInput
     */
    public static IInput createInput(IComponent component, UnitInputPort unitInputPort) {
        IInput in = new InputAdapter(component, unitInputPort);
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
    public static IOutput createOutput(IComponent component, UnitOutputPort unitOutputPort) {
        IOutput out = new OutputAdapter(component, unitOutputPort);
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
                return new SineOscillatorAdapter(component);
            case TRIANGLE:
                return new TriangleOscillatorAdapter(component);
            case SQUARE:
                return new SquareOscillatorAdapter(component);
            case SAWTOOTH:
                return new SawtoothOscillatorAdapter(component);
            case PULSE:
                return new PulseOscillatorAdapter(component);
            case IMPULSE:
                return new ImpulseOscillatorAdapter(component);
            default:
                return new SineOscillatorAdapter(component);
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
                return new BandPassFilterAdapter(component);
            case LOWPASS:
                return new LowPassFilterAdapter(component);
            case HIGHPASS:
                return new HighPassFilterAdapter(component);
            default:
                return new BandPassFilterAdapter(component);
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
                return new LineAdapter(component);
            default:
                return new LineAdapter(component);
        }
    }
    public static IVcoaFrequencyModulator createVcoaAlgorithm(IComponent component) {
        return new VcoaFrequencyModulatorAdapter(component);
    }

    /**
     * Return the synthetizer singleton
     *
     * @return Synthesizer
     */
    public static Synthesizer createSynthesizer() {
        return synthesizer;
    }

    /**
     * Create modular based on potentiometer
     *
     * @param component IComponent
     * @param type ModulatorType
     * @return IModulator
     */
    public static IModulator createModulator(IComponent component, ModulatorType type) {
        switch(type) {
            case AMPLITUDE:
                return new AmplitudeModulatorAdapter(component);
            case FREQUENCY:
                return new FrequencyModulatorAdapter(component);
            case GAIN:
                return new GainModulatorAdapter(component);
            default:
                return new AmplitudeModulatorAdapter(component);
        }
    }
}
