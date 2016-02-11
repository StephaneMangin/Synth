package org.istic.synthlab.prototype;

import com.jsyn.Synthesizer;
import org.istic.synthlab.components.eg.Eg;
import org.istic.synthlab.components.out.Out;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.services.Factory;

/**
 * Created by cyprien on 10/02/16.
 */
public class EgPrototype {

    private Eg eg;
    private Out out;
    private Vcoa vcoa;
    private Synthesizer synth;

    public void simpleEgTest() throws InterruptedException {

        synth = Factory.createSynthesizer();

        eg = new Eg("ENVELOPE");
        out = new Out("OUT");
        vcoa = new Vcoa("VCOA");

        eg.setAmplitude(1.0);
        eg.setDelay(0.0);
        eg.setAttack(0.2);
        eg.setHold(1.0);
        eg.setDecay(1.0);
        eg.setRelease(2.0);
        eg.setSustain(0.4);

        vcoa.setExponentialFrequency(200);
        vcoa.setAmplitudeSine(1.0);

        out.getAmModulator().setValue(1.0);

        eg.getInput().getUnitInputPort().set(1.0);
        eg.getOutput().connect(vcoa.getAm());
        vcoa.getOutput().connect(out.getInput());

        out.start();
        synth.start();

        synth.sleepFor(3.0);

        eg.getInput().getUnitInputPort().set(0.0);
        eg.setAmplitude(0.0);
        vcoa.setAmplitudeSine(0.0);
        System.out.println("PROC");
        System.out.println(eg.getOutput().getUnitOutputPort().getValue());

        synth.sleepFor(3.0);

        // Test not functionnal as long as the VCA Component won't be completed.
        // Do not take it as granted.
    }
}
