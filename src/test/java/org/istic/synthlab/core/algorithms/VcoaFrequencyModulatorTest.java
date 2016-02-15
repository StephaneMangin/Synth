package org.istic.synthlab.core.algorithms;

import junit.framework.TestCase;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.modulators.VcoaFrequencyModulator;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * Module VcoaFrequencyModulator Tester.
 *
 * @author <Ngassam Noumi Paola> npaolita.[ât]yahoo.fr
 * @since <pre>Feb 8, 2016</pre>
 */
public class VcoaFrequencyModulatorTest extends TestCase {

    private Vcoa vcoa;
    private VcoaFrequencyModulator frequencyModulator;

    @org.junit.Before
    public void setUp() throws Exception {
        vcoa = new Vcoa("VCOA");
        frequencyModulator = new VcoaFrequencyModulator("Freq Mod",vcoa, PotentiometerType.EXPONENTIAL);
    }

    @org.junit.Test
    public void testVcoaFrequencyModulator(){
        assertNotNull(frequencyModulator.getInput());
        assertEquals(frequencyModulator.getInput().getName(), "Freq Mod::freqIn");
        assertNotNull(frequencyModulator.getOutput());
        assertEquals(frequencyModulator.getOutput().getName(), "Freq Mod::freqOut");
        assertTrue(frequencyModulator.getMin() == 20.0);
        assertTrue(frequencyModulator.getMax() == 20000.0);
    }

    @org.junit.Test
    public void testGetInput(){
        IInput input = frequencyModulator.getInput();
        assertNotNull(input);
    }

    @org.junit.Test
    public void testGetOutput(){
        IOutput output = frequencyModulator.getOutput();
        assertNotNull(output);
    }

    @org.junit.Test
    public void testGetValue(){
        double value = frequencyModulator.getValue();
        assertEquals(frequencyModulator.getValue(), value);
    }

    @org.junit.Test
    public void testSetValue(){
        frequencyModulator.setValue(20.0);
        double value = frequencyModulator.getValue();
        assertEquals(frequencyModulator.getValue(), value);
    }
    @org.junit.Test
    public void testActivate(){
        frequencyModulator.activate();
        assertTrue(frequencyModulator.isActivated());
    }

    @org.junit.Test
    public void testDeactivate(){
        frequencyModulator.deactivate();
        assertFalse(frequencyModulator.isActivated());
    }
}
