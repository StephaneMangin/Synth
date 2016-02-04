package org.istic.synthlab.components.vcoa;

import com.jsyn.unitgen.SineOscillator;
import junit.framework.TestCase;
import org.istic.synthlab.core.modules.oscillators.SineOscillatorAdapter;

/**
 * Created by stephane on 02/02/16.
 */
public class SineOscillatorAdapterTest  extends TestCase{

    private SineOscillatorAdapter scopeAdapter;

    @org.junit.Before
    public void setUp() throws Exception {
        super.setUp();
        scopeAdapter = new SineOscillatorAdapter();
    }

    /**
     * Test method Get Input.
     */
    @org.junit.Test
    public void testGetInput() throws Exception {
        assertNull(scopeAdapter.getInput());
    }

    /**
     * Test method Set frequency.
     */
    @org.junit.Test
    public void testSetFrequency() throws Exception {
        SineOscillator scope =  scopeAdapter.getOscillator();
        scopeAdapter.setFrequency(140.0);
        assertEquals(140.0,scope.frequency.getValue());
    }
    /**
     * Test method Set amplitude.
     */
    @org.junit.Test
    public void testSetAmplitude() throws Exception {
        SineOscillator scope =  scopeAdapter.getOscillator();
        scopeAdapter.setAmplitude(0.2);
        assertEquals(0.2,scope.amplitude.getValue());
    }

    /**
     * Test method Set phase.
     */
    @org.junit.Test
    public void testSetPhase() throws Exception {
        SineOscillator scope =  scopeAdapter.getOscillator();
        scopeAdapter.setPhase(0.5);
        assertEquals(0.5,scope.phase.getValue());
    }

    @org.junit.After
    public void tearDown() throws Exception {
        super.tearDown();
    }
}