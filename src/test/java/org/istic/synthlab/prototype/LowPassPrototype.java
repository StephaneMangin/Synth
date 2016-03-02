package org.istic.synthlab.prototype;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.FilterStateVariable;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;
import org.junit.Test;

public class LowPassPrototype {

    private Synthesizer synth;

    @Test
    public void lowPassPrototype() throws InterruptedException {
        synth = JSyn.createSynthesizer();

        LineOut myOut = new LineOut();
        SineOscillator oscLow = new SineOscillator();
        SineOscillator oscHigh = new SineOscillator();
        FilterStateVariable myFilter = new FilterStateVariable();

        Potentiometer potentiometer = new Potentiometer("Cutoff", myFilter.frequency, PotentiometerType.LINEAR,
                2000.0, 0, 2000.0
        );
        Potentiometer resonance = new Potentiometer("Resonance", myFilter.resonance, PotentiometerType.LINEAR,
                1, 0, 0.2
        );

        synth.add(myOut);
        synth.add(oscLow);
        synth.add(oscHigh);
        synth.add(myFilter);

        oscLow.frequency.set(440.0);
        oscLow.amplitude.set(1.0);

        oscHigh.frequency.set(1440.0);
        oscHigh.amplitude.set(1.0);

        myFilter.amplitude.set(0.2);
        myFilter.frequency.set(2000.0);

        oscLow.output.connect(myFilter.input);
        oscHigh.output.connect(myFilter.input);
        myFilter.lowPass.connect(0, myOut.input, 0);
        myFilter.lowPass.connect(0, myOut.input, 1);

        myOut.start();
        synth.start();

        int n = 1000;
        while (n > 0) {

            if (n % 50 == 0){
                System.out.println(myFilter.frequency.getValue());
            }

            //resonance.setValue((double) n / 1000);
            potentiometer.setValue((double) n / 1000);
            //myFilter.frequency.set((double) n);

            n--;
            synth.sleepFor(0.001);
        }

    }
}
