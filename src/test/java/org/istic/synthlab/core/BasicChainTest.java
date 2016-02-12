package org.istic.synthlab.core;

import com.jsyn.Synthesizer;
import com.jsyn.engine.SynthesisEngine;
import com.jsyn.scope.AudioScope;
import org.istic.synthlab.components.eg.Eg;
import org.istic.synthlab.components.out.Out;
import org.istic.synthlab.components.replicator.Replicator;
import org.istic.synthlab.components.vca.Vca;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.modules.oscillators.OscillatorType;
import org.istic.synthlab.core.modules.oscillators.SineOscillator;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

/**
 * @author Cyprien
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
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void basicChainTest() throws InterruptedException {
        out.getInput().connect(vcoa.getOutput());
        out.start();
        synth.start();
        synth.sleepUntil(5);
        ((SynthesisEngine)synth).printConnections();
    }

    @Test
    public void TestVcoaToVcoa() throws InterruptedException {
        out.getInput().connect(vcoa.getOutput());
        Vcoa vcoa1 = new Vcoa("VCOA1");
        vcoa1.setAmplitudeSine(50);
        vcoa1.getOutput().connect(vcoa.getInput());
        vcoa.getSawToothOutput().connect(out.getInput());

        // Pour l'affichage des courbes
        AudioScope scope = new AudioScope( synth );
        scope.addProbe(vcoa.getTriangleOutput().getUnitOutputPort());
        scope.setTriggerMode( AudioScope.TriggerMode.AUTO );
        scope.getModel().getTriggerModel().getLevelModel().setDoubleValue( 0.0001 );
        scope.getView().setControlsVisible(true);
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
        out.getInput().connect(vcoa.getOutput());
        synth.start();
        out.start();
        synth.sleepUntil(3);
        out.getAmModulator().setValue(0);
        synth.sleepUntil(6);
        out.getAmModulator().setValue(0.1);
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
        Vcoa vcoa2 = new Vcoa("VCOA2");
        vcoa.setOscillatorType(OscillatorType.SINE);
        vcoa.setAmplitudeSine(1);
        //vcoa2.setExponentialFrequency(440.0);
        //vcoa2.setAmplitudeSine(1.0);
        vcoa2.activate();
        vcoa2.setAmplitudeSquare(1);
        vcoa2.setExponentialFrequency(200);
        vca.setGainModulatorValue(0.0);

        vcoa.getOutput().connect(vca.getInput());
        //vcoa2.getOutput().connect(vca.getAm());
        vcoa2.getOutput().connect(this.out.getInput());

        out.start();
        synth.start();

        ((SynthesisEngine)synth).printConnections();

        int n = 2000;
        while (n >= 0) {

            if (n % 50 == 0){
                if (out.getAmModulator().getValue() > 0.0){
                    out.getAmModulator().setValue(0.0);
                } else {
                    out.getAmModulator().setValue(1.0);
                }

            }

            //System.out.println(out.getAmModulator().getInputB().isConnected());
            //out.getAmModulator().getInputB().setValueInternal(((double) 200 - (double) n) / (double) 200);
            //out.getAmModulator().setValue(((double) 2000 - (double) n) / (double) 2000);
            out.getAmModulator().getValue();
            //System.out.println(out.getAmModulator().getValue());
            //assertEquals(0D, vca.getOutput().getUnitOutputPort().getValue(), 0.0);
            //System.out.println(vcoa2.getOutput().getUnitOutputPort().getValue());

            //assertNotEquals(0D, vca.getOutput().getUnitOutputPort().getValue(), 0.0);

            synth.sleepFor(0.01);
            n--;
        }

       ((SynthesisEngine)synth).printConnections();
    }

    @Test
    public void TestVolume() throws InterruptedException {
        vcoa.setOscillatorType(OscillatorType.SINE);
        vcoa.setAmplitudeSine(1);
        vcoa.setLinearFrequency(320.0);

        vcoa.getInputByPass().activate();

        vcoa.getOutput().connect(out.getInput());

        out.start();
        synth.start();

        Assert.assertNotEquals(Register.retrieve(out.getInput()),Register.retrieve(out.getLineOut().getInput()));


        ((SynthesisEngine)synth).printConnections();

        int n = 200;
        while (n >= 0) {

            if (n % 20 == 0){

                if (out.getAmModulator().getValue() > 0.0){
                    out.getAmModulator().setValue(0.0);
                } else {
                    out.getAmModulator().setValue(1.0);
                }

/*                if (out.getLineOut().getInput().getUnitInputPort() > 0.0){
                    out.getLineOut().setVolume(0.0);
                } else {
                    out.getLineOut().setVolume(1.0);
                }*/

