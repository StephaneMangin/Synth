package org.istic.synthlab.core;

import com.jsyn.Synthesizer;
import com.jsyn.engine.SynthesisEngine;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.io.InputAdapter;
import org.istic.synthlab.core.modules.io.OutputAdapter;
import org.istic.synthlab.core.modules.oscillators.IOscillator;
import org.istic.synthlab.core.modules.oscillators.OscillatorType;
import org.istic.synthlab.core.modules.oscillators.SineOscillatorAdapter;
import org.istic.synthlab.core.modules.filters.*;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.lineOuts.ILineOut;
import org.istic.synthlab.core.modules.lineOuts.LineAdapter;
import org.istic.synthlab.core.modules.lineOuts.LineType;


/**
 * The type Adapter factory that instantiates component
 *
 * @author Group1
 */
public class AdapterFactory {

    private static Synthesizer synthesizer = new SynthesisEngine();

    /**
     * Create an input port.
     *
     * @param unitInputPort the unit input port
     * @return the input : IInput
     */
    public static IInput createInput(UnitInputPort unitInputPort) {
        return new InputAdapter(unitInputPort);
    }

    /**
     * Create an output port.
     *
     * @param unitOutputPort the unit output port
     * @return the output : IOutput
     */
    public static IOutput createOutput(UnitOutputPort unitOutputPort) {
        return new OutputAdapter(unitOutputPort);
    }

    /**
     * Create an oscillator.
     *
     * @param component the component
     * @param type      the type of the oscillator
     * @return the oscillator
     */
    public static IOscillator createOscillator(IComponent component, OscillatorType type) {
        switch (type) {
            case SINE:
                return new SineOscillatorAdapter();
            default:
                return new SineOscillatorAdapter();
        }
    }

    /**
     * Create a filter.
     *
     * @param component the component
     * @param type      the type of the filter
     * @return the filter
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
     * Create a line out.
     *
     * @param component the component
     * @param type      the Linetype
     * @return the line out
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
     * Create a synthesizer.
     *
     * @return the synthesizer
     */
    public static Synthesizer createSynthesizer() {
        return synthesizer;
    }
}
