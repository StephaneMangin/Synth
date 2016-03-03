package org.istic.synthlab.prototype;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.FilterStateVariable;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;
import org.junit.Test;

/**
 * Created by cyprien on 11/02/16.
 */
public class ProofFilterAmplitude {

    private Synthesizer synth;

    @Test
    public void proofFilterAmplitude() throws InterruptedException {
        synth = JSyn.createSynthesizer();

        LineOut myOut = new LineOut();
        SineOscillator myOsc = new SineOscillator();
        FilterStateVariable myFilter = new FilterStateVariable();

        Potentiometer potentiometer = new Potentiometer("Amplitude", myFilter.amplitude, PotentiometerType.LINEAR,
                1, 0, 2
        );

        synth.add(myOut);
        synth.add(myOsc);
        synth.add(myFilter);

        myOsc.frequency.set(220.0);
        myOsc.amplitude.set(1.0);

        myFilter.amplitude.set(1.0);

        myOsc.output.connect(myFilter.input);
        myFilter.output.connect(0, myOut.input, 0);
        myFilter.output.connect(0, myOut.input, 1);

        myOut.start();
        synth.start();

        int n = 1000;
        while (n > 0) {

            if (n % 50 == 0){
                System.out.println(myFilter.amplitude.getValue());
                // System.out.println(synth.getCurrentTime());
            }

            //myFilter.amplitude.set((double) n / 1000);
            potentiometer.setValue((double) n / 1000);

            n--;
            synth.sleepFor(0.001);
        }

    }
}
