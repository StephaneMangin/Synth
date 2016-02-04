package org.istic.synthlab.core;

import org.istic.synthlab.components.out.Out;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by cyprien on 04/02/16.
 */
public class BasicChainTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void basicChainTest() throws InterruptedException {

        // Composant VCOa
        Vcoa composantVcoa = new Vcoa("VCOA");

        // Composant OUT
        Out composantOut = new Out("OUT");

        IOMappingService.connect(composantOut.getIInput(), composantVcoa.getOutput());

        composantOut.getLineOut().start();
        AdapterFactory.createSynthesizer().start();
        AdapterFactory.createSynthesizer().sleepUntil(10);


    }
}
