package org.istic.synthlab.components.vcoa;

import org.istic.synthlab.core.Channel;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.oscillators.AbstractOscillator;
import org.istic.synthlab.core.modules.oscillators.IOscillator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Module VCOA Tester.
 *
 * @author <Ngassam Noumi Paola> npaolita.[ât]yahoo.fr
 * @since <pre>Feb 8, 2016</pre>
 */
public class VcoaTest {

    private Vcoa vcoa;

    @Before
    public void setUp() {
        vcoa = new Vcoa("VCOA");
    }

    @Test
    public void testVCOAModule() {
        assertNotNull(vcoa.getSineOscillator());
        assertNotNull(vcoa.getPulseOscillator());
        assertNotNull(vcoa.getSquareOscillator());
        assertNotNull(vcoa.getImpulseOutput());
        assertNotNull(vcoa.getSawToothOutput());
        assertNotNull(vcoa.getTriangleOutput());
        assertNotNull(vcoa.getRedNoiseOutput());
        assertFalse(Channel.isEmpty());
    }


    @Test
    public void testActivate() {
        vcoa.activate();
        IOscillator sineOscillator= vcoa.getSineOscillator();
        IOscillator pulseOscillator= vcoa.getPulseOscillator();
        IOscillator squareOscillator= vcoa.getSquareOscillator();
        assertTrue(sineOscillator.isActivated());
        assertTrue(pulseOscillator.isActivated());
        assertTrue(squareOscillator.isActivated());
    }

    @Test
    public void testDeactivate() {
        vcoa.activate();
        vcoa.deactivate();
        IOscillator sineOscillator= vcoa.getSineOscillator();
        IOscillator pulseOscillator= vcoa.getPulseOscillator();
        IOscillator squareOscillator= vcoa.getSquareOscillator();
        assertFalse(sineOscillator.isActivated());
        assertFalse(pulseOscillator.isActivated());
        assertFalse(squareOscillator.isActivated());
    }

    @org.junit.Test
    @Deprecated
    /** Value must be between 0 and 1*/
    public void testSetExponentialFrequency() throws Exception {
        vcoa.setExponentialFrequency(30.0D);
        assertEquals(30.0D, vcoa.getExponentialFrequency(), 1e-15);
    }

    @Test
    public void testSetAmplitudeSine() {
        vcoa.setAmplitudeSine(1);
        AbstractOscillator sineOscillator= (AbstractOscillator) vcoa.getSineOscillator();
        Double value = sineOscillator.getFrequencyPotentiometer().getValue();
        assertEquals(value, sineOscillator.getFrequencyPotentiometer().getValue());
    }

    @Test
    public void testSetAmplitudePulse() {
       vcoa.setAmplitudePulse(0.8);
        AbstractOscillator pulseOscillatorOscillator= (AbstractOscillator) vcoa.getPulseOscillator();
        Double value = pulseOscillatorOscillator.getFrequencyPotentiometer().getValue();
        assertEquals(value, pulseOscillatorOscillator.getFrequencyPotentiometer().getValue());
    }

    @Test
    public void testSetAmplitudeSquare() {
        vcoa.setAmplitudePulse(0.5);
        AbstractOscillator squareOscillatorOscillator= (AbstractOscillator) vcoa.getSquareOscillator();
        Double value = squareOscillatorOscillator.getFrequencyPotentiometer().getValue();
        assertEquals(value, squareOscillatorOscillator.getFrequencyPotentiometer().getValue());
    }


    //    Testing method of Abstract Component

   @Test
   public void testAbstractComponentConstruct() {
       assertEquals("VCOA", vcoa.getName());
       assertNotNull(vcoa.getFmModulator());
       assertEquals("modFreq", vcoa.getFmModulator().getName());
       assertNotNull(vcoa.getAmModulator());
       assertEquals("modAmp", vcoa.getAmModulator().getName());
       assertNotNull(vcoa.getInputGate());
       assertNotNull(vcoa.getOutputModulator());
       assertEquals("modOut", vcoa.getOutputModulator().getName());
   }

    @Test
    public void testGetFm() {
        IInput input = vcoa.getFm();
        assertEquals(vcoa.getFmModulator().getInput(), input);
    }
    @Test
    public void testGetAm() {
        IInput input = vcoa.getAm();
        assertEquals(vcoa.getAmModulator().getInput(), input);
    }
    @Test
    public void testGetOutput() {
        IOutput output =  vcoa.getOutput();
        assertEquals(vcoa.getOutputModulator().getOutput(), output);
    }
}