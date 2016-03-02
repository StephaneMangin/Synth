package org.istic.synthlab.components.seq;

import org.istic.synthlab.core.services.Register;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author npaolita[at]yahoo[dot]fr
 * Module Replicator Tester.
 */
public class SequencerTest {

    private Sequencer sequencer;

    @Before
    public void setUp() throws Exception {
        sequencer = new Sequencer("SEQUENCER");
    }

    @Test
    public void testActivate() throws Exception {
        sequencer.activate();
       assertTrue(sequencer.isActivated());
    }

    @Test
    public void testDesactivate() throws Exception {
        sequencer.activate();
        sequencer.deactivate();
        assertFalse(sequencer.isActivated());
    }

    @Test
    public void testGetInputgate() throws Exception {
        assertNotNull(sequencer.getInputgate());
        assertEquals(sequencer, Register.getComponent(sequencer.getInputgate()));
    }

    @Test
    public void testGetOutputSeq() throws Exception {
        assertNotNull(sequencer.getOutputSeq());
        assertEquals(sequencer, Register.getComponent(sequencer.getOutputSeq()));
    }

    @Test
    public void testSetStepValue(){
        sequencer.setStepValue(1, 0.5);
        assertEquals(0.5,sequencer.getStepValue(1),1e-2);
    }

    @Test
    public void testGetCurrentStep(){
       int step= sequencer.getCurrentStep();
        assertEquals(1,step);
    }
    @Test
    public void testReset(){
        sequencer.reset();
        assertEquals(1,sequencer.getCurrentStep());
    }

    @Test
    public void testGetMaxValue(){
       double max =  sequencer.getMaxValue();
        assertEquals(1, max, 1e-2);
    }

    @Test
    public void testGetMinValue(){
        double min =  sequencer.getMinValue();
        assertEquals(-1, min, 1e-2);
    }

}