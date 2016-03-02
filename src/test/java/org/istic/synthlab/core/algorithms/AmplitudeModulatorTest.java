package org.istic.synthlab.core.algorithms;

import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.components.replicator.Replicator;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.modulators.AmplitudeModulator;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Module AmplitudeModulator Tester.
 *
 * @author <Ngassam Noumi Paola> npaolita.[ât]yahoo.fr
 * @since <pre>Feb 10, 2016</pre>
 */
public class AmplitudeModulatorTest {

    private AmplitudeModulator amplitudeModulator;
    private IComponent replicator;

    @Before
    public void setUp() throws Exception {
        replicator = new Replicator("replicator");
        amplitudeModulator = new AmplitudeModulator("Am Mod",replicator, PotentiometerType.EXPONENTIAL);
    }
    @Test
    public void testAmplitudeModulator(){
        assertNotNull(amplitudeModulator.getInput());
        assertEquals(amplitudeModulator.getInput().getName(), "Am Mod::ampIn");
        assertNotNull(amplitudeModulator.getOutput());
        assertEquals(amplitudeModulator.getOutput().getName(), "Am Mod::ampOut");
        assertTrue(amplitudeModulator.getMin() == 0D);
        assertTrue(amplitudeModulator.getMax() == 1D);
    }

    @Test
    public void testGetInput(){
        IInput input = amplitudeModulator.getInput();
        assertNotNull(input);
    }

    @Test
    public void testGetOutput(){
        IOutput output = amplitudeModulator.getOutput();
        assertNotNull(output);
    }

    @Test
    public void testGetValue(){
        double value = amplitudeModulator.getValue();
        assertEquals(amplitudeModulator.getValue(), value, 1e-15);
    }

    @Test
    public void testSetValue(){
        amplitudeModulator.setValue(10.0);
        double value = amplitudeModulator.getValue();
        assertEquals(amplitudeModulator.getValue(), value, 1e-15);
    }

    @Test
    public void testActivate(){
        amplitudeModulator.activate();
        assertTrue(amplitudeModulator.isActivated());
    }

    @Test
    public void testDeactivate(){
        amplitudeModulator.deactivate();
        assertFalse(amplitudeModulator.isActivated());
    }

}
