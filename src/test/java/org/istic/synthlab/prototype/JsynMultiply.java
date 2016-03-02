package org.istic.synthlab.prototype;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.Multiply;
import com.jsyn.unitgen.SineOscillator;

/**
 * Created by cyprien on 02/03/16.
 */
public class JsynMultiply {

    private LineOut lineOut;
    private Multiply multiply;
    private SineOscillator sine;
    private Synthesizer synth;

    public JsynMultiply() throws InterruptedException {

        synth = JSyn.createSynthesizer();
        lineOut = new LineOut();
        sine = new SineOscillator();
        multiply = new Multiply();

        synth.add(lineOut);
        synth.add(sine);
        synth.add(multiply);

        sine.frequency.set(320.0);
        sine.amplitude.set(1.0);

        sine.output.connect(multiply.inputA);
        multiply.inputB.set(0.0);

        multiply.output.connect(lineOut.input);

        lineOut.start();
        multiply.start();
        synth.start();

        synth.sleepFor(5.0);

    }
}
