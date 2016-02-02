package org.istic.synthlab.core;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Channel {

    private static Set<Pair<IInput, IOutput>> connections = new HashSet<>();

    public static void connect(IInput in, IOutput out) {
        connections.add(new Pair<>(in, out));
    }

    public static void disconnect(IInput in , IOutput out) {
        connections.removeIf(pair -> pair.getKey() == in && pair.getValue() == out);
    }
}