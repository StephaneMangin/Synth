package org.istic.synthlab.core;

import com.jsyn.Synthesizer;
import org.istic.synthlab.components.out.Out;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.services.ModulesFactory;
import org.junit.Test;

/**
 * Created by cyprien on 04/02/16.
 */
public class BasicChainTest {

    private Vcoa composantVcoa;
    private Out composantOut;
    private Synthesizer synth;

    @org.junit.Before
    public void setUp() throws Exception {
        composantVcoa = new Vcoa("VCOA");
        composantOut = new Out("OUT");
        synth = ModulesFactory.createSynthesizer();
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    @Test
    public void basicChainTest() throws InterruptedException {
        Register.connect(composantOut.getIInput(), composantVcoa.getOutput());
        composantOut.getLineOut().start();
        synth.start();
        synth.sleepUntil(10);
    }
}
