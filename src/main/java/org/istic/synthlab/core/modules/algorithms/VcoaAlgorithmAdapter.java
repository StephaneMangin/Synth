package org.istic.synthlab.core.modules.algorithms;

import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.services.ModulesFactory;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

/**
 *
 */
public class VcoaAlgorithmAdapter implements IVcoaAlgorithm {
    private IOutput output;
    private IInput input;
    private VcoaAlgorithm algorithm;

    public VcoaAlgorithmAdapter(IComponent component) {
        this.algorithm = new VcoaAlgorithm();
        this.output = ModulesFactory.createOutput(component, algorithm.output);
        this.input = ModulesFactory.createInput(component, algorithm.frequencyModulation);
    }

    @Override
    public IInput getInput() {
        return input;
    }

    @Override
    public IOutput getOutput() {
        return output;
    }

}