/*                if (vcoa.getAmplitudeSine() > 0.0){
                    vcoa.setAmplitudeSine(0.0);
                } else {
                    vcoa.setAmplitudeSine(1.0);
                }*/

            }

            System.out.println("Vcoa.getOutput().getValue() : " + vcoa.getOutput().getUnitOutputPort().getValue());
            //System.out.println("out.getLineOut().getInput().getValue() : " + out.getLineOut().getInput().getUnitInputPort().getValue());
            //System.out.println("AmplitudeModulator : " + out.getAmModulator().getValue());
            System.out.println("out.getAmModulator().getValue() : " + out.getAmModulator().getValue());
            System.out.println("out.getAmModulator().getInput().getUnitInputPort().getValue() : " + out.getAmModulator().getInput().getUnitInputPort().getValue());
            System.out.println("out.getAmModulator().getOutput().getUnitOutputPort().getValue() : " + out.getAmModulator().getOutput().getUnitOutputPort().getValue());
            System.out.println("out.getLineOut().getInput().getUnitInputPort().getValue() : " + out.getLineOut().getInput().getUnitInputPort().getValue());
            //out.getLineOut().getVolume();

            synth.sleepFor(0.1);
            n--;
        }

        ((SynthesisEngine)synth).printConnections();
    }

    @Test
    public void testReplicator() throws InterruptedException {

        Vcoa myVcoa = new Vcoa("VCOA1");

        Replicator repl = new Replicator("REPL");

        Out myOut = new Out("OUT1");
        myOut.getAmModulator().setValue(1.0);

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
        myOut.getAmModulator().setValue(1.0);

        envelope.setAttack(0.2);
        envelope.setDecay(0.2);
        envelope.setRelease(1);
        envelope.setSustain(0.1);
        envelope.activate();

        // we create the first sine  oscillator that will be connected to the input gate of EG
        SineOscillator sineOscillator = (SineOscillator) Factory.createOscillator(Vcoa, OscillatorType.SINE);
        sineOscillator.getFrequencyPotentiometer().setValue(0.5);
        sineOscillator.getAmplitudePotentiometer().setValue(1.0);
        sineOscillator.activate();
        sineOscillator.getOutput().connect(envelope.getInput());

        // we create the oscillator whose amplitude is controlled by the envelope
        SineOscillator sineOscillator2 = (SineOscillator) Factory.createOscillator(Vcoa, OscillatorType.SINE);
        sineOscillator2.getFrequencyPotentiometer().setValue(440);

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

        //System.out.println("\n Testing EG Modulewith different parameter");

        //System.out.println("\nAttack = 1s, Decay = 1s, Sustain = 1dB, Release = 0.5s");
        synth.sleepFor(5);

        //System.out.println("\nAttack = 0.5s, Decay = 1s, Sustain = 1dB, Release = 0.5s");
        envelope.setAttack(0.5);
        synth.sleepFor(5);

        //System.out.println("\nAttack = 0.2s, Decay = 1s, Sustain = 1dB, Release = 0.5s");
        envelope.setAttack(0.2);
        envelope.setDecay(1);
        synth.sleepFor(5);




    }

}
