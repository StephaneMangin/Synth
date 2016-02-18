package org.istic.synthlab.prototype;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.EnvelopeDAHDSR;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillator;

/*import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;*/

/**
 * Created by cyprien on 08/02/16.
 */
public class EnveloppeGeneratorTest {

    private Synthesizer synth;
    private SquareOscillator squareOsc;
    private SineOscillator sineOsc;
    private LineOut out;

    public void setUp() throws Exception {

        // Create a synthesizer
        synth = JSyn.createSynthesizer();

        // Prepare a squareOscillator (it produce a binary signal !)
        // Needed for an envelope generator
        squareOsc = new SquareOscillator();
        squareOsc.amplitude.set(1.0);
        squareOsc.frequency.set(80.0);

        // Prepare a SineOscillator (its amplitude will be modulated by the envelope)
        sineOsc = new SineOscillator();
        sineOsc.amplitude.set(1.0);
        sineOsc.frequency.set(320.0);

        // LineOut
        out = new LineOut();

        synth.add(squareOsc);
        synth.add(out);
        synth.add(sineOsc);
    }

    public void tearDown() throws Exception {

    }

    public void envelopeLongTiming() throws InterruptedException {

        // Fully Optional envelope generator
        // When delay and hold are set to 0 it is the same as an ADSR(Attack, Decay, Sustain, Release) envelope.
        EnvelopeDAHDSR envGen = new EnvelopeDAHDSR();

        envGen.amplitude.set(1.0);

        // Time related parameters
        // Time to wait before starting the ryze of the envGenelope
        envGen.delay.set(0.0);
        // Time related to the increase of the amplitude in the first phase of the envGenelope
        envGen.attack.set(0.2);
        // Time to wait to hold the signal at its highest value
        envGen.hold.set(1.0);
        // Range-time to delay the decrease of the sound after the hold phase to the sustain phase
        envGen.decay.set(1.0);
        // Range-time to delay the decrease of the sound after the end of the signal
        envGen.release.set(2.0);

        // Amplitude related parameters
        envGen.sustain.set(0.4);

        // Always add a new module to the synthesizer
        synth.add(envGen);

        // Force input value of the envelopeGenerator to 1.0
        // By doing so, we are in a "high" state for the entering signal which will trigger the envelopeGenerator.
        envGen.input.set(1.0);

        // Connect the output produced by the envelopeGenerator to the amplitude inputs of the SineOscillator
        envGen.output.connect(sineOsc.amplitude);

        //env.input.setDefault(1.0);
        envGen.input.setValueInternal(1.0);
        envGen.output.connect(sineOsc.amplitude);

        // Connections of the SineOscillator to the lineOut to have sound.
        sineOsc.output.connect(0, out.input, 0);
        sineOsc.output.connect(0, out.input, 1);

        // Start the LineOut, the last one of the chain, to activate everything like a cascade.
        out.start();
        // Start the synthesizer
        synth.start();
        // Force the synthesizer to sleep for one second to give us some time to work.
        // In our case may not be necessary.
        synth.sleepFor(1.0);

        int n = 50;
        while (n > 0){

            // at the 20th LOOP
            // Around the middle of the execution corresponding to two full seconds (needed to execute
            // a full envelope from beginning to the sustain phase)
            if (n == 30) {
                // Force the signal to 0.0 ("low" state)
                // it will trigger the end of the envelope, the release phase.
                envGen.input.setValueInternal(0.0);
                squareOsc.amplitude.set(0.0);
            }

            //System.out.println("Square output : " + squareOsc.output.getValue());
            System.out.println("env input : " + envGen.input.getValue());
            System.out.println(envGen.output.getValue());
            synth.sleepFor(0.1);
            n--;
        }

    }

