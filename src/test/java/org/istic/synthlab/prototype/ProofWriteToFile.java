package org.istic.synthlab.prototype;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.util.WaveRecorder;
import org.istic.synthlab.components.out.Out;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.services.Factory;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by cyprien on 22/02/16.
 */
public class ProofWriteToFile {

    private LineOut lineOut;
    private SineOscillator sine;
    private Synthesizer synth;
    private WaveRecorder wr;

    public void writeToFile() throws FileNotFoundException, InterruptedException {

        synth = JSyn.createSynthesizer();
        lineOut = new LineOut();
        sine = new SineOscillator();

        File file = new File("/tmp/bachibouzouk.wav");

        wr = new WaveRecorder(synth, file);

        synth.add(lineOut);
        synth.add(sine);

        sine.frequency.set(320.0);
        sine.amplitude.set(1.0);

        sine.output.connect(wr.getInput());
        sine.output.connect(lineOut.input);

        lineOut.start();
        wr.start();
        synth.start();

        synth.sleepFor(5.0);

    }

    public void writeToFileWithComponent() throws FileNotFoundException, InterruptedException {

        synth = Factory.createSynthesizer();
        Vcoa vcoa = new Vcoa("VCOA");
        Out out = new Out("OUT");

        File file = new File("/tmp/bachibouzouk2.wav");
        wr = new WaveRecorder(synth, file);

        vcoa.setExponentialFrequency(0.8);
        vcoa.setLinearFrequency(0.5);
        vcoa.setAmplitudeSine(1.0);

        vcoa.getOutput().connect(out.getInput());
        vcoa.getOutput().getUnitOutputPort().connect(wr.getInput());

        out.start();

        // note : the following sequence of start/stop make the total activate duration of the waveRecorder
        // corresponding to 3.5 seconds. Been checked in an other software (Totem).

        wr.start();
        synth.start();

        synth.sleepFor(2.0);

        wr.stop();
        synth.sleepFor(1.0);
        wr.start();
        synth.sleepFor(1.5);

        System.out.println("STAPH!");
        wr.stop();

        // note : a file already existing will be overwritten by the new File, must be verified.

    }

    public void writeToMultipleFileWithComponent() throws FileNotFoundException, InterruptedException {

        synth = Factory.createSynthesizer();
        Vcoa vcoa = new Vcoa("VCOA");
        Out out = new Out("OUT");

        File file = new File("/tmp/bachibouzouk2.wav");
        wr = new WaveRecorder(synth, file);

        vcoa.setExponentialFrequency(0.8);
        vcoa.setLinearFrequency(0.5);
        vcoa.setAmplitudeSine(1.0);

        vcoa.getOutput().connect(out.getInput());
        vcoa.getOutput().getUnitOutputPort().connect(wr.getInput());

        out.start();
        wr.start();
        synth.start();

        synth.sleepFor(2.0);

        wr.stop();
        synth.sleepFor(1.0);

        file = new File("/tmp/bachibouzouk3.wav");
        wr = new WaveRecorder(synth, file);
        vcoa.getOutput().getUnitOutputPort().connect(wr.getInput());
        wr.start();

        synth.sleepFor(2.0);

        System.out.println("STAPH!");
        wr.stop();

        // note : a file already existing will be overwritten by the new File, must be verified.

    }
}
