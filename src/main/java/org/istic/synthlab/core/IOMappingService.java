package org.istic.synthlab.core;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class IOMappingService {
    private static Map<IComponent, List<UnitGenerator>> mappingGenerator = new HashMap<>();
    private static Map<IComponent, Map<IInput, UnitInputPort>> mappingInput = new HashMap<>();
    private static Map<IComponent, Map<IOutput, UnitOutputPort>> mappingOutput = new HashMap<>();

    public static void declare(IComponent component, UnitGenerator unitGenerator) {
        if (!mappingGenerator.containsKey(component)) {
            mappingGenerator.put(component, new ArrayList<UnitGenerator>());
        }
        mappingGenerator.get(component).add(unitGenerator);
        AdapterFactory.createSynthesizer().add(unitGenerator);
    }

    public static void declare(IComponent component, IInput in, UnitInputPort unitIn) {
        Map<IInput, UnitInputPort> assoc = new HashMap<>();
        assoc.put(in, unitIn);
        if (!mappingInput.containsKey(component)) {
            mappingInput.put(component, assoc);
        } else {
            mappingInput.get(component).putAll(assoc);
        }
    }

    public static void declare(IComponent component, IOutput out, UnitOutputPort unitOut) {
        Map<IOutput, UnitOutputPort> assoc = new HashMap<>();
        assoc.put(out, unitOut);
        if (!mappingOutput.containsKey(component)) {
            mappingOutput.put(component, assoc);
        } else {
            mappingOutput.get(component).putAll(assoc);
        }
    }

    public static void connect(IInput in, IOutput out) {
        UnitInputPort unitIn = retrieve(in);
        UnitOutputPort unitOut = retrieve(out);
        if (unitIn == null) {
            throw new ExceptionInInitializerError("IInput has not been declared properly");
        }
        if (unitOut == null) {
            throw new ExceptionInInitializerError("IOutput has not been declared properly");
        }
        Channel.connect(in, out);
        unitOut.connect(unitIn);
    }

    public static void disconnect(IInput in, IOutput out) {
        UnitInputPort unitIn = retrieve(in);
        UnitOutputPort unitOut = retrieve(out);
        if (unitIn == null) {
            throw new ExceptionInInitializerError("IInput has not been declared properly");
        }
        if (unitOut == null) {
            throw new ExceptionInInitializerError("IOutput has not been declared properly");
        }
        Channel.disconnect(in, out);
        unitOut.disconnect(unitIn);
    }


    public static UnitInputPort retrieve(IInput in) {
        for (IComponent component: mappingInput.keySet()) {
            for (IInput input: mappingInput.get(component).keySet()) {
                if (in == input) {
                    return mappingInput.get(component).get(in);
                }
            }
        }
        return null;
    }

    public static UnitOutputPort retrieve(IOutput out) {
        for (IComponent component: mappingOutput.keySet()) {
            for (IOutput output: mappingOutput.get(component).keySet()) {
                if (out == output) {
                    return mappingOutput.get(component).get(out);
                }
            }
        }
        return null;
    }

}
