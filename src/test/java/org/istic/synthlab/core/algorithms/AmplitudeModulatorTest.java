package org.istic.synthlab.core.algorithms;

import junit.framework.TestCase;
import org.istic.synthlab.components.replicator.Replicator;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.modulators.AmplitudeModulator;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * Module AmplitudeModulator Tester.
 *
 * @author <Ngassam Noumi Paola> npaolita.[ât]yahoo.fr
 * @since <pre>Feb 10, 2016</pre>
 */
public class AmplitudeModulatorTest extends TestCase {

    private AmplitudeModulator amplitudeModulator;
    private IComponent replicator;

    @org.junit.Before
    public void setUp() throws Exception {
        replicator = new Replicator("replicator");
        amplitudeModulator = new AmplitudeModulator("Am Mod",replicator, PotentiometerType.EXPONENTIAL);
    }
    @org.junit.Test
    public void testAmplitudeModulator(){
        Potentiometer potentiometer= amplitudeModulator.getPotentiometer();
        assertNotNull(amplitudeModulator.getInput());
        assertEquals(amplitudeModulator.getInput().getName(), "Am Mod::ampIn");
        assertNotNull(amplitudeModulator.getOutput());
        assertEquals(amplitudeModulator.getOutput().getName(), "Am Mod::ampOut");
        assertTrue(potentiometer.getMin() == 0D);
        assertTrue(potentiometer.getMax() == 1D);
        assertTrue(potentiometer.getType() == PotentiometerType.EXPONENTIAL);
    }

    @org.junit.Test
    public void testGetInput(){
        IInput input = amplitudeModulator.getInput();
        assertNotNull(input);
    }

    @org.junit.Test
    public void testGetOutput(){
        IOutput output = amplitudeModulator.getOutput();
        assertNotNull(output);
    }

    @org.junit.Test
    public void testGetValue(){
        double value = amplitudeModulator.getValue();
        Potentiometer potentiometer= amplitudeModulator.getPotentiometer();
        assertEquals(potentiometer.getValue(), value);
    }

    @org.junit.Test
    public void testSetValue(){
        amplitudeModulator.setValue(10.0);
        Potentiometer potentiometer= amplitudeModulator.getPotentiometer();
        double value = amplitudeModulator.getValue();
        assertEquals(potentiometer.getValue(), value);
    }

    @org.junit.Test
    public void testActivate(){
        amplitudeModulator.activate();
        assertTrue(amplitudeModulator.isActivated());
    }

    @org.junit.Test
    public void testDeactivate(){
        amplitudeModulator.deactivate();
        assertFalse(amplitudeModulator.isActivated());
    }

}
