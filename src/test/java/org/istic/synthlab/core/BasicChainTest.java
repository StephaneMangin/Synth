package org.istic.synthlab.core;

import com.jsyn.Synthesizer;
import com.jsyn.engine.SynthesisEngine;
import com.jsyn.scope.AudioScope;
import org.istic.synthlab.components.eg.Eg;
import org.istic.synthlab.components.out.Out;
import org.istic.synthlab.components.replicator.Replicator;
import org.istic.synthlab.components.vca.Vca;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.modules.oscillators.ImpulseOscillator;
import org.istic.synthlab.core.modules.oscillators.OscillatorType;
import org.istic.synthlab.core.modules.oscillators.SineOscillator;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

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
        vcoa1.setAmplitudeSine(50);
        vcoa1.getOutput().connect(vcoa.getInput());
        vcoa.getSawToothOutput().connect(out.getInput());

        // Pour l'affichage des courbes
        AudioScope scope = new AudioScope( synth );
        scope.addProbe(vcoa.getTriangleOutput().getUnitOutputPort());
        scope.setTriggerMode( AudioScope.TriggerMode.AUTO );
        scope.getModel().getTriggerModel().getLevelModel().setDoubleValue( 0.0001 );
        scope.getView().setShowControls( true );
        scope.start();
        JFrame frame = new JFrame();
        frame.add(scope.getView());
        frame.pack();
        frame.setVisible(true);

        out.start();
        synth.start();
        synth.sleepUntil(5);
        ((SynthesisEngine)synth).printConnections();
    }

    @Test
    public void TestOutParameters() throws InterruptedException {
        synth.start();
        out.start();
        synth.sleepUntil(3);
        out.setAmplitude(0);
        synth.sleepUntil(6);
        out.setAmplitude(0.1);
        synth.sleepUntil(9);

        ((SynthesisEngine)synth).printConnections();
    }

    @Test
    public void TestVcoaSwitch() throws InterruptedException {
        vcoa.setAmplitudeRedNoise(1);
        vcoa.setAmplitudeSquare(1);
        vcoa.setExponentialFrequency(440);
        vcoa.getOutput().connect(out.getInput());

        out.start();
        synth.start();
        synth.sleepUntil(3);
        vcoa.setExponentialFrequency(880);
        vcoa.setAmplitudeSquare(0);
        synth.sleepUntil(6);
        vcoa.setAmplitudeSquare(1);
        synth.sleepUntil(9);
        vcoa.setOscillatorType(OscillatorType.REDNOISE);
        synth.sleepUntil(12);
        System.out.println("END");
        synth.stop();
        //((SynthesisEngine)synth).printConnections();
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
    public void testEg() throws InterruptedException {

        Vcoa Vcoa = new Vcoa("VCOA");
        Eg envelope = new Eg("ENVELOPE");
        Out myOut = new Out("OUT1");
        myOut.setAmplitude(1.0);

        envelope.setAttack(0.2);
        envelope.setDecay(0.2);
        envelope.setRelease(1);
        envelope.setSustain(0.1);
        envelope.activate();

        // we create the first sine  oscillator that will be connected to the input gate of EG
        SineOscillator sineOscillator = (SineOscillator) Factory.createOscillator(Vcoa, OscillatorType.SINE);
        sineOscillator.setFrequency(0.5);
        sineOscillator.setAmplitude(1.0);
        sineOscillator.activate();
        sineOscillator.getOutput().connect(envelope.getInput());

        // we create the oscillator whose amplitude is controlled by the envelope
        SineOscillator sineOscillator2 = (SineOscillator) Factory.createOscillator(Vcoa, OscillatorType.SINE);
        sineOscillator2.setFrequency(440);

        envelope.getInput().getUnitInputPort().set(1.0);
        envelope.getOutput().connect(sineOscillator2.getAm());
        sineOscillator2.getOutput().connect(myOut.getInput());
        sineOscillator2.activate();

        myOut.start();
        synth.start();
        synth.sleepFor(5);
        ((SynthesisEngine) synth).printConnections();

        // For the display of curves
        AudioScope scope = new AudioScope(synth);
        scope.addProbe(sineOscillator2.getOutput().getUnitOutputPort());

        scope.setTriggerMode(AudioScope.TriggerMode.AUTO);
        scope.getModel().getTriggerModel().getLevelModel()
                .setDoubleValue(0.0001);
        scope.getView().setShowControls(true);
        scope.start();
        JFrame frame = new JFrame();
        frame.add(scope.getView());
        frame.pack();
        frame.setVisible(true);

        System.out.println("\n Testing EG Modulewith different parameter");

        System.out.println("\nAttack = 1s, Decay = 1s, Sustain = 1dB, Release = 0.5s");
        synth.sleepFor(5);

        System.out.println("\nAttack = 0.5s, Decay = 1s, Sustain = 1dB, Release = 0.5s");
        envelope.setAttack(0.5);
        synth.sleepFor(5);

        System.out.println("\nAttack = 0.2s, Decay = 1s, Sustain = 1dB, Release = 0.5s");
        envelope.setAttack(0.2);
        envelope.setDecay(1);
        synth.sleepFor(5);




    }

}
