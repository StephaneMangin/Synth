package org.istic.synthlab.components.vca;

import junit.framework.TestCase;
import org.junit.Assert;

/**
 * @author John Marc Dechaud on 8/02/2016
 *
 *  Module VCA Tester.
 */
public class VcaTest extends TestCase{

    private Vca vca;

    @org.junit.Before
    public void setUp() throws Exception {
        this.vca = new Vca("VCA");
    }

    @org.junit.Test
    public void testActivate() throws Exception {
        vca.activate();
        assertTrue(vca.isActivated());
    }

    @org.junit.Test
    public void testDeactivate() throws Exception {
        vca.deactivate();
        assertFalse(vca.isActivated());
    }

    @org.junit.Test
    public void testGetAmplitudeModulatorValue() throws Exception {
        Double v = 1.0;
        this.vca.setAmplitudeModulatorValue(v);

        Assert.assertTrue(this.vca.getAmplitudeModulatorValue() > 1.999 && this.vca.getAmplitudeModulatorValue() < 2.001);
    }

    @org.junit.Test
    public void testGetGainModulatorValue() throws Exception {
        this.vca.getGainModulatorValue();
        assertEquals((long) 0.0, (long) (this.vca.getGainModulatorValue()));
    }

    @org.junit.Test
    public void testSetGainModulatorValue() throws Exception {
        this.vca.setGainModulatorValue(0.001);
        Assert.assertEquals((long) 0.001, (long) (this.vca.getGainModulatorValue()));
    }

    @org.junit.After
    public void tearDown() throws Exception {
        super.tearDown();
    }

}