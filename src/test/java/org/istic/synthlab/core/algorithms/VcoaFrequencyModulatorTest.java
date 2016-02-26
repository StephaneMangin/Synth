package org.istic.synthlab.core.algorithms;

import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.modulators.VcoaFrequencyModulator;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Module VcoaFrequencyModulator Tester.
 *
 * @author <Ngassam Noumi Paola> npaolita.[ât]yahoo.fr
 * @since <pre>Feb 8, 2016</pre>
 */
public class VcoaFrequencyModulatorTest {

    private Vcoa vcoa;
    private VcoaFrequencyModulator frequencyModulator;

    @Before
    public void setUp() throws Exception {
        vcoa = new Vcoa("VCOA");
        frequencyModulator = new VcoaFrequencyModulator("Freq Mod",vcoa, PotentiometerType.EXPONENTIAL);
    }

    @Test
    public void testVcoaFrequencyModulator(){
        assertNotNull(frequencyModulator.getInput());
        assertEquals(frequencyModulator.getInput().getName(), "Freq Mod::freqIn");
        assertNotNull(frequencyModulator.getOutput());
        assertEquals(frequencyModulator.getOutput().getName(), "Freq Mod::freqOut");
        assertTrue(frequencyModulator.getMin() == 20.0);
        assertTrue(frequencyModulator.getMax() == 20000.0);
    }

    @Test
    public void testGetInput(){
        IInput input = frequencyModulator.getInput();
        assertNotNull(input);
    }

    @Test
    public void testGetOutput(){
        IOutput output = frequencyModulator.getOutput();
        assertNotNull(output);
    }

    @Test
    public void testGetValue(){
        double value = frequencyModulator.getValue();
        assertEquals(frequencyModulator.getValue(), value, 1e-15);
    }

    @Test
    public void testSetValue(){
        frequencyModulator.setValue(20.0);
        double value = frequencyModulator.getValue();
        assertEquals(frequencyModulator.getValue(), value, 1e-15);
    }
    @Test
    public void testActivate(){
        frequencyModulator.activate();
        assertTrue(frequencyModulator.isActivated());
    }

    @Test
    public void testDeactivate(){
        frequencyModulator.deactivate();
        assertFalse(frequencyModulator.isActivated());
    }
}
