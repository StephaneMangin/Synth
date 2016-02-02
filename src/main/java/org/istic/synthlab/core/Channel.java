package org.istic.synthlab.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by stephane on 02/02/16.
 */
public class Channel {

    Map<IInput, IOutput> connections;

    Channel() {
        connections = new HashMap<IInput, IOutput>();
    }

    void connect(IInput in, IOutput out) {
        connections.put(in, out);
        in.connect(out);
    }

    void disconnect(IInput in , IOutput out) {
        connections.remove(in, out);
    }

}
