package org.istic.synthlab.components.oscillators;

import com.jsyn.unitgen.UnitOscillator;
import junit.framework.TestCase;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.oscillators.SineOscillator;

/**
 * Sine Oscillator Tester.
 * We test all the function of Abstract Oscillator
 *
 * @author <Ngassam Noumi Paola> npaolita.[ât]yahoo.fr
 * @since <pre>Feb 9, 2016</pre>
 */
public class SineOscillatorTest extends TestCase{

    private SineOscillator sineOscillator;
    private IComponent component;
    private static final double DELTA = 1e-15;

    @org.junit.Before
    public void setUp() throws Exception {
        super.setUp();
        component = new Vcoa("VCOA");
        sineOscillator = new SineOscillator(component);
    }

    @org.junit.Test
    public void testSineOscillatorConstruct(){
        assertNotNull(sineOscillator.getAm());
        assertNotNull(sineOscillator.getFm());
        assertNotNull(sineOscillator.getOutput());
        assertNotNull(sineOscillator.getOscillator());
        assertNotNull(sineOscillator.getAmplitudePotentiometer());
        assertNotNull(sineOscillator.getFrequencyPotentiometer());
        assertEquals(20000.0,sineOscillator.getFrequencyPotentiometer().getMax(),DELTA);
        assertEquals(20.0,sineOscillator.getFrequencyPotentiometer().getMin(), DELTA);
        assertEquals(0.0,sineOscillator.getAmplitudePotentiometer().getMin(),DELTA);
        assertEquals(10000.0,sineOscillator.getAmplitudePotentiometer().getMax(),DELTA);
    }


    @org.junit.Test
    public void testActivate() {
        sineOscillator.activate();
        assertTrue(sineOscillator.getOscillator().isEnabled());
    }

    @org.junit.Test
    public void testDesactivate(){
        sineOscillator.activate();
        sineOscillator.deactivate();
        assertFalse(sineOscillator.isActivated());
    }

    @org.junit.Test
    public void testGetComponent(){
        IComponent vcoa= sineOscillator.getComponent();
        assertEquals(vcoa, component);
    }

    @org.junit.Test
    public void testGetOscillator(){
        UnitOscillator oscillator= sineOscillator.getOscillator();
        assertNotNull(oscillator);
    }

    @org.junit.Test
    public void testGetFm(){
        IInput fm = sineOscillator.getFm();
        assertNotNull(fm);
        assertTrue(fm.getName()=="Fm");
        assertEquals(fm.getComponent(), component);
    }

    @org.junit.Test
    public void testGetAm(){
        IInput am = sineOscillator.getAm();
        assertNotNull(am);
        assertTrue(am.getName()=="Am");
        assertEquals(am.getComponent(),component);
    }


    @org.junit.Test
    public void testGetOutput() {
        IOutput output= sineOscillator.getOutput();
        assertNotNull(output);
        assertTrue(output.getName() == "Out");
        assertEquals(output.getComponent(),component);
    }

    @org.junit.Test
    public void testSetFrequency() {
        sineOscillator.setFrequency(30.0);
        assertEquals(30.0, sineOscillator.getFrequencyPotentiometer().getValue(),DELTA);
    }

    @org.junit.Test
    public void testGetFrequency() {
        double frequency = sineOscillator.getFrequency();
        assertEquals(1000.0, frequency,DELTA);
    }

    @org.junit.Test
    public void testSetAmplitude() throws Exception {
        sineOscillator.setAmplitude(2.0);
        assertEquals(2.0, sineOscillator.getAmplitude(),DELTA);
    }

    @org.junit.Test
    public void testGetAmplitude() throws Exception {
        double amplitude = sineOscillator.getAmplitude();
        assertEquals(1.0, amplitude,DELTA);
    }

    @org.junit.After
    public void tearDown() throws Exception {
        super.tearDown();
    }
}