    public void envelopeOneShotTiming() throws InterruptedException {

        // Fully Optional envelope generator
        // When delay and hold are set to 0 it is the same as an ADSR(Attack, Decay, Sustain, Release) envelope.
        EnvelopeDAHDSR envGen = new EnvelopeDAHDSR();

        envGen.amplitude.set(1.0);

        // Time related parameters
        // Time to wait before starting the ryze of the envGenelope
        envGen.delay.set(0.0);
        // Time related to the increase of the amplitude in the first phase of the envGenelope
        envGen.attack.set(0.2);
        // Time to wait to hold the signal at its highest value
        envGen.hold.set(1.0);
        // Range-time to delay the decrease of the sound after the hold phase to the sustain phase
        envGen.decay.set(1.0);
        // Range-time to delay the decrease of the sound after the end of the signal
        envGen.release.set(2.0);

        // Amplitude related parameters
        envGen.sustain.set(0.4);

        // Note about envelopeDAHDSR, how it works
        // an envelopeGenerator produce a value between 0.0 and 1.0 which is typically an amplitude.
        // Signal can be either "high" ( > 0.0 ) or "low" ( = 0.0 )

        // When using a uniform "high" signal, the envelope will be played correctly up to the sustain
        // phase waiting for the signal to go in "low" signal...

        // If the synthesizer, or the lineOut, or the oscillator which is wired after the envelopeGenerator
        // are disabled/stopped/has their amplitude set to 0.0, the release phase will not be triggered

        // If the signal is not long enough to reach the sustain phase and ends before it, it will
        // (from what i've understood) trigger the release phase even though he hasn't reached it yet.

        // When using an oscillator as the input of the envelopeGenerator, generally speaking, the period
        // of a cycle of the oscillator will always be shorter than the envelope total duration.
        // The amplitude output of the envelopeGenerator will be stable but in fact it will be a succession
        // of tiny-envelope partly played.


        // Always add a new module to the synthesizer
        synth.add(envGen);

        // Force input value of the envelopeGenerator to 1.0
        // By doing so, we are in a "high" state for the entering signal which will trigger the envelopeGenerator.
        envGen.input.set(1.0);

        // Connect the output produced by the envelopeGenerator to the amplitude inputs of the SineOscillator
        envGen.output.connect(sineOsc.amplitude);

        // Connections of the SineOscillator to the lineOut to have sound.
        sineOsc.output.connect(0, out.input, 0);
        sineOsc.output.connect(0, out.input, 1);

        // Start the LineOut, the last one of the chain, to activate everything like a cascade.
        out.start();
        // Start the synthesizer
        synth.start();
        // Force the synthesizer to sleep for one second to give us some time to work.
        // In our case may not be necessary.
        synth.sleepFor(1.0);

        int n = 50;
        while (n > 0){

            // at the FIRST LOOP
            // (That is why this is called a oneShot Signal
            if (n == 50) {
                // Force the signal to 0.0 ("low" state)
                // it will trigger the end of the envelope, the release phase.
                envGen.input.setValueInternal(0.0);
            }

            System.out.println("env input\t: " + envGen.input.getValue());
            System.out.println("env output\t: " + envGen.output.getValue());

            // although the input is 0.0
            // during the release phase, the output is superior to 0, which is exactly what we want.
            synth.sleepFor(0.1);
            n--;
        }

    }

    public void squareOscillatorFrequencyTesting() throws InterruptedException {

        squareOsc.frequency.set(1.0);
        squareOsc.output.connect(sineOsc.amplitude);
        sineOsc.output.connect(0, out.input, 0);
        sineOsc.output.connect(0, out.input, 1);

        out.start();
        synth.start();
        synth.sleepFor(10.0);

    }

    public void squareOscillatorDisabledValues() throws InterruptedException {

        //This is a test to check about an oscillator behavior when he pass to a disabled state.

        squareOsc.frequency.set(80.0);
        squareOsc.amplitude.set(1.0);
        squareOsc.setEnabled(false);

        squareOsc.output.connect(sineOsc.amplitude);
        sineOsc.output.connect(0, out.input, 0);
        sineOsc.output.connect(0, out.input, 1);

        out.start();
        synth.start();
        synth.sleepFor(1.0);

        //assertEquals(squareOsc.output.getValue(), 0.0);

        // Here the squareOscillator output value should be 0.0
        //
        // the oscillator never had the chance to run, so even though we configured its parameters
        // it has never used them and so it's internal value will be 0.0

        System.out.println("squareOscillator output value should be 0.0");
        System.out.println("squareOscillator output is : " + squareOsc.output.getValue());

        squareOsc.setEnabled(true);
        squareOsc.amplitude.set(0.0);
        synth.sleepFor(1.0);

        // In this case, the square oscillator has run a bit (one second)
        // Still, it's amplitude was forced to 0.0, and the output is determined by the amplitude
        // Even though it will be stopped in either high or low state (-1 or 1), it will be 0.0 or -0.0

        //assertTrue(squareOsc.output.getValue() == (0.0) || squareOsc.output.getValue() == (-0.0));
        System.out.println("squareOscillator output value should be 0.0 or -0.0");
        System.out.println("squareOscillator output is : " + squareOsc.output.getValue());

        // Remark
        // Disable an oscillator does not force its value back to 0.0
        // The value won't fluctuate but will remain the same.

    }
}
