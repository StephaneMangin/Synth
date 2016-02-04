package org.istic.synthlab.core.modules.algorithms;

import org.istic.synthlab.core.AdapterFactory;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

/**
 *
 */
public class VcoaAlgorithmAdapter implements IVcoaAlgorithm {
    private IOutput output;
    private IInput input;
    private VcoaAlgorithm algorithm;

    public VcoaAlgorithmAdapter() {
        this.algorithm = new VcoaAlgorithm();
        this.output = AdapterFactory.createOutput(algorithm.output);
        this.input = AdapterFactory.createInput(algorithm.frequencyModulation);
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
