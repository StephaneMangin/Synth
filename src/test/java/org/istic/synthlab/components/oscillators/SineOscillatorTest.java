package org.istic.synthlab.components.oscillators;

import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.oscillators.SineOscillator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Sine Oscillator Tester.
 * We test all the function of Abstract Oscillator
 *
 * @author <Ngassam Noumi Paola> npaolita.[ât]yahoo.fr
 * @since <pre>Feb 9, 2016</pre>
 */
public class SineOscillatorTest {

    private SineOscillator sineOscillator;
    private IComponent component;
    private static final double DELTA = 1e-15;

    @Before
    public void setUp() throws Exception {
        component = new Vcoa("VCOA");
        sineOscillator = new SineOscillator(component);
    }

    @Test
    public void testSineOscillatorConstruct(){
        assertNotNull(sineOscillator.getAm());
        assertNotNull(sineOscillator.getFm());
        assertNotNull(sineOscillator.getOutput());
        assertNotNull(sineOscillator.getAmplitudePotentiometer());
        assertNotNull(sineOscillator.getFrequencyPotentiometer());
        assertEquals(30000.0,sineOscillator.getFrequencyPotentiometer().getMax(),DELTA);
        assertEquals(0.0,sineOscillator.getFrequencyPotentiometer().getMin(), DELTA);
        assertEquals(0.0,sineOscillator.getAmplitudePotentiometer().getMin(),DELTA);
        assertEquals(1.0,sineOscillator.getAmplitudePotentiometer().getMax(),DELTA);
    }


    @Test
    public void testActivate() {
        sineOscillator.activate();
        assertTrue(sineOscillator.isActivated());
    }

    @Test
    public void testDesactivate(){
        sineOscillator.activate();
        sineOscillator.deactivate();
        assertFalse(sineOscillator.isActivated());
    }

    @Test
    public void testGetComponent(){
        IComponent vcoa= sineOscillator.getComponent();
        assertEquals(vcoa, component);
    }

    @Test
    public void testGetFm(){
        IInput fm = sineOscillator.getFm();
        assertNotNull(fm);
        assertEquals(fm.getName(), "Fm");
        assertEquals(fm.getComponent(), component);
    }

    @Test
    public void testGetAm(){
        IInput am = sineOscillator.getAm();
        assertNotNull(am);
        assertEquals(am.getName(), "Am");
        assertEquals(am.getComponent(),component);
    }

    @Test
    public void testGetOutput() {
        IOutput output= sineOscillator.getOutput();
        assertNotNull(output);
        assertEquals(output.getName(), "Out");
        assertEquals(output.getComponent(),component);
    }

    @Test
    public void testSetFrequency() {
        sineOscillator.getFrequencyPotentiometer().setValue(30.0);
        assertEquals(40.0, sineOscillator.getFrequencyPotentiometer().getValue(),DELTA);
    }

    @Test
    public void testGetFrequency() {
        double frequency = sineOscillator.getFrequencyPotentiometer().getValue();
        assertEquals(40, frequency,DELTA);
    }

    @Test
    public void testSetAmplitude() throws Exception {
        sineOscillator.getAmplitudePotentiometer().setValue(1.0);
        assertEquals(1.0, sineOscillator.getAmplitudePotentiometer().getValue(), DELTA);
    }

    @Test
    public void testGetAmplitude() throws Exception {
        double amplitude = sineOscillator.getAmplitudePotentiometer().getValue();
        assertEquals(1.0, amplitude,DELTA);
    }

    @After
    public void tearDown() throws Exception {

    }
}