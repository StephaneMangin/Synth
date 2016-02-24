package org.istic.synthlab.prototype;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.engine.SynthesisEngine;
import com.jsyn.unitgen.*;
import org.istic.synthlab.components.out.Out;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Dechaud John Marc and Ngassam Paola on 2/13/16.
 */
public class MixerGenerate {

    SineOscillator sineOscillator;
    SquareOscillator squareOscillator;
    TriangleOscillator triangleOscillator;
    SawtoothOscillator sawtoothOscillator;
    LineOut lineOut;
    Add add;
    MixerStereo mixerStereo;
    Synthesizer synth;
    private Vcoa vcoa;
    private Out out;
    private Vcoa vcoa1;

    @Before
    public void setUp() throws Exception {
        sineOscillator = new SineOscillator();
        squareOscillator = new SquareOscillator();
        sawtoothOscillator = new SawtoothOscillator();
        triangleOscillator = new TriangleOscillator();

        lineOut = new LineOut();
        add = new Add();
        mixerStereo = new MixerStereo(2);
        synth = JSyn.createSynthesizer();
        vcoa = new Vcoa("VCOA");
        vcoa1 = new Vcoa("v");
        vcoa.activate();
        out = new Out("OUT");
        out.activate();
    }

    @Test
    public void testMix() throws InterruptedException {
        sineOscillator.amplitude.set(3.0);
        sineOscillator.frequency.set(320.0);
        squareOscillator.amplitude.set(3.0);
        squareOscillator.frequency.set(80.0);
        triangleOscillator.amplitude.set(3.0);
        triangleOscillator.frequency.set(80.0);
        sawtoothOscillator.amplitude.set(3.0);
        sawtoothOscillator.frequency.set(80.0);
        //mixerStereo.addPort(sineOscillator.output);
        //mixerStereo.addPort(squareOscillator.output);
        //lineOut.input.connect(mixerStereo.output);
        synth.add(sineOscillator);
        synth.add(squareOscillator);
        synth.add(sawtoothOscillator);
        synth.add(triangleOscillator);
        synth.add(lineOut);

        add.inputA.connect(sineOscillator.output);
        add.inputB.connect(squareOscillator.output);
        add.inputA.connect(sawtoothOscillator.output);

        System.out.println(add.getPortByName("triangle") +", "+add.getPortByName("sine")+", "+add.getPortByName("square"));
        synth.add(add);
        add.start();
        //mixerStereo.activate();

        //vcoa.getOutput().connect(vcoa1.getInput());

        //out.activate();
        lineOut.getInput().connect(add.output);
        synth.start();
        lineOut.start();
        synth.sleepFor(5.0);
        ((SynthesisEngine)synth).printConnections();
    }

}
