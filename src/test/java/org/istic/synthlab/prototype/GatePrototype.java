package org.istic.synthlab.prototype;

import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.TriangleOscillator;
import com.jsyn.unitgen.UnitGate;
import org.istic.synthlab.core.services.Factory;
import org.junit.Test;

/**
 * @author Thibaud Hulin thibaud[dot]hulin[dot]cm[at]gmail[dot]com
 * @creation 26/02/16.
 */
public class GatePrototype {
    @Test
    public void testGatePort() throws InterruptedException {
        TriangleOscillator oscillator = new TriangleOscillator();
        oscillator.frequency.set(440.0);
        oscillator.amplitude.set(0.9);
        Synthesizer synthesis = Factory.createSynthesizer();
        LineOut lineOut = new LineOut();

        synthesis.add(lineOut);
        synthesis.add(oscillator);

        MyGate gate = new MyGate();

        synthesis.add(gate);

        gate.signal.connect(oscillator.output);
        lineOut.input.connect(gate.output);

        gate.start();
        lineOut.start();
        synthesis.start();
        synthesis.sleepFor(3);
        gate.input.on();
        synthesis.sleepFor(3);
        gate.input.off();
        synthesis.sleepFor(3);
    }

    class MyGate extends UnitGate {
        public UnitInputPort signal;
        public MyGate() {
            signal = new UnitInputPort("Signal");
            signal.setDefault(0.0);
            addPort(signal);
        }
        @Override
        public void generate(int start, int limit) {
            double[] samples = signal.getValues();
            double[] outputs = output.getValues();


            for (int i = start; i < limit; i++ ){
                boolean triggered = input.checkGate(i);
                if (triggered) {
                    outputs[i] = samples[i];
                }
            }
        }
    }
}
