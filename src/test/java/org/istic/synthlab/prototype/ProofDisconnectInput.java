package org.istic.synthlab.prototype;

import com.jsyn.Synthesizer;
import org.istic.synthlab.components.out.Out;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.services.Factory;

/**
 * @author gottstein[dot]cyprien[at]gmail[dot]com on 09/02/16.
 *
 * Proof that disconnects a component by input is properly working.
 *
 */
public class ProofDisconnectInput {

    private Vcoa vcoa;
    private Out out;

    public ProofDisconnectInput() {
        vcoa = new Vcoa("VCOA");
        out = new Out("OUT");

        vcoa.setAmplitudeSine(1.0);
        vcoa.setExponentialFrequency(0.4);

        vcoa.getOutput().connect(out.getInput());

    }

    public void run() throws InterruptedException {
        out.start();
        Synthesizer synth = Factory.createSynthesizer();
        synth.start();

        synth.sleepFor(2.0);

        out.getInput().disconnect();

        System.out.println("VCOA HAS BEEN DISCONNECTED !");

    }

}
