package org.istic.synthlab.core;


import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import javafx.util.Pair;
import junit.framework.TestCase;

/**
 * Created by paola on 02/02/16.
 */
public class ChannelTest extends TestCase {

    private Channel channel;
    private IInput input;
    private IOutput outPut;

    @org.junit.Before
    public void setUp() throws Exception {
        channel = new Channel();
    }
    @org.junit.Test
    public void testConnect() throws Exception {
        input = new InputAdapter(new UnitInputPort("port"));
        outPut = new OutputAdapter(new UnitOutputPort("port"));

        channel.connect(input, outPut);
        Pair<IInput, IOutput> pair =  new Pair<>(input, outPut);
        assertFalse(channel.getConnections().isEmpty());
        assertTrue(channel.getConnections().size() == 1);
        assertTrue(channel.getConnections().contains(pair));
    }

    @org.junit.Test
    public void testDisconnect() throws Exception {
        input = new InputAdapter(new UnitInputPort("port1"));
        outPut = new OutputAdapter(new UnitOutputPort("port2"));

        channel.connect(input,outPut);
        channel.disconnect(input, outPut);
        Pair<IInput, IOutput> pair =  new Pair<>(input, outPut);
        assertTrue(channel.getConnections().isEmpty());
        assertFalse(channel.getConnections().contains(pair));
    }
}