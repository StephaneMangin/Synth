package org.istic.synthlab.components;

import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.modulators.IModulator;

/**
 * @author  Group1
 *
 * The interface Component
 */
public interface IComponent {

    void activate();
    void deactivate();
    boolean isActivated();
    void init();
    void run();


    /**
     * Returns the input port
     *
     * @return input of the component
     */
    IInput getInput();

    /**
     * Returns the input gate port
     *
     * @return gate input of the component
     */
    IInput getInputGate();

    /**
     * Returns the frequency port
     *
     * @return FM input of the component
     */
    IInput getFm();

    /**
     * Returns the amplitude port
     *
     * @return AM input of the component
     */
    IInput getAm();

    /**
     * Returns the output port
     *
     * @return output of the component
     */
    IOutput getOutput();

    /**
     * Returns the output gate port
     *
     * @return output of the component
     */
    IOutput getOutputGate();

    /**
     * Returns the input's modulator
     *
     * @return IModulator
     */
    IModulator getInputByPass();

    /**
     * Returns the frequency's modulator
     *
     * @return IModulator
     */
    IModulator getFmModulator();

    /**
     * Returns the amplitude's modulator
     *
     * @return IModulator
     */
    IModulator getAmModulator();

    /**
     * Returns the output's modulator
     *
     * @return IModulator
     */
    IModulator getOutputModulator();

    /**
     * Set the id of this current component
     *
     * @param numInstance
     */
    void setId(int numInstance);

    /**
     * Set the componant name
     *
     * @param name
     */
    void setName(String name);

    /**
     * Returns componant name
     *
     * @return String
     */
    String getName();

    /**
     * Returns componant identifier
     *
     * @return int
     */
    int getId();
}
