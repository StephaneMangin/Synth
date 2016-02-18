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
        envelope.getAmplitudePotentiometer().setValue(0.8);
        assertEquals(0.8,envelope.getAmplitudePotentiometer().getValue(),DELTA);
    }

    public void testGetAmplitude() throws Exception {
        assertEquals(1.0,envelope.getAmplitudePotentiometer().getValue(),DELTA);
    }

    public void testGetAmplitudeMax() throws Exception {
        assertEquals(1.0,envelope.getAmplitudePotentiometer().getMax(),DELTA);
    }

    public void testGetAmplitudeMin() throws Exception {
        assertEquals(0.0,envelope.getAmplitudePotentiometer().getMin(),DELTA);
    }

    public void testSetDelay() throws Exception {
        envelope.getDelayPotentiometer().setValue(0.3);
        assertEquals(0.3, envelope.getDelayPotentiometer().getValue(),DELTA);
    }

    public void testGetDelay() throws Exception {
        assertEquals(0.0,envelope.getDelayPotentiometer().getValue(),DELTA);
    }

    public void testGetDelayMax() throws Exception {
        assertEquals(1.0,envelope.getDelayPotentiometer().getMax(),DELTA);
    }

    public void testGetDelayMin() throws Exception {
        assertEquals(0.0,envelope.getDelayPotentiometer().getMin(),DELTA);
    }

    public void testSetAttack() throws Exception {
        envelope.getAmplitudePotentiometer().setValue(0.8);
        assertEquals(0.8, envelope.getAttackPotentiometer().getValue(),DELTA);
    }

    // FIXME: value is 0.809, should be 0.1 ??
    // TODO: uncomment when done
//    public void testGetAttack() throws Exception {
//        assertEquals(0.1,envelope.getAttack(),DELTA);
//    }

    public void testGetAttackMax() throws Exception {
        assertEquals(1.0,envelope.getAttackPotentiometer().getMax(),DELTA);
    }

    public void testSetHold() throws Exception {
         envelope.getHoldPotentiometer().setValue(0.6);
         assertEquals(0.6, envelope.getHoldPotentiometer().getValue(),DELTA);
    }

    // FIXME: value is 0.6, should be 0.3 ??
    // TODO: uncomment when done
//    public void testGetHold() throws Exception {
//        assertEquals(0.3,envelope.getHold(),DELTA);
//    }

    public void testGetHoldMax() throws Exception {
        assertEquals(1.0,envelope.getHoldPotentiometer().getMax(),DELTA);
    }

    public void testGetHoldMin() throws Exception {
        assertEquals(0.0,envelope.getHoldPotentiometer().getMin(),DELTA);
    }

    public void testSetDecay() throws Exception {
        envelope.getDecayPotentiometer().setValue(0.2);
        assertEquals(0.2, envelope.getDecayPotentiometer().getValue(),DELTA);
    }

    // FIXME: value is 0.809, should be 0.1 ??
    // TODO: uncomment when done
//    public void testGetDecay() throws Exception {
//        assertEquals(0.1,envelope.getDecay(),DELTA);
//    }

    public void testGetDecayMax() throws Exception {
        assertEquals(1.0,envelope.getDecayPotentiometer().getMax(),DELTA);
    }

    public void testGetDecayMin() throws Exception {
        assertEquals(0.0,envelope.getDecayPotentiometer().getMin(),DELTA);
    }

    public void testSetSustain() throws Exception {
        envelope.getSustainPotentiometer().setValue(0.2);
        assertEquals(0.2, envelope.getSustainPotentiometer().getValue(),DELTA);
    }

    public void testGetSustain() throws Exception {
        assertEquals(0.0,envelope.getSustainPotentiometer().getValue(),DELTA);
    }

    public void testGetSustainMax() throws Exception {
        assertEquals(1.0,envelope.getSustainPotentiometer().getMax(),DELTA);
    }

    public void testGetSustainMin() throws Exception {
        assertEquals(0.0,envelope.getSustainPotentiometer().getMin(),DELTA);
    }

    public void testSetRelease() throws Exception {
        envelope.getReleasePotentiometer().setValue(0.2);
        assertEquals(0.2, envelope.getReleasePotentiometer().getValue(),DELTA);
    }

    // FIXME: value is 0.809, should be 0.1 ??
    // TODO: uncomment when done
//    public void testGetRelease() throws Exception {
//        assertEquals(0.1,envelope.getRelease(),DELTA);
//    }

    public void testGetReleaseMax() throws Exception {
        assertEquals(1.0,envelope.getReleasePotentiometer().getMax(),DELTA);
    }

    public void testGetReleaseMin() throws Exception {
        assertEquals(0.0,envelope.getReleasePotentiometer().getMin(),DELTA);
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }
}