package org.istic.synthlab.core;

import com.jsyn.Synthesizer;
import com.jsyn.engine.SynthesisEngine;
import org.istic.synthlab.components.out.Out;
import org.istic.synthlab.components.vca.Vca;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.modules.oscillators.ImpulseOscillator;
import org.istic.synthlab.core.modules.oscillators.OscillatorType;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by cyprien on 04/02/16.
 */
public class BasicChainTest {

    private Vcoa vcoa;
    private Out out;
    private Synthesizer synth;

    @Before
    public void setUp() throws Exception {
        vcoa = new Vcoa("VCOA");
        vcoa.activate();
        out = new Out("OUT");
        out.activate();
        vcoa.setAmplitudeSquare(1);
        vcoa.setExponentialFrequency(200);
        vcoa.setAmplitudeSine(10000);
        //vcoa.setAmplitudeSquare(10000);
        synth = Factory.createSynthesizer();
        out.getInput().connect(vcoa.getOutput());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void basicChainTest() throws InterruptedException {
        out.start();
        synth.start();
        synth.sleepUntil(5);
        ((SynthesisEngine)synth).printConnections();
    }

    @Test
    public void TestVcoaToVcoa() throws InterruptedException {
        Vcoa vcoa1 = new Vcoa("VCOA1");
        ImpulseOscillator impulseOscillator = (ImpulseOscillator) Factory.createOscillator(vcoa1, OscillatorType.IMPULSE);
        impulseOscillator.activate();
        vcoa1.activate();
        vcoa1.getSource().connect(impulseOscillator.getFm());
        impulseOscillator.getOutput().connect(vcoa1.getSink());
        Register.connect(vcoa.getInput(), vcoa1.getOutput());
        Register.connect(out.getInput(), vcoa1.getOutput());
        out.start();
        synth.start();
        synth.sleepUntil(5);
        ((SynthesisEngine)synth).printConnections();
    }

    @Test
    public void TestVca() throws InterruptedException {
        Vca vca = new Vca("VCA");
        Vcoa vcoa1 = new Vcoa("VCOA1");
        Vcoa vcoa2 = new Vcoa("VCOA2");
        ImpulseOscillator impulseOscillator = (ImpulseOscillator) Factory.createOscillator(vcoa1, OscillatorType.IMPULSE);
        impulseOscillator.activate();
        vcoa1.activate();
        vcoa2.activate();
        vca.activate();
        vcoa1.getOutput().connect(vcoa2.getFm());
        vcoa2.getOutput().connect(vca.getInput());
        vca.getOutput().connect(this.out.getInput());
        impulseOscillator.setFrequency(500.0);
        impulseOscillator.activate();
        vcoa1.getOutput().connect(impulseOscillator.getFm());
        impulseOscillator.getOutput().connect(vca.getAm());
        Register.connect(vcoa2.getFm(), vcoa1.getOutput());
        Register.connect(vca.getInput(), vcoa2.getOutput());
        Register.connect(out.getInput(), vca.getOutput());
        Register.connect(impulseOscillator.getFm(), vcoa1.getOutput());
        Register.connect(vca.getAm(), vcoa1.getOutput());

        out.start();
        synth.start();
        synth.sleepUntil(5);
        ((SynthesisEngine)synth).printConnections();
    }

}
