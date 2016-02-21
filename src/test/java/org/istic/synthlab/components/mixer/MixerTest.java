package org.istic.synthlab.components.mixer;

import org.istic.synthlab.core.services.Register;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author gottstein[dot]cyprien[at]gmail[dot]com on 09/02/16.
 * Module Replicator Tester.
 */
public class MixerTest {

    private Mixer mixer;

    @Before
    public void setUp() throws Exception {
        mixer = new Mixer("MIX");
    }

    @Test
    public void testActivate() throws Exception {
        mixer.activate();
        assertTrue(mixer.isActivated());
    }

    @Test
    public void testDesactivate() throws Exception {
        mixer.deactivate();
        assertFalse(mixer.isActivated());
    }

    @Test
    public void testGetInputs() throws Exception {
        assertNotNull(mixer.getInput1());
        assertEquals(mixer, Register.getComponent(mixer.getInput1()));

        assertNotNull(mixer.getInput2());
        assertEquals(mixer, Register.getComponent(mixer.getInput2()));

        assertNotNull(mixer.getInput3());
        assertEquals(mixer, Register.getComponent(mixer.getInput3()));

        assertNotNull(mixer.getInput4());
        assertEquals(mixer, Register.getComponent(mixer.getInput4()));
    }

    @Test
    public void testGetOutputMixer() throws Exception {
        assertNotNull(mixer.getOutputMixer());
        assertEquals(mixer, Register.getComponent(mixer.getOutputMixer()));
    }

    @Test
    public void testGetGainValueInputs() throws Exception {
        this.mixer.setGainValueInput1(0.5);
        assertEquals(0.5, this.mixer.getGainValueInput1(), 0.0);

        this.mixer.setGainValueInput2(0.002);
        assertEquals(0.002, this.mixer.getGainValueInput2(), 0.0);

        this.mixer.setGainValueInput3(0.12);
        assertEquals(0.12, this.mixer.getGainValueInput3(), 0.0);

        this.mixer.setGainValueInput4(0.60);
        assertEquals(0.60, this.mixer.getGainValueInput4(), 0.0);
    }

}