package org.istic.synthlab.core.utils.parametrization;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.SineOscillator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by blacknight on 15/02/16.
 */
public class PotentiometerTest {

    Potentiometer potard;
    private UnitInputPort port;
    private double doublePrecision = 0.00000001D;
    private double initialValue = 440.0D;
    private double initialValueExp = 0.000436148424308D;
    private double minValue = 20.0D;
    private double maxValue = 20000.0D;
    private double minValueExp = 0.0D;
    private double maxValueExp = 1.0D;
    private PotentiometerType type = PotentiometerType.EXPONENTIAL;

    @Before
    public void setUp() throws Exception {
        //initialValue = Math.pow(2,(maxValue-minValue)*initialValueExp) + minValue;
        initialValueExp = Math.log(initialValue)/Math.log(maxValue);
        System.out.println(initialValueExp);
        port = Mockito.mock(UnitInputPort.class);
        when(port.getMaximum()).thenReturn(maxValue);
        when(port.getMinimum()).thenReturn(minValue);
        when(port.getDefault()).thenReturn(initialValue);
        potard = new Potentiometer("Mon potard", port, type, maxValue, minValue, initialValue);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSetValue() throws Exception {
        double oracle = 1.0D;
        potard.setValue(oracle);
        assertEquals(oracle, potard.getValue(), doublePrecision);
    }

    @Test
    public void testGetValue() throws Exception {
        assertEquals(initialValueExp, potard.getValue(), doublePrecision);
    }

    @Test
    public void testGetMax() throws Exception {
        assertEquals(maxValue, potard.getMax(), doublePrecision);
    }

    @Test
    public void testSetMax() throws Exception {
        double oracle = 0.8D;
        when(port.getMaximum()).thenReturn(oracle);
        potard.setMax(oracle);
        assertEquals(oracle, potard.getMax(), doublePrecision);
    }

    @Test
    public void testGetMin() throws Exception {
        assertEquals(minValue, potard.getMin(), doublePrecision);
    }

    @Test
    public void testSetMin() throws Exception {
        double oracle = 0.1D;
        when(port.getMinimum()).thenReturn(oracle);
        potard.setMin(oracle);
        assertEquals(oracle, potard.getMin(), doublePrecision);
    }

    @Test
    public void testCalculateStep() throws Exception {
        //
    }

    @Test
    public void testGetType() throws Exception {
        assertEquals(type, potard.getType());
    }

    @Test
    public void testGetRawValue() throws Exception {
        assertEquals(initialValue, potard.getRawValue(), doublePrecision);
    }

    @Test
    public void testSetRawValue() throws Exception {
        double oracle = 200.0D;
        when(port.getValue()).thenReturn(oracle);
        potard.setRawValue(oracle);
        assertEquals(oracle, potard.getRawValue(), doublePrecision);
    }
}