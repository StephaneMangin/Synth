package org.istic.synthlab.core.services;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;
import org.istic.synthlab.components.out.Out;
import org.istic.synthlab.core.Channel;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.lineOuts.ILineOut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class pretends to help I/O associations management.
 * Domain object with JSyn object relations also.
 *
 * It can be viewed as an active register that manage JSin equivalent semantics.
 *
 * @author Stéphane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 *
 */
public class Register {

    // Keep an eye on components generators
    protected static Map<IComponent, List<UnitGenerator>> mappingGenerator = new HashMap<>();
    // Same for Inputs
    protected static Map<IComponent, Map<IInput, UnitInputPort>> mappingInput = new HashMap<>();
    // Same for Outputs
    protected static Map<IComponent, Map<IOutput, UnitOutputPort>> mappingOutput = new HashMap<>();
    // The most important one, inputs/outputs associations
    protected static Map<IInput, IOutput> associations = new HashMap<>();

    /**
     * Declare an dual association for a components and a generator
     *
     * @param component IComponents
     * @param unitGenerator UnitGenerator
     */
    public static void declare(IComponent component, UnitGenerator unitGenerator) {
        if (!mappingGenerator.containsKey(component)) {
            mappingGenerator.put(component, new ArrayList<>());
        }
        mappingGenerator.get(component).add(unitGenerator);
        ModulesFactory.createSynthesizer().add(unitGenerator);
    }

    /**
     * Declare a triple assocation for a component and inputs.
     *
     * @param component IComponent
     * @param in IInput
     * @param unitIn UnitInputPort
     */
    public static void declare(IComponent component, IInput in, UnitInputPort unitIn) {
        Map<IInput, UnitInputPort> assoc = new HashMap<>();
        assoc.put(in, unitIn);
        if (!mappingInput.containsKey(component)) {
            mappingInput.put(component, assoc);
        } else {
            mappingInput.get(component).putAll(assoc);
        }
    }

    /**
     * Declare a triple assocation for a component and outputs.
     *
     * @param component IComponent
     * @param out IOutput
     * @param unitOut UnitOutputPort
     */
    public static void declare(IComponent component, IOutput out, UnitOutputPort unitOut) {
        Map<IOutput, UnitOutputPort> assoc = new HashMap<>();
        assoc.put(out, unitOut);
        if (!mappingOutput.containsKey(component)) {
            mappingOutput.put(component, assoc);
        } else {
            mappingOutput.get(component).putAll(assoc);
        }
    }

    /**
     * Connect two line together.
     * Lets it follow to JSyn synthetizer.
     *
     * @param in IInput
     * @param out IOutput
     *
     * @see Channel::connect(in, out)
     */
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
        associations.put(in, out);
    }

    /**
     * Disables an association from an input.
     *
     * @param in IInput
     *
     * @see Channel
     * @see UnitInputPort
     */
    public static void disconnect(IInput in) {
        UnitInputPort unitIn = retrieve(in);
        IOutput out = associations.get(in);
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

    /**
     * Disables an association from an output.
     *
     * @param out IOutput
     *
     * @see Channel
     * @see UnitOutputPort
     */
    public static void disconnect(IOutput out) {
        IInput in = null;
        for (IInput in1: associations.keySet()) {
            if (out == associations.get(in1)) {
                in = in1;
            }
        }
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

    /**
     * Get the related unitport from an input.
     *
     * @param in IInput
     * @return UnitInputPort or null
     */
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

    /**
     * Get the related unitport from an output.
     *
     * @param out IOutput
     * @return UnitOutputPort or null
     */
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

    /**
     * Get the component for a given input.
     *
     * @param in IInput
     * @return IComponent or null
     */
    public static IComponent getComponent(IInput in) {
        for (IComponent component: mappingInput.keySet()) {
            for (IInput input: mappingInput.get(component).keySet()) {
                if (in == input) {
                    return component;
                }
            }
        }
        return null;
    }

    /**
     * Get the component for a given output.
     *
     * @param out IOutput
     * @return IComponent or null
     */
    public static IComponent getComponent(IOutput out) {
        for (IComponent component: mappingOutput.keySet()) {
            for (IOutput output: mappingOutput.get(component).keySet()) {
                if (out == output) {
                    return component;
                }
            }
        }
        return null;
    }


    // FIXME
    // #SigneScrumMaster
    public static void uglyPatchWork(){
        for (IComponent component : mappingGenerator.keySet()){
            if (component instanceof Out){
                Out ugly = (Out) component;
                ugly.getLineOut().start();
            }
        }
    }
}
