package org.istic.synthlab.core;

import com.jsyn.Synthesizer;
import com.jsyn.engine.SynthesisEngine;
import com.jsyn.scope.AudioScope;
import org.istic.synthlab.components.out.Out;
import org.istic.synthlab.components.replicator.Replicator;
import org.istic.synthlab.components.vca.Vca;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.modules.oscillators.OscillatorType;
import org.istic.synthlab.core.modules.oscillators.SawtoothOscillator;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.ui.CoreController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;

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
        SawtoothOscillator s = (SawtoothOscillator) Factory.createOscillator(vcoa, OscillatorType.SAWTOOTH);
        vcoa.getSawToothOutput().connect(out.getInput());

        // Pour l'affichage des courbes
        AudioScope scope = new AudioScope( synth );
        scope.addProbe(vcoa.getOutput().getUnitOutputPort());
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
        out.getInput().connect(vcoa.getOutput());
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

        int n = 200;
        while (n > 0) {
            n--;
            //assertEquals(0D, vca.getOutput().getUnitOutputPort().getValue(), 0.0);
            System.out.println(vcoa2.getOutput().getUnitOutputPort().getValue());

            //assertNotEquals(0D, vca.getOutput().getUnitOutputPort().getValue(), 0.0);

            synth.sleepFor(0.01);
        }

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

}
