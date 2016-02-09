package org.istic.synthlab.core.algorithms;

import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.modulators.VcoaFrequencyModulator;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Module VcoaFrequencyModulator Tester.
 *
 * @author <Ngassam Noumi Paola> npaolita.[ât]yahoo.fr
 * @since <pre>Feb 8, 2016</pre>
 */
public class VcoaFrequencyModulatorTest {

    private VcoaFrequencyModulator frequencyModulator;

    @Before
    public void setUp() throws Exception {
        Vcoa vcoa = new Vcoa("VCOA");
        frequencyModulator = new VcoaFrequencyModulator("Freg Mod", vcoa, PotentiometerType.EXPONENTIAL);
    }

    @Test
    public void testVcoaFrequencyModulator(){
        Potentiometer potentiometer= frequencyModulator.getPotentiometer();
        assertNotNull(frequencyModulator.getInput());
        assertNotNull(frequencyModulator.getOutput());
        assertTrue(potentiometer.getMin() == 20.0);
        assertTrue(potentiometer.getMax() == 20000.0);
        assertTrue(potentiometer.getType() == PotentiometerType.EXPONENTIAL);
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
        Potentiometer potentiometer= frequencyModulator.getPotentiometer();
        double value = frequencyModulator.getValue();
        assertEquals(potentiometer.getValue(), value, 1e-15);
    }

    @Test
    public void testSetValue(){
        frequencyModulator.setValue(20.0);
        Potentiometer potentiometer= frequencyModulator.getPotentiometer();
        double value = frequencyModulator.getValue();
        assertEquals(potentiometer.getValue(), value, 1e-15);
    }
}
