package org.istic.synthlab.core;

import com.jsyn.Synthesizer;
import com.jsyn.engine.SynthesisEngine;
import com.jsyn.scope.AudioScope;
import org.istic.synthlab.components.out.Out;
import org.istic.synthlab.components.replicator.Replicator;
import org.istic.synthlab.components.vca.Vca;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.modules.oscillators.ImpulseOscillator;
import org.istic.synthlab.core.modules.oscillators.OscillatorType;
import org.istic.synthlab.core.modules.oscillators.SawtoothOscillator;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.junit.After;
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
        SawtoothOscillator s = (SawtoothOscillator) Factory.createOscillator(vcoa, OscillatorType.SAWTOOTH);
        vcoa.getSawToothOutput().connect(out.getInput());

        // Pour l'affichage des courbes
        AudioScope scope = new AudioScope( synth );
        scope.addProbe(vcoa.getOutput().getUnitOutputPort());
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

}
