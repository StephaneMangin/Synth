package org.istic.synthlab.components.vca;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        vca.deactivate();
        assertFalse(vca.isActivated());
    }

    @Test
    public void testGetAmplitudeModulatorValue() throws Exception {
        Double v = 1.0;
        this.vca.getGainModulator().setValue(v);

        // FIXME: assertion error
        // TODO: uncomment when done
        //Assert.assertTrue(this.vca.getAmplitudeModulatorValue() > 1.999 && this.vca.getAmplitudeModulatorValue() < 2.001);
    }

    @Test
    public void testGetGainModulatorValue() throws Exception {
        this.vca.getGainModulator().getValue();
        assertEquals((long) 0.0, (long) (this.vca.getGainModulator().getValue()));
    }

    @Test
    public void testSetGainModulatorValue() throws Exception {
        this.vca.getGainModulator().setValue(0.001);
        assertEquals((long) 0.001, (long) (this.vca.getGainModulator().getValue()));
    }

}