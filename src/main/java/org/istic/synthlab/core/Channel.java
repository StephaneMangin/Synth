package org.istic.synthlab.core;

import javafx.util.Pair;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

import java.util.HashSet;
import java.util.Set;

public class Channel {

    private static Set<Pair<IInput, IOutput>> connections = new HashSet<>();

    public static void connect(IInput in, IOutput out) {
        connections.add(new Pair<>(in, out));
    }

    public static void disconnect(IInput in , IOutput out) {
        connections.removeIf(pair -> pair.getKey() == in && pair.getValue() == out);
    }

    public static boolean isEmpty() {
        return connections.isEmpty();
    }

    public static int size() {
        return connections.size();
    }

    public static boolean contains(Pair<IInput, IOutput> pair) {
        return connections.contains(pair);
    }

}