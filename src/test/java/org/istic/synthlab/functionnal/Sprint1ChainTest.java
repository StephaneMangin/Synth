package org.istic.synthlab.functionnal;

import com.jsyn.Synthesizer;
import org.istic.synthlab.components.out.Out;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.services.Register;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import static junit.framework.Assert.assertTrue;


/**
 * @author Cyprien
 */
public class Sprint1ChainTest {

    private Vcoa vcoa;
    private Out out;
    private Synthesizer synth;

    @Before
    public void setUp() throws Exception {
        vcoa = new Vcoa("VCOA");
        vcoa.activate();
        out = new Out("OUT");
        out.activate();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void BasicTest() {
        vcoa.getOutput().connect(out.getInput());
        assertTrue(Register.isConnected(vcoa.getOutput()));
        assertTrue(Register.isConnected(out.getInput()));
    }
}
