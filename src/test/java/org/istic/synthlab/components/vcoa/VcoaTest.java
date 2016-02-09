package org.istic.synthlab.components.vcoa;

import org.istic.synthlab.core.Channel;
import org.istic.synthlab.core.modules.oscillators.AbstractOscillator;
import org.istic.synthlab.core.modules.oscillators.IOscillator;
import org.junit.After;
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

    public static final double EPSILON = 1e-15;
    private Vcoa vcoa;

    @Before
    public void setUp() throws Exception {
        vcoa = new Vcoa("VCOA");
    }

    @Test
    public void testVCOAModule(){
        assertNotNull(vcoa.getSineOscillator());
        assertNotNull(vcoa.getPulseOscillator());
        assertNotNull(vcoa.getSquareOscillator());
        assertFalse(Channel.isEmpty());
    }


    @Test
    public void testActivate() throws Exception {
        vcoa.activate();
        IOscillator sineOscillator= vcoa.getSineOscillator();
        IOscillator pulseOscillator= vcoa.getPulseOscillator();
        IOscillator squareOscillator= vcoa.getSquareOscillator();
        assertTrue(sineOscillator.isActivated());
        assertTrue(pulseOscillator.isActivated());
        assertTrue(squareOscillator.isActivated());
    }

    @Test
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

    @Test
    public void testSetAmplitudeSine() throws Exception {
        vcoa.setAmplitudeSine(1);
        AbstractOscillator sineOscillator= (AbstractOscillator) vcoa.getSineOscillator();
        double value = sineOscillator.getFrequencyPotentiometer().getValue();
        assertEquals(value, sineOscillator.getFrequency(), EPSILON);
    }

    @Test
    public void testSetAmplitudePulse() throws Exception {
       vcoa.setAmplitudePulse(0.8);
        AbstractOscillator pulseOscillatorOscillator= (AbstractOscillator) vcoa.getPulseOscillator();
        double value = pulseOscillatorOscillator.getFrequencyPotentiometer().getValue();
        assertEquals(value, pulseOscillatorOscillator.getFrequency(), EPSILON);
    }

    @Test
    public void testSetAmplitudeSquare() throws Exception {
        vcoa.setAmplitudePulse(0.5);
        AbstractOscillator squareOscillatorOscillator= (AbstractOscillator) vcoa.getSquareOscillator();
        double value = squareOscillatorOscillator.getFrequencyPotentiometer().getValue();
        assertEquals(value, squareOscillatorOscillator.getFrequency(), EPSILON);
    }

    @After
    public void tearDown() throws Exception {

    }
}