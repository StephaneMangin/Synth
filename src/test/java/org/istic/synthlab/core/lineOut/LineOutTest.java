package org.istic.synthlab.core.lineOut;

import com.jsyn.Synthesizer;
import org.istic.synthlab.components.out.Out;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.lineOuts.LineOut;
import org.istic.synthlab.core.services.Factory;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * LineOut Tester.
 *
 * @author <Ngassam Noumi Paola> npaolita.[ât]yahoo.fr
 */
public class LineOutTest {

    private LineOut lineOut;
    private Synthesizer synth;
    private IComponent out;
    private static final double DELTA = 1e-15;

    @org.junit.Before
    public void setUp() throws Exception {
        out = new Out("OUT");
        lineOut = new LineOut(out);
        synth = Factory.createSynthesizer();
    }

    @Test
    public void testSetVolume()  {
        lineOut.setVolume(0.2);
        assertEquals(0.2,lineOut.getVolume(),DELTA);
    }

    @Test
    public void testGetVolume()  {
        assertEquals(0.01,lineOut.getVolume(),DELTA);
    }

    @Test
    public void testStart()  {
        lineOut.start();
        assertTrue(lineOut.isActivated());
        assertTrue(synth.isRunning());
    }

    @Test
    public void testStop()  {
        lineOut.stop();
        assertFalse(lineOut.isActivated());
        assertFalse(synth.isRunning());
    }

    @Test
    public void testGetInput()  {
        assertNotNull(lineOut.getInput());
    }

}
