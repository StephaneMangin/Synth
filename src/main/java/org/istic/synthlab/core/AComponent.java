package org.istic.synthlab.core;

import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.modulators.IModulator;
import org.istic.synthlab.core.modules.modulators.ModulatorType;
import org.istic.synthlab.core.services.ModulesFactory;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;

/**
 * The abstract class component.
 */
public abstract class AComponent implements IComponent {
    /**
     * The Name.
     */
    private String name;

    private IInput input;
    private IInput inputGate;
    private IInput inputFm;
    private IInput inputAm;
    private IOutput output;
    private IOutput outputGate;
    private IInput modInput;
    private IInput modInputGate;
    private IInput modInputFm;
    private IInput modInputAm;
    private IOutput modOutput;
    private IOutput modOutputGate;
    private IModulator inputModulator;
    private IModulator frequencyModulator;
    private IModulator amplitudeModulator;
    private IModulator inputGateModulator;
    private IModulator outputModulator;
    private IModulator outputGateModulator;

    /**
     * Instantiates a new  component.
     *
     * @param name the name
     */
    public AComponent(String name) {
        setName(name);

        // Define all modulators
        inputModulator = ModulesFactory.createModulator(this, ModulatorType.AMPLITUDE);
        frequencyModulator = ModulesFactory.createModulator(this, ModulatorType.FREQUENCY);
        amplitudeModulator = ModulesFactory.createModulator(this, ModulatorType.AMPLITUDE);
        inputGateModulator = ModulesFactory.createModulator(this, ModulatorType.AMPLITUDE);
        outputModulator = ModulesFactory.createModulator(this, ModulatorType.AMPLITUDE);
        outputGateModulator = ModulesFactory.createModulator(this, ModulatorType.AMPLITUDE);

        // Link modulators to inside modulators ports
        modInput = inputModulator.getInput();
        modInputGate = inputGateModulator.getInput();
        modInputFm = frequencyModulator.getInput();
        modInputAm = amplitudeModulator.getInput();
        modOutput = outputModulator.getOutput();
        modOutputGate = outputGateModulator.getOutput();

        // link modulator's ports to component's ports
        input = modInput;
        inputGate = modInputGate;
        inputFm = modInputFm;
        inputAm = modInputAm;
        output = modOutput;
        outputGate = modOutputGate;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the name
     */
    protected void setName(String name) {
        this.name = name;
    }

    /**
     * Rename.
     *
     * @param name the name
     */
    public void rename(String name) {
        setName(name);
    }

    @Override
    public void activate() {
    }

    @Override
    public void desactivate() {
    }

    @Override
    public void init() {
    }

    @Override
    public void run() {

    }

    public IInput getInput() {
        return this.input;
    }
    public IInput getInputGate() {
        return this.inputGate;
    }
    public IInput getFm() {
        return this.inputFm;
    }
    public IInput getAm() {
        return this.inputAm;
    }
    public IOutput getOutput() {
        return this.output;
    }
    public IOutput getOutputGate() {
        return this.outputGate;
    }

    public IModulator getInputModulator() {
        return inputModulator;
    }
    public IModulator getFmModulator() {
        return frequencyModulator;
    }
    public IModulator getAmModulator() {
        return amplitudeModulator;
    }
    public IModulator getInputGateModulator() {
        return inputGateModulator;
    }
    public IModulator getOutputModulator() {
        return outputModulator;
    }
    public IModulator getOutputGateModulator() {
        return outputGateModulator;
    }
}
