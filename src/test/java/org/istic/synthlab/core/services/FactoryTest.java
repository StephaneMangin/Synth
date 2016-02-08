package org.istic.synthlab.core.services;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import junit.framework.TestCase;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.lineOuts.LineType;
import org.istic.synthlab.core.modules.oscillators.OscillatorType;
import org.istic.synthlab.core.modules.oscillators.SineOscillator;
import org.istic.synthlab.core.modules.oscillators.SquareOscillator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * The type Modules factory test.
 */
@RunWith(MockitoJUnitRunner.class)
public class FactoryTest extends TestCase {

    private Vcoa vcoa;

    @org.junit.Before
    public void setUp() throws Exception {
        vcoa = new Vcoa("VCOA");
    }

    /**
     * Testing method createInput(UnitInputPort unitInputPort).
     *
     */
    @Test
    public void createInputTest()  {
        UnitInputPort Port= new UnitInputPort("in");
        IInput inputPort= Factory.createInput("In", vcoa,Port);
        UnitInputPort input =  inputPort.getUnitInputPort();
        assertEquals(Port,input);
    }

    /**
     * Testing method createOutput(UnitOutputPort unitOutputPort)
     */
    @Test
    public void createOutputTest() {
        UnitOutputPort Port= new UnitOutputPort("out");
        IOutput outputPort= Factory.createOutput("Out", vcoa,Port);
        UnitOutputPort output =  outputPort.getUnitOutputPort();
        assertEquals(Port,output);
    }

    /**
     * Testing method createOscillator(IComponent component, OscillatorType type)
     */
    @Test
    public void createOscillatorTest() {
        SineOscillator sineOscillator = (SineOscillator) Factory.createOscillator(vcoa, OscillatorType.SINE);
        Vcoa component = (Vcoa) sineOscillator.getComponent();
        assertEquals(vcoa,component);
    }

    /**
     * Testing method createSquare(IComponent component, OscillatorType type)
     */
    @Test
    public void createSquareTest()  {
        SquareOscillator squareOscillator = (SquareOscillator) Factory.createOscillator(vcoa, OscillatorType.SQUARE);
        Vcoa component = (Vcoa) squareOscillator.getComponent();
        assertEquals(vcoa, component);
    }

    /**
     * Testing method createLineOut(IComponent component, LineType type)
     *
     */
    @Test
    public void createLineOutTest()  {
        Factory.createLineOut(vcoa, LineType.OUT);
        assertTrue(Register.mappingInput.containsKey(vcoa));
        assertTrue(Register.mappingGenerator.containsKey(vcoa));
    }

    @org.junit.After
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
