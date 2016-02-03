package org.istic.synthlab.core;

import com.jsyn.Synthesizer;
import com.jsyn.engine.SynthesisEngine;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
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
 *
 */
public class AdapterFactory {

    private static Synthesizer synthesizer = new SynthesisEngine();

    public static IInput createInput(UnitInputPort unitInputPort) {
        return new InputAdapter(unitInputPort);
    }

    public static IOutput createOutput(UnitOutputPort unitOutputPort) {
        return new OutputAdapter(unitOutputPort);
    }

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

    public static ILineOut createLineOut(IComponent component, LineType type) {
        switch(type) {
            case OUT:
                return new LineAdapter();
            default:
                return new LineAdapter();
        }
    }

    public static Synthesizer createSynthesizer() {
        return synthesizer;
    }
}
