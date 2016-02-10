package org.istic.synthlab.core;

import com.jsyn.Synthesizer;
import com.jsyn.engine.SynthesisEngine;
import org.istic.synthlab.components.eg.Eg;
import org.istic.synthlab.components.out.Out;
import org.istic.synthlab.components.replicator.Replicator;
import org.istic.synthlab.components.vca.Vca;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.modules.lineOuts.ILineOut;
import org.istic.synthlab.core.modules.lineOuts.LineType;
import org.istic.synthlab.core.modules.oscillators.IOscillator;
import org.istic.synthlab.core.modules.oscillators.ImpulseOscillator;
import org.istic.synthlab.core.modules.oscillators.OscillatorType;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

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
    public void simpleSineOscillator() throws InterruptedException {
        IComponent componentOut = Mockito.mock(Out.class);
        IComponent componentSin = Mockito.mock(Vcoa.class);
        IOscillator sin = Factory.createOscillator(componentSin, OscillatorType.SINE);

        sin.setFrequency(320.0);
        sin.setAmplitude(1.0);
        sin.activate();

        ILineOut lineOut = Factory.createLineOut(componentOut, LineType.OUT);

        sin.getOutput().connect(lineOut.getInput());

        Synthesizer synth = Factory.createSynthesizer();

        lineOut.start();
        lineOut.setVolume(1.0);

        synth.start();

        int n = 20;

        while ( n > 0 ){
            n--;
            System.out.println("sine output : " + sin.getOutput().getUnitOutputPort().getValue());
            System.out.println("sine amplitude : " + sin.getAm().getUnitInputPort().getValue());
            synth.sleepFor(0.1);
        }
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

    @Test
    public void testReplicator() throws InterruptedException {

        Vcoa myVcoa = new Vcoa("VCOA1");

        Replicator repl = new Replicator("REPL");

        Out myOut = new Out("OUT1");
        myOut.setAmplitude(1.0);

        myVcoa.setExponentialFrequency(40);
        myVcoa.setAmplitudeSine(1.0);
        myVcoa.getOutput().connect(repl.getInput());

        repl.getOutputReplicated1().connect(myOut.getInput());
        myOut.start();
        synth.start();
        synth.sleepFor(5);

        ((SynthesisEngine) synth).printConnections();

    }

    @Test
    public void testEnvelope() throws InterruptedException {

        Eg myEnvelope = new Eg("Envelope");
        myEnvelope.setAmplitude(1.0);
        myEnvelope.setDelay(0.0);
        myEnvelope.setAttack(0.2);
        myEnvelope.setHold(1.0);
        myEnvelope.setDecay(1.0);
        myEnvelope.setRelease(2.0);
        myEnvelope.setSustain(0.4);

        myEnvelope.getInput().getUnitInputPort().set(1.0);
        myEnvelope.getOutput().connect(vcoa.getInput());
        vcoa.getOutput().connect(out.getInput());

        //vcoa.setExponentialFrequency();

        out.start();

        synth.start();
        synth.sleepFor(1.0);

        int n = 50;
        while (n > 0){

            // at the 20th LOOP
            // Around the middle of the execution corresponding to two full seconds (needed to execute
            // a full envelope from beginning to the sustain phase)
            if (n == 30) {
                // Force the signal to 0.0 ("low" state)
                // it will trigger the end of the envelope, the release phase.
                myEnvelope.getInput().getUnitInputPort().set(0.0);
            }

            //System.out.println("Square output : " + squareOsc.output.getValue());
            System.out.println("envelope input : " + myEnvelope.getInput().getUnitInputPort().getValue());
            System.out.println("envelope output : " + myEnvelope.getOutput().getUnitOutputPort().getValue());
            synth.sleepFor(0.1);
            n--;
        }

        ((SynthesisEngine) synth).printConnections();
    }

}
