package org.istic.synthlab.core.services;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;
import org.istic.synthlab.components.out.Out;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * IOMapping Tester.
 *
 * @author <John Marc Dechaud>
 * @since <pre>Feb 4, 2016</pre>
 * @version 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class RegisterTest {

    private Vcoa vcoa;
    private Out out;
    private IInput input1;
    private IInput input2;
    private IOutput output1;
    private IOutput output2;
    private UnitGenerator unitGenerator;
    private UnitInputPort unitInputPort2;
    private UnitInputPort unitInputPort1;
    private UnitOutputPort unitOutputPort1;
    private UnitOutputPort unitOutputPort2;

    @Before
    public void setUp() throws Exception {
        this.vcoa = Mockito.mock(Vcoa.class);
        this.out = Mockito.mock(Out.class);
        this.unitGenerator = Mockito.mock(UnitGenerator.class);
        this.unitInputPort2 = Mockito.mock(UnitInputPort.class);
        this.unitInputPort1 = Mockito.mock(UnitInputPort.class);
        this.unitOutputPort1 = Mockito.mock(UnitOutputPort.class);
        this.unitOutputPort2 = Mockito.mock(UnitOutputPort.class);
        this.input2 = Mockito.mock(IInput.class);
        this.input1 = Mockito.mock(IInput.class);
        this.output2 = Mockito.mock(IOutput.class);
        this.output1 = Mockito.mock(IOutput.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     *
     * Method: declare(IComponent component, UnitGenerator unitOscillator)
     *
     */
    @Test
    public void testDeclareForComponentUnitGenerator() throws Exception {
        Register.declare(this.vcoa, this.unitGenerator);
        Assert.assertTrue(Register.mappingGenerator.containsKey(this.vcoa));
        Register.mappingGenerator.containsValue(this.unitGenerator);
        Assert.assertTrue(Register.mappingGenerator.get(this.vcoa).contains(this.unitGenerator));
    }

    /**
     *
     * Method: declare(IComponent component, IInput in, UnitInputPort unitIn)
     *
     */
    @Test
    public void testDeclareForComponentInUnitIn() throws Exception {
        Register.declare(this.vcoa, this.input1, this.unitInputPort2);
        Assert.assertTrue(Register.mappingInput.containsKey(this.vcoa));
        Assert.assertTrue(Register.mappingInput.get(this.vcoa).containsKey(this.input1));
        Assert.assertTrue(Register.mappingInput.get(this.vcoa).containsValue(unitInputPort2));
    }

    /**
     *
     * Method: declare(IComponent component, IOutput out, UnitOutputPort unitOut)
     *
     */
    @Test
    public void testDeclareForComponentOutUnitOut() throws Exception {
        Register.declare(this.vcoa, this.output1, this.unitOutputPort1);
        Mockito.when(this.vcoa.getOutput()).thenReturn(output1);
        Assert.assertTrue(Register.mappingOutput.get(this.vcoa).containsKey(this.vcoa.getOutput()));
        Assert.assertTrue(Register.mappingOutput.containsKey(this.vcoa));
        Assert.assertTrue(Register.mappingOutput.get(this.vcoa).containsValue(this.unitOutputPort1));
        Mockito.verify(this.vcoa).getOutput();
    }

    /**
     *
     * Method: connect(IInput in, IOutput out)
     *
     */
    @Test
    public void testConnect() throws Exception {
        Register.declare(this.vcoa, this.output2, this.unitOutputPort2);
        Register.declare(this.out, this.input1, this.unitInputPort1);
        Register.declare(this.out, this.output1, this.unitOutputPort1);
        Mockito.when(this.vcoa.getOutput()).thenReturn(output2);
        Register.connect(this.input1, output2);
        Assert.assertEquals(this.vcoa.getOutput(), Register.associations.get(this.input1));
        Mockito.verify(this.vcoa).getOutput();
    }

    /**
     *
     * Method: disconnect(IInput in)
     *
     */
    @Test
    public void testDisconnectIn() throws Exception {
        Register.declare(this.vcoa, this.output2, this.unitOutputPort2);
        Register.declare(this.vcoa, this.input2, this.unitInputPort2);
        Register.declare(this.out, this.input1, this.unitInputPort1);
        Register.declare(this.out, this.output1, this.unitOutputPort1);
        Mockito.when(this.vcoa.getOutput()).thenReturn(this.output2);
        Mockito.when(this.vcoa.getInput()).thenReturn(this.input2);
        Mockito.when(this.out.getInput()).thenReturn(this.input1);
        Register.connect(this.input1, this.output2);
        Assert.assertEquals(this.vcoa.getOutput(), Register.associations.get(this.input1));
        Register.disconnect(this.out.getInput());
        Assert.assertFalse(this.unitInputPort1.isConnected());
        //Assert.assertEquals(this.vcoa.getOutput(), Register.associations.get(this.input1));
        //Mockito.verify(this.vcoa).getOutput();
        //Mockito.verify(this.vcoa).getInput();
        //Mockito.verify(this.out).getInput();
    }

    /**
     *
     * Method: disconnect(IOutput out)
     *
     */
    @Test
    public void testDisconnectOut() throws Exception {
        Register.declare(this.vcoa, this.output2, this.unitOutputPort2);
        Register.declare(this.vcoa, this.input2, this.unitInputPort2);
        Register.declare(this.out, this.input1, this.unitInputPort1);
        Register.declare(this.out, this.output1, this.unitOutputPort1);
        Mockito.when(this.vcoa.getOutput()).thenReturn(this.output2);
        Mockito.when(this.vcoa.getInput()).thenReturn(this.input2);
        Mockito.when(this.out.getInput()).thenReturn(this.input1);
        Register.connect(this.input1, this.output2);
        Assert.assertEquals(this.vcoa.getOutput(), Register.associations.get(this.input1));
        Register.disconnect(this.vcoa.getOutput());
        Assert.assertFalse(this.unitOutputPort2.isConnected());
    }

    /**
     *
     * Method: retrieve(IInput in)
     *
     */
    @Test
    public void testRetrieveIn() throws Exception {
        Register.declare(this.vcoa, this.input1, this.unitInputPort2);
        Assert.assertEquals(this.unitInputPort2,Register.retrieve(this.input1));
    }

    /**
     *
     * Method: retrieve(IOutput out)
     *
     */
    @Test
    public void testRetrieveOut() throws Exception {
        Register.declare(this.vcoa, this.output1, this.unitOutputPort1);
        Assert.assertEquals(this.unitOutputPort1, Register.retrieve(this.output1));
    }

    /**
     *
     * Method: getComponent(IInput in)
     *
     */
    @Test
    public void testGetComponentIn() throws Exception {
        Register.declare(this.vcoa, this.input1, this.unitInputPort2);
        Assert.assertEquals(this.vcoa, Register.getComponent(this.input1));
    }

    /**
     *
     * Method: getComponent(IOutput out)
     *
     */
    @Test
    public void testGetComponentOut() throws Exception {
        Register.declare(this.vcoa, this.output1, this.unitOutputPort1);
        Mockito.when(this.vcoa.getOutput()).thenReturn(output1);
        Assert.assertEquals(this.vcoa, Register.getComponent(this.output1));
    }

}
