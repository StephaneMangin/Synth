package org.istic.synthlab.components.vcoa;

import junit.framework.TestCase;
import org.istic.synthlab.core.Channel;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.oscillators.*;
import org.istic.synthlab.core.modules.oscillators.IOscillator;

/**
 * Module VCOA Tester.
 *
 * @author <Ngassam Noumi Paola> npaolita.[ât]yahoo.fr
 * @since <pre>Feb 8, 2016</pre>
 */
public class VcoaTest extends TestCase{

    private Vcoa vcoa;

    @org.junit.Before
    public void setUp() throws Exception {
        vcoa = new Vcoa("VCOA");
    }

    @org.junit.Test
    public void testVCOAModule(){
        assertNotNull(vcoa.getSineOscillator());
        assertNotNull(vcoa.getPulseOscillator());
        assertNotNull(vcoa.getSquareOscillator());
        assertNotNull(vcoa.getImpulseOutput());
        assertNotNull(vcoa.getSawToothOutput());
        assertNotNull(vcoa.getTriangleOutput());
        assertNotNull(vcoa.getRedNoiseOutput());
        assertFalse(Channel.isEmpty());
    }


    @org.junit.Test
    public void testActivate() throws Exception {
        vcoa.activate();
        IOscillator sineOscillator= vcoa.getSineOscillator();
        IOscillator pulseOscillator= vcoa.getPulseOscillator();
        IOscillator squareOscillator= vcoa.getSquareOscillator();
        assertTrue(sineOscillator.isActivated());
        assertTrue(pulseOscillator.isActivated());
        assertTrue(squareOscillator.isActivated());
    }

    @org.junit.Test
    public void testDesactivate() throws Exception {
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
    public void testSetExponentialFrequency() throws Exception {
        vcoa.setExponentialFrequency(30.0D);
        assertEquals(30.0D, vcoa.getExponentialFrequency(), 1e-15);
    }

    @org.junit.Test
    public void testSetAmplitudeSine() throws Exception {
        vcoa.setAmplitudeSine(1);
        AbstractOscillator sineOscillator= (AbstractOscillator) vcoa.getSineOscillator();
        Double value = sineOscillator.getFrequencyPotentiometer().getValue();
        assertEquals(value, sineOscillator.getFrequencyPotentiometer().getValue());
    }

    @org.junit.Test
    public void testSetAmplitudePulse() throws Exception {
       vcoa.setAmplitudePulse(0.8);
        AbstractOscillator pulseOscillatorOscillator= (AbstractOscillator) vcoa.getPulseOscillator();
        Double value = pulseOscillatorOscillator.getFrequencyPotentiometer().getValue();
        assertEquals(value, pulseOscillatorOscillator.getFrequencyPotentiometer().getValue());
    }

    @org.junit.Test
    public void testSetAmplitudeSquare() throws Exception {
        vcoa.setAmplitudePulse(0.5);
        AbstractOscillator squareOscillatorOscillator= (AbstractOscillator) vcoa.getSquareOscillator();
        Double value = squareOscillatorOscillator.getFrequencyPotentiometer().getValue();
        assertEquals(value, squareOscillatorOscillator.getFrequencyPotentiometer().getValue());
    }


    //    Testing method of Abstract Component

   @org.junit.Test
   public void testAbstractComponentConstruct(){
       assertTrue(vcoa.getName() == "VCOA");
       assertNotNull(vcoa.getFmModulator());
       assertTrue(vcoa.getFmModulator().getName() == "modFreq");
       assertNotNull(vcoa.getAmModulator());
       assertTrue(vcoa.getAmModulator().getName() == "modAmp");
       assertNotNull(vcoa.getInputGate());
       assertNotNull(vcoa.getOutputModulator());
       assertTrue(vcoa.getOutputModulator().getName() == "modOut");
   }

    @org.junit.Test
    public void testGetFm() throws Exception {
        IInput input = vcoa.getFm();
        assertEquals(vcoa.getFmModulator().getInput(), input);
    }
    @org.junit.Test
    public void testGetAm() throws Exception {
        IInput input = vcoa.getAm();
        assertEquals(vcoa.getAmModulator().getInput(), input);
    }
    @org.junit.Test
    public void testGetOutput() throws Exception {
        IOutput output =  vcoa.getOutput();
        assertEquals(vcoa.getOutputModulator().getOutput(), output);
    }
    @org.junit.After
    public void tearDown() throws Exception {
        super.tearDown();
    }
}