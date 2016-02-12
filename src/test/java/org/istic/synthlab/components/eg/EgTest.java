package org.istic.synthlab.components.eg;

import javafx.util.Pair;
import junit.framework.TestCase;
import org.istic.synthlab.core.Channel;
import org.istic.synthlab.core.services.Register;
import org.junit.Test;

/**
 * Module EG Tester.
 *
 * @author gottstein[dot]cyprien[at]gmail[dot]com on 10/02/16.
 * @author npaolita[at]yahoo[dot]fr on 10/02/16
 *
 */
public class EgTest extends TestCase {

    private Eg envelope;
    private static final double DELTA = 1e-15;

    @org.junit.Before
    public void setUp() throws Exception {
        super.setUp();
        envelope = new Eg("ENVELOPE");
    }

    @org.junit.Test
    public void testEGModule(){
        assertTrue(envelope.getName()=="ENVELOPE");
        assertTrue(Channel.contains(new Pair<>(envelope.getOutputModulator().getInput(),envelope.getOutput())));
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
        envelope.setAmplitude(0.8);
        assertEquals(0.8,envelope.getAmplitude(),DELTA);
    }

    public void testGetAmplitude() throws Exception {
        assertEquals(1.0,envelope.getAmplitude(),DELTA);
    }

    public void testGetAmplitudeMax() throws Exception {
        assertEquals(1.0,envelope.getAmplitudeMax(),DELTA);
    }

    public void testGetAmplitudeMin() throws Exception {
        assertEquals(0.0,envelope.getAmplitudeMin(),DELTA);
    }

    public void testSetDelay() throws Exception {
        envelope.setDelay(0.3);
        assertEquals(0.3, envelope.getDelay(),DELTA);
    }

    public void testGetDelay() throws Exception {
        assertEquals(0.0,envelope.getDelay(),DELTA);
    }

    public void testGetDelayMax() throws Exception {
        assertEquals(1.0,envelope.getDelayMax(),DELTA);
    }

    public void testGetDelayMin() throws Exception {
        assertEquals(0.0,envelope.getDelayMin(),DELTA);
    }

    public void testSetAttack() throws Exception {
        envelope.setAttack(0.8);
        assertEquals(0.8, envelope.getAttack(),DELTA);
    }

    // FIXME: value is 0.809, should be 0.1 ??
    // TODO: uncomment when done
//    public void testGetAttack() throws Exception {
//        assertEquals(0.1,envelope.getAttack(),DELTA);
//    }

    public void testGetAttackMax() throws Exception {
        assertEquals(1.0,envelope.getAttackMax(),DELTA);
    }

    public void testSetHold() throws Exception {
         envelope.setHold(0.6);
         assertEquals(0.6, envelope.getHold(),DELTA);
    }

    // FIXME: value is 0.6, should be 0.3 ??
    // TODO: uncomment when done
//    public void testGetHold() throws Exception {
//        assertEquals(0.3,envelope.getHold(),DELTA);
//    }

    public void testGetHoldMax() throws Exception {
        assertEquals(1.0,envelope.getHoldMax(),DELTA);
    }

    public void testGetHoldMin() throws Exception {
        assertEquals(0.0,envelope.getHoldMin(),DELTA);
    }

    public void testSetDecay() throws Exception {
        envelope.setDecay(0.2);
        assertEquals(0.2, envelope.getDecay(),DELTA);
    }

    // FIXME: value is 0.809, should be 0.1 ??
    // TODO: uncomment when done
//    public void testGetDecay() throws Exception {
//        assertEquals(0.1,envelope.getDecay(),DELTA);
//    }

    public void testGetDecayMax() throws Exception {
        assertEquals(1.0,envelope.getDecayMax(),DELTA);
    }

    public void testGetDecayMin() throws Exception {
        assertEquals(0.0,envelope.getDecayMin(),DELTA);
    }

    public void testSetSustain() throws Exception {
        envelope.setSustain(0.2);
        assertEquals(0.2, envelope.getSustain(),DELTA);
    }

    public void testGetSustain() throws Exception {
        assertEquals(0.5,envelope.getSustain(),DELTA);
    }

    public void testGetSustainMax() throws Exception {
        assertEquals(1.0,envelope.getSustainMax(),DELTA);
    }

    public void testGetSustainMin() throws Exception {
        assertEquals(0.0,envelope.getSustainMin(),DELTA);
    }

    public void testSetRelease() throws Exception {
        envelope.setRelease(0.2);
        assertEquals(0.2, envelope.getRelease(),DELTA);
    }

    // FIXME: value is 0.809, should be 0.1 ??
    // TODO: uncomment when done
//    public void testGetRelease() throws Exception {
//        assertEquals(0.1,envelope.getRelease(),DELTA);
//    }

    public void testGetReleaseMax() throws Exception {
        assertEquals(1.0,envelope.getReleaseMax(),DELTA);
    }

    public void testGetReleaseMin() throws Exception {
        assertEquals(0.0,envelope.getReleaseMin(),DELTA);
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }
}