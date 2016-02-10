package org.istic.synthlab.components.eg;

import junit.framework.TestCase;
import org.istic.synthlab.core.services.Register;
import org.junit.Test;

/**
 * @author gottstein[dot]cyprien[at]gmail[dot]com on 10/02/16.
 */
public class EgTest extends TestCase {

    private Eg envelope;

    public void setUp() throws Exception {
        super.setUp();
        envelope = new Eg("ENVELOPE");

    }

    public void tearDown() throws Exception {

    }

    @Test
    public void testActivate() throws Exception {
        envelope.activate();
        assertTrue(envelope.isActivated());
    }

    @Test
    public void testDesactivate() throws Exception {
        envelope.deactivate();
        assertFalse(envelope.isActivated());
    }

    public void testInit() throws Exception {

    }

    public void testRun() throws Exception {

    }

    @Test
    public void testGetInput() throws Exception {
        assertNotNull(envelope.getInput());
        assertEquals(envelope, Register.getComponent(envelope.getInput()));
    }

    public void testGetOutput() throws Exception {
        assertNotNull(envelope.getOutput());
        assertEquals(envelope, Register.getComponent(envelope.getOutput()));
    }

    public void testSetAmplitude() throws Exception {

    }

    public void testGetAmplitude() throws Exception {

    }

    public void testGetAmplitudeMax() throws Exception {

    }

    public void testGetAmplitudeMin() throws Exception {

    }

    public void testSetDelay() throws Exception {

    }

    public void testGetDelay() throws Exception {

    }

    public void testGetDelayMax() throws Exception {

    }

    public void testGetDelayMin() throws Exception {

    }

    public void testSetAttack() throws Exception {

    }

    public void testGetAttack() throws Exception {

    }

    public void testGetAttackMax() throws Exception {

    }

    public void testSetHold() throws Exception {

    }

    public void testGetHold() throws Exception {

    }

    public void testGetHoldMax() throws Exception {

    }

    public void testGetHoldMin() throws Exception {

    }

    public void testSetDecay() throws Exception {

    }

    public void testGetDecay() throws Exception {

    }

    public void testGetDecayMax() throws Exception {

    }

    public void testGetDecayMin() throws Exception {

    }

    public void testSetSustain() throws Exception {

    }

    public void testGetSustain() throws Exception {

    }

    public void testGetSustainMax() throws Exception {

    }

    public void testGetSustainMin() throws Exception {

    }

    public void testSetRelease() throws Exception {

    }

    public void testGetRelease() throws Exception {

    }

    public void testGetReleaseMax() throws Exception {

    }

    public void testGetReleaseMin() throws Exception {

    }
}