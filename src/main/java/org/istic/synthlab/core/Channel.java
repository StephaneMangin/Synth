package org.istic.synthlab.core;

import javafx.util.Pair;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

import java.util.HashSet;
import java.util.Set;

/**
 * @author  Group1
 * The class Channel.
 */
public class Channel {

    private static Set<Pair<IInput, IOutput>> connections = new HashSet<>();

    /**
     * the method Connect that connects an input port with an output port
     *
     * @param in  the input port
     * @param out the output port
     */
    public static void connect(IInput in, IOutput out) {
        connections.add(new Pair<>(in, out));
    }

    /**
     * Disconnect the input with its corresponding output.
     *
     * @param in  the input port
     * @param out the output port
     */
    public static void disconnect(IInput in , IOutput out) {
        connections.removeIf(pair -> pair.getKey() == in && pair.getValue() == out);
    }

    /**
     * Method isEmpty that tests if we have connection in the channel
     *
     * @return the boolean
     */
    public static boolean isEmpty() {
        return connections.isEmpty();
    }

    /**
     * Method Size that returns the number of connections in the channel
     *
     * @return the the size : int
     */
    public static int size() {
        return connections.size();
    }

    /**
     * Method Contains .
     *
     * @param pair the pair input,ouput
     * @return the boolean
     */
    public static boolean contains(Pair<IInput, IOutput> pair) {
        return connections.contains(pair);
    }

}