package org.istic.synthlab.core.services;

import com.jsyn.Synthesizer;
import com.jsyn.engine.SynthesisEngine;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.io.InputAdapter;
import org.istic.synthlab.core.modules.io.OutputAdapter;
import org.istic.synthlab.core.modules.oscillators.*;
import org.istic.synthlab.core.modules.filters.*;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.lineOuts.ILineOut;
import org.istic.synthlab.core.modules.lineOuts.LineAdapter;
import org.istic.synthlab.core.modules.lineOuts.LineType;


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
     * @param unitInputPort UnitInputPort
     * @return IInput
     */
    public static IInput createInput(UnitInputPort unitInputPort) {
        return new InputAdapter(unitInputPort);
    }

    /**
     * Return an IOutput instance.
     *
     * @param unitOutputPort UnitOutputPort
     * @return IOutput
     */
    public static IOutput createOutput(UnitOutputPort unitOutputPort) {
        return new OutputAdapter(unitOutputPort);
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
                return new SineOscillatorAdapter();
            case TRIANGLE:
                return new TriangleOscillatorAdapter();
            case SQUARE:
                return new SquareOscillatorAdapter();
            case SAWTOOTH:
                return new SawtoothOscillatorAdapter();
            case PULSE:
                return new PulseOscillatorAdapter();
            case IMPULSE:
                return new ImpulseOscillatorAdapter();
            default:
                return new SineOscillatorAdapter();
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
                return new BandPassFilterAdapter();
            case LOWPASS:
                return new LowPassFilterAdapter();
            case HIGHPASS:
                return new HighPassFilterAdapter();
            default:
                return new BandPassFilterAdapter();
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
                return new LineAdapter();
            default:
                return new LineAdapter();
        }
    }

    /**
     * Return the synthetizer singleton
     *
     * @return Synthesizer
     */
    public static Synthesizer createSynthesizer() {
        return synthesizer;
    }
}
