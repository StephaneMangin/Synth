package org.istic.synthlab.components.vcoa;

import junit.framework.TestCase;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.oscillators.SineOscillatorAdapter;

import static org.mockito.Mockito.mock;

/**
 * @author stephane on 02/02/16.
 */
public class SineOscillatorAdapterTest  extends TestCase{

    private SineOscillatorAdapter scopeAdapter;
    private IComponent component;

    @org.junit.Before
    public void setUp() throws Exception {
        super.setUp();
        component = new Vcoa("TEST");
        scopeAdapter = new SineOscillatorAdapter(component);
    }

    /**
     * Test method Get Input.
     */
    @org.junit.Test
    public void testGetInput() throws Exception {
        assertNull(scopeAdapter.getInput());
    }

    /**
     * Test method Get frequency.
     */
    @org.junit.Test
    public void testGetFrequency() throws Exception {
        //assertEquals(0.0, scopeAdapter.getFrequency());
    }
    /**
     * Test method Get amplitude
     */
    @org.junit.Test
    public void testGetAmplitude() throws Exception {
        //assertEquals(0.0, scopeAdapter.getAmplitude());
    }

    /**
     * Test method Get phase
     */
    @org.junit.Test
    public void testGetPhase() throws Exception {
        //assertEquals(0.0, scopeAdapter.getPhase());
    }

    /**
     * Test method Set amplitude.
     */
    @org.junit.Test
    public void testSetAmplitude() throws Exception {
        //SineOscillator scope =  scopeAdapter.getOscillator();
        //scopeAdapter.setAmplitude(0.2);
        //assertEquals(0.2, scopeAdapter.getAmplitude());
        //assertEquals(0.2,scope.amplitude.getValue());
    }

    /**
     * Test method Set phase.
     */
    @org.junit.Test
    public void testSetPhase() throws Exception {
        //SineOscillator scope =  scopeAdapter.getOscillator();
        //scopeAdapter.setPhase(0.5);
        //assertEquals(0.5, scopeAdapter.getPhase());
        //assertEquals(0.5,scope.phase.getValue());
    }

    @org.junit.After
    public void tearDown() throws Exception {
        super.tearDown();
    }
}