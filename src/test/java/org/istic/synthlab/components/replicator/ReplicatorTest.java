package org.istic.synthlab.components.replicator;

import junit.framework.TestCase;
import org.istic.synthlab.core.services.Register;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by cyprien on 09/02/16.
 */
public class ReplicatorTest extends TestCase {

    private Replicator replicator;

    @Before
    public void setUp() throws Exception {
        replicator = new Replicator("REPL");
    }

    @Test
    public void testActivate() throws Exception {
        replicator.activate();
        assertTrue(replicator.isActivated());
        assertTrue(replicator.getPassThrough().isEnable());
    }

    @Test
    public void testDesactivate() throws Exception {
        replicator.desactivate();
        assertFalse(replicator.isActivated());
        assertFalse(replicator.getPassThrough().isEnable());
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
}