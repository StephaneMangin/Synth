package org.istic.synthlab.core.algorithms;

import junit.framework.TestCase;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.modules.algorithms.*;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
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
        frequencyModulator = new VcoaFrequencyModulator(vcoa);
    }

    @org.junit.Test
    public void testVcoaFrequencyModulator(){;
        assertNotNull(frequencyModulator.getInput());
        assertNotNull(frequencyModulator.getOutput());
        assertNotNull(frequencyModulator.getFrequency());
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
    public void testGetFrequency(){
        Potentiometer potentiometer= frequencyModulator.getFrequency();
        assertEquals(320.0, potentiometer.getValue());
        assertTrue(potentiometer.getMin()== 20.0);
        assertTrue(potentiometer.getMax()== 20000.0);
        assertTrue(potentiometer.getType() == PotentiometerType.EXPONENTIAL);
    }
}
