package org.istic.synthlab.core;

import com.jsyn.Synthesizer;
import org.istic.synthlab.components.out.Out;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.services.IOMapping;
import org.istic.synthlab.core.services.ModulesFactory;
import org.junit.Test;

/**
 * Created by cyprien on 04/02/16.
 */
public class BasicChainTest {

    @org.junit.Before
    public void setUp() throws Exception {

    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @Test
    public void basicChainTest() throws InterruptedException {

        // Composant VCOa
        Vcoa composantVcoa = new Vcoa("VCOA");

        // Composant OUT
        Out composantOut = new Out("OUT");

        IOMapping.connect(composantOut.getIInput(), composantVcoa.getOutput());

        composantOut.getLineOut().start();
        Synthesizer synth = ModulesFactory.createSynthesizer();
        synth.start();
        synth.sleepUntil(10);


    }
}
