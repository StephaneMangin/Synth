package org.istic.synthlab.core;


import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import javafx.util.Pair;
import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.io.Input;
import org.istic.synthlab.core.modules.io.Output;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by paola on 02/02/16.
 */
public class ChannelTest {

    private IComponent component;
    private IInput input;
    private IOutput outPut;

    @org.junit.Before
    public void setUp() throws Exception {
        component = new Vcoa("TEST");
    }
    @org.junit.Test
    public void testConnect() throws Exception {
        input = new Input("In", component, new UnitInputPort("port"));
        outPut = new Output("Out", component, new UnitOutputPort("port"));

        Channel.connect(input, outPut);
        Pair<IInput, IOutput> pair =  new Pair<>(input, outPut);
        assertFalse(Channel.isEmpty());
        assertTrue(Channel.contains(pair));
    }

    @org.junit.Test
    public void testDisconnect() throws Exception {
        input = new Input("In", component, new UnitInputPort("port1"));
        outPut = new Output("Out", component, new UnitOutputPort("port2"));

        Channel.connect(input,outPut);
        Channel.disconnect(input, outPut);
        Pair<IInput, IOutput> pair =  new Pair<>(input, outPut);
        assertFalse(Channel.contains(pair));
    }
}