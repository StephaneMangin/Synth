package org.istic.synthlab.components.vca;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author John Marc Dechaud on 8/02/2016
 *
 *  Module VCA Tester.
 */
public class VcaTest {

    private Vca vca;

    @Before
    public void setUp() {
        this.vca = new Vca("VCA");
    }

    @Test
    public void testActivate() throws Exception {
        vca.activate();
        assertTrue(vca.isActivated());
    }

    @Test
    public void testDeactivate() throws Exception {
        vca.activate();
        vca.deactivate();
        assertFalse(vca.isActivated());
    }

    @Test
    public void testGetGainModulatorValue() throws Exception {
        this.vca.getGainModulator().getValue();
        assertEquals((long) 0.0, (long) (this.vca.getGainModulator().getValue()));
    }

    @Test
    public void testSetGainModulatorValue() throws Exception {
        this.vca.getGainModulator().setValue(0.005);
        assertEquals((long) 0.005, (long) (this.vca.getGainModulator().getValue()));
    }

}