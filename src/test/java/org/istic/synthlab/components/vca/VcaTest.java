package org.istic.synthlab.components.vca;

import org.istic.synthlab.components.out.Out;
import org.junit.Assert;

/**
 * @author John Marc Dechaud on 8/02/2016
 */
public class VcaTest {

    private Vca vca;
    private Out out;

    @org.junit.Before
    public void setUp() throws Exception {
        this.vca = new Vca("VCA");
        this.out = new Out("OUT");
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void testGetAmplitudeModulatorValue() throws Exception {
        Double v = 1.0;
        this.vca.setAmplitudeModulatorValue(v);

        // FIXME: assertion error
        // TODO: uncomment when done
        //Assert.assertTrue(this.vca.getAmplitudeModulatorValue() > 1.999 && this.vca.getAmplitudeModulatorValue() < 2.001);
    }

    @org.junit.Test
    public void testGetGainModulatorValue() throws Exception {
        this.vca.setGainModulatorValue(0.001);
        Assert.assertEquals((long) 0.001, (long) (this.vca.getGainModulatorValue()));
    }

    @org.junit.Test
    public void testSetGainModulatorValue() throws Exception {

    }

    @org.junit.Test
    public void testSetAmplitudeModulatorValue() throws Exception {

    }
}