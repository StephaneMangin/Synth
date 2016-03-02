package org.istic.synthlab.prototype;

import com.jsyn.Synthesizer;
import org.istic.synthlab.components.noise.Noise;
import org.istic.synthlab.components.out.Out;
import org.istic.synthlab.components.vca.Vca;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.modules.oscillators.OscillatorType;
import org.istic.synthlab.core.services.Factory;

/**
 * Created by cyprien on 10/02/16.
 */
public class VcaPrototype {

    private Vca vca;
    private Out out;
    private Vcoa vcoa;
    private Noise wn;
    private Synthesizer synth;

    public VcaPrototype() throws InterruptedException {

        synth = Factory.createSynthesizer();

        vca = new Vca("ENVELOPE");
        out = new Out("OUT");
        vcoa = new Vcoa("VCOA");

        Vcoa square = new Vcoa("SQUARE");
        //wn = new Noise("NOISE");

        vcoa.setOscillatorType(OscillatorType.SINE);
        vcoa.setExponentialFrequency(0.6);
        vcoa.setLinearFrequency(0.4);
        vcoa.setAmplitudeOscillator(1.0);

        square.setOscillatorType(OscillatorType.SQUARE);
        square.setLinearFrequency(0.5);
        square.setExponentialFrequency(0.1);
        square.setAmplitudeOscillator(1.0);

        out.getAmModulator().setValue(1.0);

        vcoa.getOutput().connect(vca.getInput());
        //wn.getOutput().connect(vca.getAm());
        square.getOutput().connect(vca.getAm());

        vca.getOutput().connect(out.getInput());

        out.start();
        synth.start();

        //synth.sleepFor(3.0);
        System.out.println("STEP1");

        vca.getGainModulator().setValue(0.0);
        //out.getAmModulator().setValue(0.0);

        int n = 30;
        while (n > 0){
            //System.out.println("Output value : " + vca.getOutput().getUnitOutputPort().getValue());
            //System.out.println("Potentiometer value : " + vca.getGainModulator().getValue());
            //System.out.println("Potentiometer Output value : " + vca.getGainModulator().getOutput().getUnitOutputPort().getValue());
            synth.sleepFor(0.1);
            n--;
        }

        //synth.sleepFor(3.0);
        System.out.println("STEP2");

        vca.getGainModulator().setValue(0.5);
        System.out.println(vca.getOutput().getUnitOutputPort());

        synth.sleepFor(3.0);

        // Test not functional as long as the VCA Component won't be completed.
        // Do not take it as granted.
    }
}
