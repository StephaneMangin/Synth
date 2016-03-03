package org.istic.synthlab.components.eg;

import org.istic.synthlab.core.services.Register;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Module EG Tester.
 *
 * @author gottstein[dot]cyprien[at]gmail[dot]com on 10/02/16.
 * @author npaolita[at]yahoo[dot]fr on 10/02/16
 *
 */
public class EgTest {

    private Eg envelope;
    private static final double DELTA = 1e-2;

    @Before
    public void setUp() throws Exception {
        envelope = new Eg("ENVELOPE");
    }

    @Test
    public void testEGModule(){
        assertEquals("ENVELOPE", envelope.getName());
     }


    @Test
    public void testActivate() throws Exception {
        envelope.activate();
        assertTrue(envelope.isActivated());
    }

    @Test
    public void testDeactivate() throws Exception {
        envelope.activate();
        envelope.deactivate();
        assertFalse(envelope.isActivated());
    }

    @Test
    public void testInit() throws Exception {

    }

    @Test
    public void testRun() throws Exception {

    }

    @Test
    public void testGetInput() throws Exception {
        assertNotNull(envelope.getInput());
        assertEquals(envelope, Register.getComponent(envelope.getInput()));
    }

    @Test
    public void testGetOutput() throws Exception {
        assertNotNull(envelope.getOutput());
        assertEquals(envelope, Register.getComponent(envelope.getOutput()));
    }

    @Test
    public void testSetAmplitude() throws Exception {
        envelope.getAmplitudePotentiometer().setValue(0.8);
        assertEquals(0.8,envelope.getAmplitudePotentiometer().getValue(),DELTA);
    }

    @Test
    public void testGetAmplitude() throws Exception {
        assertEquals(1.0,envelope.getAmplitudePotentiometer().getValue(),DELTA);
    }

    @Test
    public void testGetAmplitudeMax() throws Exception {
        assertEquals(1.0,envelope.getAmplitudePotentiometer().getMax(),DELTA);
    }

    @Test
    public void testGetAmplitudeMin() throws Exception {
        assertEquals(0.0,envelope.getAmplitudePotentiometer().getMin(),DELTA);
    }

    @Test
    public void testSetDelay() throws Exception {
        envelope.getDelayPotentiometer().setValue(0.3);
        assertEquals(0.3, envelope.getDelayPotentiometer().getValue(),DELTA);
    }

    @Test
    public void testGetDelay() throws Exception {
        assertEquals(0.0,envelope.getDelayPotentiometer().getValue(),DELTA);
    }

    @Test
    public void testGetDelayMax() throws Exception {
        assertEquals(1.0,envelope.getDelayPotentiometer().getMax(),DELTA);
    }

    @Test
    public void testGetDelayMin() throws Exception {
        assertEquals(0.0,envelope.getDelayPotentiometer().getMin(),DELTA);
    }

    @Test
    public void testSetAttack() throws Exception {
        envelope.getAmplitudePotentiometer().setValue(0.8);
        assertEquals(0.8, envelope.getAttackPotentiometer().getValue(),DELTA);
    }

    public void testGetAttack() throws Exception {
        assertEquals(0.1,envelope.getAttackPotentiometer().getValue(),DELTA);
    }

    @Test
    public void testGetAttackMax() throws Exception {
        assertEquals(1.0,envelope.getAttackPotentiometer().getMax(),DELTA);
    }

    @Test
    public void testSetHold() throws Exception {
         envelope.getHoldPotentiometer().setValue(0.6);
         assertEquals(0.6, envelope.getHoldPotentiometer().getValue(),DELTA);
    }

    public void testGetHold() throws Exception {
        assertEquals(0.3,envelope.getHoldPotentiometer().getValue(),DELTA);
    }

    @Test
    public void testGetHoldMax() throws Exception {
        assertEquals(1.0,envelope.getHoldPotentiometer().getMax(),DELTA);
    }

    @Test
    public void testGetHoldMin() throws Exception {
        assertEquals(0.0,envelope.getHoldPotentiometer().getMin(),DELTA);
    }

    @Test
    public void testSetDecay() throws Exception {
        envelope.getDecayPotentiometer().setValue(0.2);
        assertEquals(0.2, envelope.getDecayPotentiometer().getValue(),DELTA);
    }

    public void testGetDecay() throws Exception {
        assertEquals(0.1,envelope.getDecayPotentiometer().getValue(),DELTA);
    }

    @Test
    public void testGetDecayMax() throws Exception {
        assertEquals(1.0,envelope.getDecayPotentiometer().getMax(),DELTA);
    }

    @Test
    public void testGetDecayMin() throws Exception {
        assertEquals(0.0,envelope.getDecayPotentiometer().getMin(),DELTA);
    }

    @Test
    public void testSetSustain() throws Exception {
        envelope.getSustainPotentiometer().setValue(0.2);
        assertEquals(0.2, envelope.getSustainPotentiometer().getValue(),DELTA);
    }

    @Test
    public void testGetSustain() throws Exception {
        assertEquals(0.5,envelope.getSustainPotentiometer().getValue(),DELTA);
    }

    @Test
    public void testGetSustainMax() throws Exception {
        assertEquals(1.0,envelope.getSustainPotentiometer().getMax(),DELTA);
    }

    @Test
    public void testGetSustainMin() throws Exception {
        assertEquals(0.0,envelope.getSustainPotentiometer().getMin(),DELTA);
    }

    @Test
    public void testSetRelease() throws Exception {
        envelope.getReleasePotentiometer().setValue(0.2);
        assertEquals(0.2, envelope.getReleasePotentiometer().getValue(),DELTA);
    }

   public void testGetRelease() throws Exception {
       assertEquals(0.1,envelope.getReleasePotentiometer().getValue(),DELTA);
    }

    @Test
    public void testGetReleaseMax() throws Exception {
        assertEquals(1.0,envelope.getReleasePotentiometer().getMax(),DELTA);
    }

    @Test
    public void testGetReleaseMin() throws Exception {
        assertEquals(0.0,envelope.getReleasePotentiometer().getMin(),DELTA);
    }
}