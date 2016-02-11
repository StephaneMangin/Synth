package org.istic.synthlab.core;

import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.modulators.IModulator;
import org.istic.synthlab.core.modules.modulators.ModulatorType;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

import java.lang.reflect.ParameterizedType;

/**
 * The abstract class component.
 *
 * This class abstracts port accesses.

 *
 * 'Abstract components' abstract representation
 * ---------------------------------------------
 *
 *            External view (public access to inputs and outputs)
 * VIRTUAL   +-----------------------------------------------------------------------------------------------+  VIRTUAL
 * PORTS     |                  Internal view (protected access to sources/sinks ports                       |  PORTS
 *           |     IModulator   +-------------------------------------------------------+    IModulator      |
 *         +-+-+   +--------+   |                 Module view                           |    +---------+   +-+-+
 *   input |   +---> Bypass +---> source+------+  +-----------------------+    +-->sink +----> Amp     +--->   |output
 *         +-+-+   +--------+   |              +-->in1                    |    |        |    | Mod     |   +-+-+
 *           |                  |                 |                   out1+----+        |    | Linear  |     |
 *           |     +--------+   |             +--->in2 Modules (jsyn)     |             |    +---------+     |
 *         +-+-+   |Freq    |   |             |   |                       |             |                    |
 *      fm |   +--->Mod     +---> sourceFm+-+ |   |                       |             |                    |
 *         +-+-+   |Exp     |   |           | |   +-----------------------+             |                    |
 *           |     +--------+   |           | |                                         |                    |
 *           |     +--------+   |           | +------------------+                      |                    |
 *         +-+-+   |Amp     |   |           |                    |                      |                    |
 *      am |   +--->Mod     +---> SourceAm  |     IModulator     |                      |                    |
 *         +-+-+   |Linear  |   |           |     +---------+    |                      |                    |
 *           |     +--------+   |           |     |Freq     |    |                      |                    |
 *           |                  |           +> --->Mod      +----+                      |     IModulator     |
 *         +-+-+   +--------+   |                 |Linear   |                           |     +---------+  +-+-+
 *   gateIn|   +---> Bypass +---> SourceGate      +---------+                  sinkGate +-----> Bypass  +-->   |gateOut
 *         +-+-+   +--------+   |                                                       |     +---------+  +-+-+
 *           |                  +-------------------------------------------------------+                    |
 *           |                                                                                               |
 *           +-----------------------------------------------------------------------------------------------+
 *                                                                                    Made with : http://asciiflow.com/
 * Abstraction by getter (see code):
 *  Component's inputs linked to IModulator's inputs by getter
 *  Component's outputs linked to IModulator's outputs by getter
 *  Component's sources linked to IModulator's outputs by getter
 *  Component's sinks linked to IModulator's inputs by getter
 *
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 *
 */
public abstract class AbstractComponent implements IComponent {

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
    public AbstractComponent(String name) {
        setName(name);

        // Define all modulators
        inputModulator = Factory.createModulator(
                "modIn", this,
                ModulatorType.AMPLITUDE,
                PotentiometerType.LINEAR);
        frequencyModulator = Factory.createModulator(
                "modFreq", this,
                ModulatorType.FREQUENCY,
                PotentiometerType.EXPONENTIAL);
        amplitudeModulator = Factory.createModulator(
                "modAmp", this,
                ModulatorType.AMPLITUDE,
                PotentiometerType.LINEAR);
        inputGateModulator = Factory.createModulator(
                "modInGate", this,
                ModulatorType.AMPLITUDE,
                PotentiometerType.LINEAR);
        outputModulator = Factory.createModulator(
                "modOut", this,
                ModulatorType.AMPLITUDE,
                PotentiometerType.LINEAR);
        outputGateModulator = Factory.createModulator(
                "modOutGate", this,
                ModulatorType.AMPLITUDE,
                PotentiometerType.LINEAR);
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
     * @return input of the component
     */
    public IInput getInput() {
        return inputModulator.getInput();
    }

    /**
     * Returns the input for external connexions
     * @return gate input of the component
     */
    public IInput getInputGate() {
        return inputGateModulator.getInput();
    }

    /**
     * Returns the input for external connexions
     * @return FM input of the component
     */
    public IInput getFm() {
        return frequencyModulator.getInput();
    }

    /**
     * Returns the input for external connexions
     * @return AM input of the component
     */
    public IInput getAm() {
        return amplitudeModulator.getInput();
    }

    /**
     * Returns the output for external connexions
     * @return output of the component
     */
    public IOutput getOutput() {
        return outputModulator.getOutput();
    }

    /**
     * Returns the output for external connexions
     * @return gate output of the component
     */
    protected IOutput getOutputGate() {
        return outputGateModulator.getOutput();
    }

    /**
     * Returns the input for internal connexions
     * @return intern output of the component
     */
    protected IOutput getSource() {
        return inputModulator.getOutput();
    }

    /**
     * Returns the input for internal connexions
     * @return intern gate output of the component
     */
    protected IOutput getSourceGate() {
        return inputGateModulator.getOutput();
    }

    /**
     * Returns the input for internal connexions
     * @return intern FM output of the component
     */
    protected IOutput getSourceFm() {
        return frequencyModulator.getOutput();
    }

    /**
     * Returns the input for internal connexions
     * @return intern AM output of the component
     */
    protected IOutput getSourceAm() {
        return amplitudeModulator.getOutput();
    }

    /**
     * Returns the input for internal connexions
     * @return intern input of the component
     */
    protected IInput getSink() {
        return outputModulator.getInput();
    }

    /**
     * Returns the output for internal connexions
     * @return intern gate input of the component
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
