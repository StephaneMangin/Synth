package org.istic.synthlab.core;


import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import javafx.util.Pair;
import junit.framework.TestCase;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.io.Input;
import org.istic.synthlab.core.modules.io.Output;

/**
 * Created by paola on 02/02/16.
 */
public class ChannelTest extends TestCase {

    private IComponent component;
    private Channel channel;
    private IInput input;
    private IOutput outPut;

    @org.junit.Before
    public void setUp() throws Exception {
        channel = new Channel();
        component = new Vcoa("TEST");
    }
    @org.junit.Test
    public void testConnect() throws Exception {
        input = new Input("In", component, new UnitInputPort("port"));
        outPut = new Output("Out", component, new UnitOutputPort("port"));

        channel.connect(input, outPut);
        Pair<IInput, IOutput> pair =  new Pair<>(input, outPut);
        assertFalse(channel.isEmpty());
        assertTrue(channel.contains(pair));
    }

    @org.junit.Test
    public void testDisconnect() throws Exception {
        input = new Input("In", component, new UnitInputPort("port1"));
        outPut = new Output("Out", component, new UnitOutputPort("port2"));

        channel.connect(input,outPut);
        channel.disconnect(input, outPut);
        Pair<IInput, IOutput> pair =  new Pair<>(input, outPut);
        assertFalse(channel.contains(pair));
    }
}