package org.istic.synthlab.functionnal;

import com.jsyn.Synthesizer;
import org.istic.synthlab.components.eg.Eg;
import org.istic.synthlab.components.out.Out;
import org.istic.synthlab.components.replicator.Replicator;
import org.istic.synthlab.components.vca.Vca;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.services.Register;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


/**
 * @author Cyprien
 */
public class Sprint2ChainTest {

    private Vcoa vcoa1;
    private Vcoa vcoa2;
    private Eg eg;
    private Vca vca;
    private Out out;
    private Replicator rep;
    private Synthesizer synth;

    @Before
    public void setUp() throws Exception {
        vcoa1 = new Vcoa("VCOA");
        vcoa2 = new Vcoa("VCOA");
        vcoa1.activate();
        vcoa2.activate();
        vca = new Vca("VCA");
        vca.activate();
        eg = new Eg("EG");
        eg.activate();
        rep = new Replicator("REP");
        rep.activate();
        out = new Out("OUT");
        out.activate();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void BasicTest() {
        vcoa1.getOutput().connect(rep.getInput());
        rep.getOutput().connect(vcoa2.getFm());
        rep.getOutput2().connect(eg.getInputGate());
        vcoa2.getOutput().connect(vca.getInput());
        eg.getOutput().connect(vca.getAm());
        vca.getOutput().connect(out.getInput());
        assertTrue(Register.isConnected(vcoa1.getOutput()));
        assertTrue(Register.isConnected(out.getInput()));
    }
}
