package org.istic.synthlab.components.replicator;

import org.istic.synthlab.core.services.Register;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author gottstein[dot]cyprien[at]gmail[dot]com on 09/02/16.
 * Module Replicator Tester.
 */
public class ReplicatorTest {

    private Replicator replicator;

    @Before
    public void setUp() throws Exception {
        replicator = new Replicator("REPL");
    }

    @Test
    public void testActivate() throws Exception {
        replicator.activate();
        assertTrue(replicator.isActivated());
        assertTrue(replicator.getPassThrough().isActivated());
    }

    @Test
    public void testDesactivate() throws Exception {
        replicator.deactivate();
        assertFalse(replicator.isActivated());
        assertFalse(replicator.getPassThrough().isActivated());
    }

    @Test
    public void testGetInput() throws Exception {
        assertNotNull(replicator.getInput());
        assertEquals(replicator, Register.getComponent(replicator.getInput()));
    }

    @Test
    public void testGetOutput() throws Exception {
        assertNotNull(replicator.getOutput());
        assertEquals(replicator, Register.getComponent(replicator.getOutput()));
    }

    @Test
    public void testGetOutputReplicated1() throws Exception {
        assertNotNull(replicator.getOutputReplicated1());
        assertEquals(replicator, Register.getComponent(replicator.getOutputReplicated1()));
    }

    @Test
    public void testGetOutputReplicated2() throws Exception {
        assertNotNull(replicator.getOutputReplicated2());
        assertEquals(replicator, Register.getComponent(replicator.getOutputReplicated2()));
    }

    @Test
    public void testGetPassThrough() throws Exception {
        assertNotNull(replicator.getPassThrough());
    }
}