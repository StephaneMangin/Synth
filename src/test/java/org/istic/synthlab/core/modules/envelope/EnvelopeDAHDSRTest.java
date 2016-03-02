package org.istic.synthlab.core.modules.envelope;

import com.jsyn.Synthesizer;
import junit.framework.TestCase;
import org.istic.synthlab.components.eg.Eg;
import org.istic.synthlab.components.out.Out;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.components.IComponent;
import org.istic.synthlab.core.modules.lineOuts.ILineOut;
import org.istic.synthlab.core.modules.lineOuts.LineType;
import org.istic.synthlab.core.modules.oscillators.IOscillator;
import org.istic.synthlab.core.modules.oscillators.OscillatorType;
import org.istic.synthlab.core.services.Factory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author gottstein[dot]cyprien[at]gmail[dot]com on 09/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class EnvelopeDAHDSRTest extends TestCase {

    private IEnvelopeDAHDSR envelope;
    private IComponent component;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        component = Mockito.mock(Eg.class);
        envelope = Factory.createEnvelopeDAHDSR(component);

        envelope.getAmplitudePotentiometer().setValue(1.0);
        envelope.getDelayPotentiometer().setValue(0.0);
        envelope.getAttackPotentiometer().setValue(0.2);
        envelope.getHoldPotentiometer().setValue(1.0);
        envelope.getDecayPotentiometer().setValue(1.0);
        envelope.getReleasePotentiometer().setValue(2.0);
        envelope.getSustainPotentiometer().setValue(0.4);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void internalWire() throws Exception {

        IComponent componentSin = Mockito.mock(Vcoa.class);
        IComponent componentOut = Mockito.mock(Out.class);

        IOscillator sin = Factory.createOscillator(componentSin, OscillatorType.SINE);

        sin.getFrequencyPotentiometer().setValue(320.0);
        sin.getAmplitudePotentiometer().setValue(1.0);
        sin.activate();

        ILineOut lineOut = Factory.createLineOut(componentOut, LineType.OUT);

        envelope.getInput().getUnitInputPort().set(1.0);
        envelope.getOutput().connect(sin.getAm());
        sin.getOutput().connect(lineOut.getInput());

        Synthesizer synth = Factory.createSynthesizer();

        lineOut.start();

        synth.start();
        synth.sleepFor(0.1);

        int n = 20;

        while ( n > 0 ){
            n--;
            assertEquals(envelope.getOutput().getUnitOutputPort().getValue(), sin.getAm().getUnitInputPort().getValue(),1e-2);
            synth.sleepFor(0.1);
        }

    }

    public void testGetInput() throws Exception {

    }

    public void testGetOutput() throws Exception {

    }

    public void testSetAmplitude() throws Exception {

    }

    public void testGetAmplitude() throws Exception {

    }

    public void testGetAmplitudeMax() throws Exception {

    }

    public void testGetAmplitudeMin() throws Exception {

    }

    public void testSetDelay() throws Exception {

    }

    public void testGetDelay() throws Exception {

    }

    public void testGetDelayMax() throws Exception {

    }

    public void testGetDelayMin() throws Exception {

    }

    public void testSetAttack() throws Exception {

    }

    public void testGetAttack() throws Exception {

    }

    public void testGetAttackMax() throws Exception {

    }

    public void testGetAttackMin() throws Exception {

    }

    public void testSetHold() throws Exception {

    }

    public void testGetHold() throws Exception {

    }

    public void testGetHoldMax() throws Exception {

    }

    public void testGetHoldMin() throws Exception {

    }

    public void testSetDecay() throws Exception {

    }

    public void testGetDecay() throws Exception {

    }

    public void testGetDecayMax() throws Exception {

    }

    public void testGetDecayMin() throws Exception {

    }

    public void testSetSustain() throws Exception {

    }

    public void testGetSustain() throws Exception {

    }

    public void testGetSustainMax() throws Exception {

    }

    public void testGetSustainMin() throws Exception {

    }

    public void testSetRelease() throws Exception {

    }

    public void testGetRelease() throws Exception {

    }

    public void testGetReleaseMax() throws Exception {

    }

    public void testGetReleaseMin() throws Exception {

    }

    public void testActivate() throws Exception {

    }

    public void testDeactivate() throws Exception {

    }

    public void testIsActivated() throws Exception {

    }
}