package org.istic.synthlab.core;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;

import java.util.HashMap;

/**
 *
 */
public class IOMappingService {
    private static HashMap<OutputAdapter, UnitOutputPort> mappingOutput = new HashMap<>();
    private static HashMap<InputAdapter, UnitInputPort> mappingInput = new HashMap<>();

    static void connect(IInput input, IOutput output) {
        mappingInput.get(input).connect(mappingOutput.get(output));
    }

    static IInput getAdapter(UnitInputPort unit){
        InputAdapter in = new InputAdapter(unit);
        mappingInput.put(in, unit);
        return in;
    }

    public static IOutput getAdapter(UnitOutputPort unit) {
        OutputAdapter out = new OutputAdapter(unit);
        mappingOutput.put(out, unit);
        return out;
    }

    static void disconnect(IInput input, IOutput output) {
        mappingInput.get(input).disconnect(mappingOutput.get(output));
    }

    static UnitInputPort getInput(InputAdapter input) {
        return mappingInput.get(input);
    }
    static UnitOutputPort getInput(OutputAdapter output) {
        return mappingOutput.get(output);
    }
}
