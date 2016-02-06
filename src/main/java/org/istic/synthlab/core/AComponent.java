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

    private IModulator inputModulator;
    private IModulator frequencyModulator;
    private IModulator amplitudeModulator;
    private IModulator inputGateModulator;
    private IModulator outputModulator;
    private IModulator outputGateModulator;

    /**
     * Instantiates a new component.
     *
     * @param name the name
     */
    public AComponent(String name) {
        setName(name);

        // Define all modulators
        inputModulator = ModulesFactory.createModulator("modIn", this, ModulatorType.AMPLITUDE);
        frequencyModulator = ModulesFactory.createModulator("modFreq", this, ModulatorType.FREQUENCY);
        amplitudeModulator = ModulesFactory.createModulator("modAmp", this, ModulatorType.AMPLITUDE);
        inputGateModulator = ModulesFactory.createModulator("modInGate", this, ModulatorType.AMPLITUDE);
        outputModulator = ModulesFactory.createModulator("modOut", this, ModulatorType.AMPLITUDE);
        outputGateModulator = ModulesFactory.createModulator("modOutGate", this, ModulatorType.AMPLITUDE);
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

    /**
     * Returns the input for external connexions
     * @return
     */
    public IInput getInput() {
        return inputModulator.getInput();
    }

    /**
     * Returns the input for external connexions
     * @return
     */
    public IInput getInputGate() {
        return inputGateModulator.getInput();
    }

    /**
     * Returns the input for external connexions
     * @return
     */
    public IInput getFm() {
        return frequencyModulator.getInput();
    }

    /**
     * Returns the input for external connexions
     * @return
     */
    public IInput getAm() {
        return amplitudeModulator.getInput();
    }

    /**
     * Returns the output for external connexions
     * @return
     */
    public IOutput getOutput() {
        return outputModulator.getOutput();
    }

    /**
     * Returns the output for external connexions
     * @return
     */
    protected IOutput getOutputGate() {
        return outputGateModulator.getOutput();
    }

    /**
     * Returns the input for internal connexions
     * @return
     */
    protected IOutput getSource() {
        return inputModulator.getOutput();
    }

    /**
     * Returns the input for internal connexions
     * @return
     */
    protected IOutput getSourceGate() {
        return inputGateModulator.getOutput();
    }

    /**
     * Returns the input for internal connexions
     * @return
     */
    protected IOutput getSourceFm() {
        return frequencyModulator.getOutput();
    }

    /**
     * Returns the input for internal connexions
     * @return
     */
    protected IOutput getSourceAm() {
        return amplitudeModulator.getOutput();
    }

    /**
     * Returns the output for internal connexions
     * @return
     */
    protected IInput getSink() {
        return outputModulator.getInput();
    }

    /**
     * Returns the output for internal connexions
     * @return
     */
    protected IInput getSinkGate() {
        return outputGateModulator.getInput();
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

    @Override
    public String toString() {
        return this.getName() + "<" + this.hashCode() + ">";
    }
}
