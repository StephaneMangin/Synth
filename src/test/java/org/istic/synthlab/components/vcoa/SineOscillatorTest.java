package org.istic.synthlab.components.vcoa;

import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.oscillators.SineOscillator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author stephane on 02/02/16.
 */
public class SineOscillatorTest {

    private SineOscillator scopeAdapter;

    @Before
    public void setUp() throws Exception {
        IComponent component = new Vcoa("TEST");
        scopeAdapter = new SineOscillator(component);
    }

    /**
     * Test method Get frequency modulation.
     */
    @Test
    public void testGetFm() throws Exception {
        assertNotNull(scopeAdapter.getFm());
    }
    /**
     * Test method Get amplitude modulation.
     */
    @Test
    public void testGetAm() throws Exception {
        assertNotNull(scopeAdapter.getAm());
    }

    /**
     * Test method Get frequency.
     */
    @Test
    public void testGetFrequency() throws Exception {
        //assertEquals(0.0, scopeAdapter.getFrequency());
    }
    /**
     * Test method Get amplitude
     */
    @Test
    public void testGetAmplitude() throws Exception {
        //assertEquals(0.0, scopeAdapter.getAmplitude());
    }

    /**
     * Test method Get phase
     */
    @Test
    public void testGetPhase() throws Exception {
        //assertEquals(0.0, scopeAdapter.getPhase());
    }

    /**
     * Test method Set amplitude.
     */
    @Test
    public void testSetAmplitude() throws Exception {
        //SineOscillator scope =  scopeAdapter.getOscillator();
        //scopeAdapter.setFrequency(0.2);
        //assertEquals(0.2, scopeAdapter.getAmplitude());
        //assertEquals(0.2,scope.amplitude.getValue());
    }

    /**
     * Test method Set phase.
     */
    @Test
    public void testSetPhase() throws Exception {
        //SineOscillator scope =  scopeAdapter.getOscillator();
        //scopeAdapter.setPhase(0.5);
        //assertEquals(0.5, scopeAdapter.getPhase());
        //assertEquals(0.5,scope.phase.getValue());
    }

    @After
    public void tearDown() throws Exception {

    }
}