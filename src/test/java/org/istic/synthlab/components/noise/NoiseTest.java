package org.istic.synthlab.components.noise;

import org.istic.synthlab.core.services.Register;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Module Noise Tester.
 * @author npaolita[at]yahoo[dot]fr
 *
 */
public class NoiseTest {

    private Noise noise;

    @Before
    public void setUp() throws Exception {
        noise = new Noise("NOISE");
    }

    @Test
    public void testNoiseModule(){
        assertEquals("NOISE", noise.getName());
    }


    @Test
    public void testActivate() throws Exception {
        noise.activate();
        assertTrue(noise.isActivated());
    }

    @Test
    public void testDeactivate() throws Exception {
        noise.activate();
        noise.deactivate();
        assertFalse(noise.isActivated());
    }

    @Test
    public void testGetOutput() throws Exception {
        assertNotNull(noise.getOutput());
        assertEquals(noise, Register.getComponent(noise.getOutput()));
    }

}