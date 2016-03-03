package org.istic.synthlab.prototype;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;

/**
 * Created by cyprien on 11/02/16.
 */
public class JSynSynchronizeTesting {

    Synthesizer synth;
    SineOscillator sin;
    LineOut out;

    public JSynSynchronizeTesting() throws InterruptedException {
        synth = JSyn.createSynthesizer();

        sin = new SineOscillator();
        sin.amplitude.set(1.0);
        sin.frequency.set(320.0);

        synth.add(sin);

        out = new LineOut();
        synth.add(out);

        sin.output.connect(out.getInput());

        synth.start();
        out.start();

        ThreadUpdater tu = new ThreadUpdater(sin);
        Thread t = new Thread(tu);
        t.start();

        synth.sleepFor(10000.0);
    }

}
