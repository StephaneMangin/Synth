package org.istic.synthlab.core.io;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import javafx.util.Pair;
import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.components.out.Out;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.Channel;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.io.Input;
import org.istic.synthlab.core.modules.io.Output;
import org.istic.synthlab.core.services.Register;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Module Input Tester.
 *
 * @author <Ngassam Noumi Paola> npaolita.[ât]yahoo.fr
 * @since <pre>Feb 10, 2016</pre>
 */
public class InputTest {

    private IInput input;
    private UnitInputPort unitInputPort;
    private UnitOutputPort unitOutputPort;
    private IComponent out;
    private IComponent vcoa;

    @Before
    public void setUp() throws Exception {
        // the component Out
        out = new Out("OUT");
        unitInputPort = new UnitInputPort("PortIn");
        // we create our input port of the component out
        input  = new Input("IN",out,unitInputPort);
    }

    @Test
    public void testInputConstruct(){
        // we test if the construction of the input port out is well designed
        assertNotNull(input.getComponent());
        assertEquals("IN", input.getName());
        assertEquals(Register.getComponent(input), out);
    }

    @Test
    public void testConnect(){
        vcoa = new Vcoa("VCOA");
        unitOutputPort = new UnitOutputPort("PortOut");
        IOutput output = new Output("OUT",vcoa,unitOutputPort);
        // we test if the input port of the out is well connected to the vcoa output port
        input.connect(output);
        assertTrue(Channel.contains(new Pair<>(input, output)));
    }
    @Test
    public void testDisConnect(){
        vcoa = new Vcoa("VCOA");
        unitOutputPort = new UnitOutputPort("PortOut");
        IOutput output = new Output("OUT",vcoa,unitOutputPort);
        //we connect first the 2 ports before disconnecting them
        input.connect(output);
        // we test if the input port of the out is well disconnected from the output vco
        input.disconnect();
        assertFalse(Channel.contains(new Pair<>(input, output)));
    }
}
