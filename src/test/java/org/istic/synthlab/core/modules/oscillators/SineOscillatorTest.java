package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.Synthesizer;
import org.istic.synthlab.components.out.Out;
import org.istic.synthlab.components.replicator.Replicator;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.lineOuts.ILineOut;
import org.istic.synthlab.core.modules.lineOuts.LineType;
import org.istic.synthlab.core.modules.passThrough.PassThrough;
import org.istic.synthlab.core.services.Factory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.exceptions.ExceptionIncludingMockitoWarnings;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

/**
 * set frequency
 * setamplitude
 * activate
 * connect lineout
 * lineout.setVolume(1.0)
 * for 20 sleepFor(0.1)
 */
@RunWith(MockitoJUnitRunner.class)
public class SineOscillatorTest {
    private IOscillator sineOscillator;
    private IComponent component;

    @Before
    public void setUp(){
        component = Mockito.mock(Replicator.class);
        sineOscillator = Factory.createOscillator(component, OscillatorType.SINE);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAmplitude() throws Exception {
        IComponent componentOut = Mockito.mock(Out.class);

        sineOscillator.setFrequency(320.0);
        sineOscillator.setAmplitude(0.5);
        sineOscillator.activate();

        ILineOut lineOut = Factory.createLineOut(componentOut, LineType.OUT);

        lineOut.getInput().connect(sineOscillator.getOutput());

        Synthesizer synth = Factory.createSynthesizer();

        lineOut.start();

        synth.start();
        synth.sleepFor(0.5);

        int n = 10;
        while (n > 0){
            n--;
            assertNotSame(0.0, sineOscillator.getOutput().getUnitOutputPort().getValue());

            synth.sleepFor(0.5);
        }

        sineOscillator.setAmplitude(0);

        while (n > 0) {
            n--;
            assertSame(0.0, sineOscillator.getOutput().getUnitOutputPort().getValue());

            synth.sleepFor(0.5);
        }

    }

    @Test
    public void testFrequency() throws Exception {
        IComponent componentOut = Mockito.mock(Out.class);

        sineOscillator.setFrequency(320.0);
        sineOscillator.setAmplitude(0.5);
        sineOscillator.activate();

        ILineOut lineOut = Factory.createLineOut(componentOut, LineType.OUT);

        lineOut.getInput().connect(sineOscillator.getOutput());

        Synthesizer synth = Factory.createSynthesizer();

        lineOut.start();

        synth.start();
        synth.sleepFor(0.5);

        int n = 10;
        while (n > 0) {
            n--;
            assertNotSame(0.0, sineOscillator.getOutput().getUnitOutputPort().getValue());

            synth.sleepFor(0.5);
        }

        sineOscillator.setFrequency(0);

        while (n > 0) {
            n--;
            assertSame(0.0, sineOscillator.getOutput().getUnitOutputPort().getValue());

            synth.sleepFor(0.5);
        }
    }
}
