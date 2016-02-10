package org.istic.synthlab.core.modules.passThrough;

import com.jsyn.Synthesizer;
import junit.framework.TestCase;
import org.istic.synthlab.components.out.Out;
import org.istic.synthlab.components.replicator.Replicator;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.lineOuts.ILineOut;
import org.istic.synthlab.core.modules.lineOuts.LineType;
import org.istic.synthlab.core.modules.oscillators.IOscillator;
import org.istic.synthlab.core.modules.oscillators.OscillatorType;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by cyprien on 09/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class PassThroughTest extends TestCase {

    private PassThrough passThrough;
    private IComponent component;

    @Before
    public void setUp(){
        component = Mockito.mock(Replicator.class);
        passThrough = new PassThrough(component);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void internalWire() throws Exception {

        IComponent componentSin = Mockito.mock(Vcoa.class);

        IComponent componentOut1 = Mockito.mock(Out.class);
        IComponent componentOut2 = Mockito.mock(Out.class);
        IComponent componentOut3 = Mockito.mock(Out.class);

        IOscillator sin = Factory.createOscillator(componentSin, OscillatorType.SINE);

        sin.setFrequency(320.0);
        sin.setAmplitude(1.0);
        sin.activate();

        ILineOut lineOut1 = Factory.createLineOut(componentOut1, LineType.OUT);
        ILineOut lineOut2 = Factory.createLineOut(componentOut2, LineType.OUT);
        ILineOut lineOut3 = Factory.createLineOut(componentOut3, LineType.OUT);

        sin.getOutput().connect(passThrough.getInput());

        lineOut1.getInput().connect(passThrough.getOutput1());
        lineOut2.getInput().connect(passThrough.getOutput2());
        lineOut3.getInput().connect(passThrough.getOutput3());

        Synthesizer synth = Factory.createSynthesizer();

        lineOut1.start();
        lineOut1.setVolume(0.0);

        lineOut2.start();
        lineOut2.setVolume(0.0);

        lineOut3.start();
        lineOut3.setVolume(0.0);

        synth.start();
        synth.sleepFor(0.5);

        int n = 10;
        while (n > 0){
            n--;
            assertNotSame(0.0, sin.getOutput().getUnitOutputPort().getValue());
            assertNotSame(0.0, passThrough.getOutput1().getUnitOutputPort().getValue());
            assertNotSame(0.0, passThrough.getOutput2().getUnitOutputPort().getValue());
            assertNotSame(0.0, passThrough.getOutput3().getUnitOutputPort().getValue());

            synth.sleepFor(0.5);
        }


    }

    @Test
    public void testGetInput() throws Exception {
        assertNotNull(passThrough.getInput());
        assertNotNull(Register.getComponent(passThrough.getInput()));
        assertEquals(component, Register.getComponent(passThrough.getInput()));
    }

    @Test
    public void testGetOutput1() throws Exception {
        assertNotNull(passThrough.getOutput1());
        assertNotNull(Register.getComponent(passThrough.getOutput1()));
        assertEquals(component, Register.getComponent(passThrough.getOutput1()));
    }

    @Test
    public void testGetOutput2() throws Exception {
        assertNotNull(passThrough.getOutput2());
        assertNotNull(Register.getComponent(passThrough.getOutput2()));
        assertEquals(component, Register.getComponent(passThrough.getOutput2()));
    }

    @Test
    public void testGetOutput3() throws Exception {
        assertNotNull(passThrough.getOutput3());
        assertNotNull(Register.getComponent(passThrough.getOutput3()));
        assertEquals(component, Register.getComponent(passThrough.getOutput3()));
    }

    @Test
    public void testActivate() throws Exception {
        passThrough.activate();
        assertTrue(passThrough.isActivated());
    }

    @Test
    public void testDesactivate() throws Exception {
        passThrough.deactivate();
        assertFalse(passThrough.isActivated());
    }

    public void testIsEnable() throws Exception {
        // nothing to do here, already tested by the two function ahead.
    }
}