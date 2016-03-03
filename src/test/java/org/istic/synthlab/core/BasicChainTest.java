package org.istic.synthlab.core;

import com.jsyn.Synthesizer;
import com.jsyn.engine.SynthesisEngine;
import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.TriangleOscillator;
import org.istic.synthlab.components.eg.Eg;
import org.istic.synthlab.components.mixer.Mixer;
import org.istic.synthlab.components.whitenoise.WhiteNoise;
import org.istic.synthlab.components.out.Out;
import org.istic.synthlab.components.replicator.Replicator;
import org.istic.synthlab.components.seq.Sequencer;
import org.istic.synthlab.components.vca.Vca;
import org.istic.synthlab.components.vcfa.Vcfa;
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
        out.getAmModulator().setValue(0.5);
        vcoa.setAmplitudeOscillator(0.5);
        vcoa.setExponentialFrequency(0.75);
        vcoa.setLinearFrequency(0.5);
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
    }

    @Test
    public void TestVcoaToVcoa() throws InterruptedException {
        out.getInput().connect(vcoa.getOutput());
        Vcoa vcoa1 = new Vcoa("VCOA1");
        vcoa1.setOscillatorType(OscillatorType.SINE);
        vcoa1.setAmplitudeSine(1.0);
        vcoa1.setExponentialFrequency(0.5);
        vcoa1.setLinearFrequency(0.5);
        vcoa1.getOutput().connect(vcoa.getFm());

        // to display curves
        //AudioScope scope = new AudioScope( synth );
        //scope.addProbe(vcoa.getTriangleOutput().getUnitOutputPort());
        //scope.setTriggerMode( AudioScope.TriggerMode.AUTO );
        //scope.getModel().getTriggerModel().getLevelModel().setDoubleValue( 0.0001 );
        //scope.getView().setControlsVisible(true);
        //scope.start();
        //JFrame frame = new JFrame();
        //frame.add(scope.getView());
        //frame.pack();
        //frame.setVisible(true);

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
        vcoa.setAmplitudeSquare(1);
        vcoa.setExponentialFrequency(0.75);
        vcoa.setLinearFrequency(0.5);
        vcoa.getOutput().connect(out.getInput());

        out.start();
        synth.start();
        synth.sleepUntil(3);
        vcoa.setExponentialFrequency(0.75);
        vcoa.setLinearFrequency(0.5);
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
        double amplitude = 0.1;
        Vca vca = new Vca("VCA");
        Vcoa vcoa2 = new Vcoa("VCOA2");

        //Configuration
        vcoa.setOscillatorType(OscillatorType.SINE);
        vcoa.setAmplitudeSine(1);
        vcoa2.setOscillatorType(OscillatorType.SQUARE);
        vcoa2.setAmplitudeSquare(amplitude);
        vcoa2.setExponentialFrequency(0.8);
        vcoa2.setLinearFrequency(0.8);
        vca.getGainModulator().setValue(0.0);

        //Connection
        vcoa.getOutput().connect(vca.getInput());
        vcoa2.getOutput().connect(vca.getAm());
        //vcoa2.getOutputPlug().connect(this.out.getInputPlug());
        vca.getOutput().connect(this.out.getInput());

        out.start();
        synth.start();

        ((SynthesisEngine)synth).printConnections();

        int n = 2000;
        while (n >= 0) {
            if (n % 50 == 0){
                if (out.getAmModulator().getValue() > 0.0){
                    out.getAmModulator().setValue(0.0);
                } else {
                    out.getAmModulator().setValue(amplitude);
                    amplitude = amplitude - 0.025;
                }

            }

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

        Assert.assertNotEquals(Register.retrieve(out.getInput()), Register.retrieve(out.getLineOut().getInput()));

        int n = 200;
        while (n >= 0) {

            if (n % 20 == 0){

                if (out.getAmModulator().getValue() > 0.0){
                    out.getAmModulator().setValue(0.0);
                } else {
                    out.getAmModulator().setValue(1.0);
                }

/*                if (out.getLineOut().getInputPlug().getUnitInputPort() > 0.0){
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

            System.out.println("Vcoa.getOutputPlug().getValue() : " + vcoa.getOutput().getUnitOutputPort().getValue());
            //System.out.println("out.getLineOut().getInputPlug().getValue() : " + out.getLineOut().getInputPlug().getUnitInputPort().getValue());
            //System.out.println("AmplitudeModulator : " + out.getAmModulator().getValue());
            System.out.println("out.getAmModulator().getValue() : " + out.getAmModulator().getValue());
            System.out.println("out.getAmModulator().getInputPlug().getUnitInputPort().getValue() : " + out.getAmModulator().getInput().getUnitInputPort().getValue());
            System.out.println("out.getAmModulator().getOutputPlug().getUnitOutputPort().getValue() : " + out.getAmModulator().getOutput().getUnitOutputPort().getValue());
            System.out.println("out.getLineOut().getInputPlug().getUnitInputPort().getValue() : " + out.getLineOut().getInput().getUnitInputPort().getValue());
            //out.getLineOut().getVolume();

            synth.sleepFor(0.1);
            n--;
        }

        ((SynthesisEngine)synth).printConnections();
    }

    @Test
    public void testReplicator() throws InterruptedException {
        Replicator repl = new Replicator("REPL");

        vcoa.getOutput().connect(repl.getInput());
        repl.getOutput2().connect(out.getInput());

        out.start();
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

        envelope.getAttackPotentiometer().setValue(0.2);
        envelope.getDecayPotentiometer().setValue(0.2);
        envelope.getReleasePotentiometer().setValue(1);
        envelope.getSustainPotentiometer().setValue(0.1);
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
        scope.getView().setControlsVisible(true);
        scope.start();
        JFrame frame = new JFrame();
        frame.add(scope.getView());
        frame.pack();
        frame.setVisible(true);

        //System.out.println("\n Testing EG Module with different parameter");

        //System.out.println("\nAttack = 1s, Decay = 1s, Sustain = 1dB, Release = 0.5s");
        synth.sleepFor(5);

        //System.out.println("\nAttack = 0.5s, Decay = 1s, Sustain = 1dB, Release = 0.5s");
        envelope.getAttackPotentiometer().setValue(0.5);
        synth.sleepFor(5);

        //System.out.println("\nAttack = 0.2s, Decay = 1s, Sustain = 1dB, Release = 0.5s");
        envelope.getAttackPotentiometer().setValue(0.2);
        envelope.getDecayPotentiometer().setValue(1);
        synth.sleepFor(5);
    }


    @Test
    public void testMixer() throws InterruptedException {
        Mixer mixer = new Mixer("MIX");
        Vcoa v1 = new Vcoa("v1");
        Vcoa v2 = new Vcoa("v2");
        Vcoa v3 = new Vcoa("v3");
        Vcoa v4 = new Vcoa("v4");

        v1.setAmplitudeOscillator(0.5);
        v1.setExponentialFrequency(0.80);
        v1.setLinearFrequency(0.5);
        v1.setOscillatorType(OscillatorType.SQUARE);

        v2.setAmplitudeOscillator(1.0);
        v2.setExponentialFrequency(0.80);
        v2.setLinearFrequency(0.55);
        v2.setOscillatorType(OscillatorType.SINE);

        v3.setAmplitudeOscillator(1.0);
        v3.setExponentialFrequency(0.60);
        v3.setLinearFrequency(0.8);
        v3.setOscillatorType(OscillatorType.SQUARE);

        v4.setAmplitudeOscillator(0.6);
        v4.setExponentialFrequency(0.5);
        v4.setLinearFrequency(0.7);
        v4.setOscillatorType(OscillatorType.SAWTOOTH);

        v1.getOutput().connect(mixer.getInput1());
        v2.getOutput().connect(mixer.getInput2());
        v3.getOutput().connect(mixer.getInput3());
        v4.getOutput().connect(mixer.getInput4());
        mixer.getOutput().connect(out.getInput());

        out.start();
        synth.start();
        synth.sleepFor(5);
        mixer.setGainValueInput2(0.000001);
        mixer.setGainValueInput3(0.000001);
        mixer.setGainValueInput4(0.000001);
        v2.setOscillatorType(OscillatorType.TRIANGLE);
        v3.setOscillatorType(OscillatorType.TRIANGLE);
        v4.setOscillatorType(OscillatorType.TRIANGLE);
        synth.sleepFor(5);

        ((SynthesisEngine) synth).printConnections();
    }
    @Test
    public void testWhiteNoise() throws InterruptedException {
        WhiteNoise whiteNoise = new WhiteNoise("WHITE NOISE");
        whiteNoise.activate();
        out.start();
        out.getInput().connect(whiteNoise.getOutput());
        synth.start();
        synth.sleepFor(10);

        ((SynthesisEngine) synth).printConnections();
    }

    @Test
    public void testSequencer() throws InterruptedException {
        vcoa.setOscillatorType(OscillatorType.SQUARE);
        vcoa.setExponentialFrequency(0.1);
        vcoa.setLinearFrequency(0.5);
        Sequencer sequencer = new Sequencer("SEQ");

        sequencer.getInputgate().connect(vcoa.getOutput());
        sequencer.getOutputSeq().connect(out.getInput());


        out.start();
        synth.start();
        synth.sleepFor(3);
        sequencer.activate();
        synth.sleepFor(5);
        sequencer.deactivate();
        synth.sleepFor(5);
    }
    @Test
    public void TestVcfa() throws InterruptedException {
        Vcfa vcfa = new Vcfa("VCFA");
        Vcoa vcoa2 = new Vcoa("VCOA2");

        //Configuration
        vcoa.setOscillatorType(OscillatorType.SINE);
        vcoa.setAmplitudeSine(1);
        vcoa2.setOscillatorType(OscillatorType.TRIANGLE);
        vcoa2.setAmplitudeTriangle(1.0);
        vcoa2.setExponentialFrequency(0.2);
        vcoa2.setLinearFrequency(0.5);
        vcfa.setCutoff(1.0);

        //Connection
        vcoa.getOutput().connect(vcfa.getInput());
        vcoa2.getOutput().connect(vcfa.getFm());
        vcfa.getOutput().connect(this.out.getInput());

        out.start();
        synth.start();

        int n = 1000;
        while (n >= 0) {
            if (n % 50 == 0){
                vcfa.setCutoff(((double) n) / 1000);
            }

            synth.sleepFor(0.01);
            n--;
        }

        ((SynthesisEngine)synth).printConnections();
    }
}
