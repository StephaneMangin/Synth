package org.istic.synthlab.prototype;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.SineOscillator;

/**
 * Created by cyprien on 08/02/16.
 */
public class PassThroughTesting {

    private Synthesizer synth;
    private LineOut out1;
    private LineOut out2;
    private LineOut out3;
    private SineOscillator sineOsc;
    private PassThrough passThrough;


    public void setUp() throws Exception {

        // Create a synthesizer
        synth = JSyn.createSynthesizer();

        passThrough = new PassThrough();

        // Prepare a SineOscillator (its amplitude will be modulated by the envelope)
        sineOsc = new SineOscillator();
        sineOsc.amplitude.set(1.0);
        sineOsc.frequency.set(320.0);

        // LineOut
        out1 = new LineOut();
        out2 = new LineOut();
        out3 = new LineOut();

        synth.add(out1);
        synth.add(out2);
        synth.add(out3);
        synth.add(passThrough);
        synth.add(sineOsc);
    }

    public void SimpleTestOneInputToThreeOutput() throws InterruptedException {

        sineOsc.output.connect(passThrough.input);

        passThrough.output.connect(out1.input);
        passThrough.output.connect(out2.input);
        passThrough.output.connect(out3.input);

        out2.setEnabled(false);
        out3.setEnabled(false);

        synth.start();
        out1.start();
        synth.sleepFor(3.0);

    }

    public void SimpleTest() throws InterruptedException {

        // Create a synthesizer
        synth = JSyn.createSynthesizer();

        // Prepare a SineOscillator (its amplitude will be modulated by the envelope)
        sineOsc = new SineOscillator();
        sineOsc.amplitude.set(1.0);
        sineOsc.frequency.set(320.0);

        synth.add(sineOsc);
        synth.start();
        sineOsc.start();

        int n = 30;
        while (n > 0){
            n--;
            System.out.println(sineOsc.output.getValue());
            synth.sleepFor(0.1);
        }



    }
}
