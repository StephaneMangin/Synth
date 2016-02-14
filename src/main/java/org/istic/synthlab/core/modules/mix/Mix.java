package org.istic.synthlab.core.modules.mix;

import com.jsyn.unitgen.Add;
import com.jsyn.unitgen.PassThrough;
import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.modulators.IModulator;
import org.istic.synthlab.core.modules.modulators.ModulatorType;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dechaud John Marc johnmarcdechaud[at]gmail[dot]com on 2/12/16.
 */

/**
 * Class that define a mixer that able to mix multiple signals
 * from different modules and send the mixture on an output port
 * */
public class Mix implements IMix {

    // Variable declaration
    private PassThrough passThroughIn1;
    private PassThrough passThroughIn2;
    private PassThrough passThroughIn3;
    private PassThrough passThroughIn4;
    private PassThrough passThroughOut;

    private IModulator gainInput1;
    private IModulator gainInput2;
    private IModulator gainInput3;
    private IModulator gainInput4;

    private List<IInput> inputList;

    private Add addInput12;
    private Add addInput34;
    private Add totalAddInput;

    private IInput input1;
    private IInput input2;
    private IInput input3;
    private IInput input4;

    private IOutput output;

    public Mix(IComponent component) {

        // Instantiate the input list
        this.inputList = new ArrayList<>();

        // Instantiate the PassThrough for the inputs ports
        this.passThroughIn1 = new PassThrough();
        this.passThroughIn2 = new PassThrough();
        this.passThroughIn3 = new PassThrough();
        this.passThroughIn4 = new PassThrough();
        this.passThroughOut = new PassThrough();

        // Instantiate the adds class to perform a signed addition on its two inputs
        this.addInput12 = new Add();
        this.addInput34 = new Add();
        this.totalAddInput = new Add();

        // Instantiate the gain modulator
        this.gainInput1 = Factory.createModulator("Gain1", component, ModulatorType.GAIN, PotentiometerType.LINEAR);
        this.gainInput2 = Factory.createModulator("Gain2", component, ModulatorType.GAIN, PotentiometerType.LINEAR);
        this.gainInput3 = Factory.createModulator("Gain3", component, ModulatorType.GAIN, PotentiometerType.LINEAR);
        this.gainInput4 = Factory.createModulator("Gain4", component, ModulatorType.GAIN, PotentiometerType.LINEAR);

        // Create the inputs by the Factory method
        this.input1 = Factory.createInput("In1", component, this.passThroughIn1.input);
        this.input2 = Factory.createInput("In2", component, this.passThroughIn2.input);
        this.input3 = Factory.createInput("In3", component, this.passThroughIn3.input);
        this.input4 = Factory.createInput("In4", component, this.passThroughIn4.input);

        // Create the output port by the Factory method
        this.output = Factory.createOutput("Out", component, this.passThroughOut.output);

        // Declare the generator to the register
        Register.declare(component, addInput12);
        Register.declare(component, addInput34);
        Register.declare(component, totalAddInput);

        // Link different ports
        this.addInput12.inputA.connect(this.passThroughIn1.output);
        this.addInput12.inputB.connect(this.passThroughIn2.output);
        this.addInput34.inputA.connect(this.passThroughIn3.output);
        this.addInput34.inputB.connect(this.passThroughIn4.output);
        this.totalAddInput.inputA.connect(this.addInput12.output);
        this.totalAddInput.inputB.connect(this.addInput34.output);

        this.totalAddInput.output.connect(this.passThroughOut.input);

        // Add the input int the list
        this.inputList.add(input1);
        this.inputList.add(input2);
        this.inputList.add(input3);
        this.inputList.add(input4);
    }

    /**
     * Return the input number one of the mixer
     *
     * @return IInput
     */
    @Override
    public IInput getInput1() {
        return this.input1;
    }

    /**
     * Return the input number two of the mixer
     *
     * @return IInput
     */
    @Override
    public IInput getInput2() {
        return this.input2;
    }

    /**
     * Return the input number three of the mixer
     *
     * @return IInput
     */
    @Override
    public IInput getInput3() {
        return this.input3;
    }

    /**
     * Return the input number four of the mixer
     *
     * @return IInput
     */
    @Override
    public IInput getInput4() {
        return this.input4;
    }

    /**
     * Return the output of the mixer
     *
     * @return IOutput
     */
    @Override
    public IOutput getOutput() {
        return this.output;
    }

    /**
     * Return the ieme input port
     *
     * @param i
     * @return IInput
     */
    @Override
    public IInput getInputPortByIndex(int i) {
        return this.inputList.get(i);
    }

    /**
     * Add a new input port
     *
     * @param input
     */
    @Override
    public void addInputPort(IInput input) {
        this.inputList.add(input);
    }

    /**
     * Activate the inputs ports
     */
    @Override
    public void activate() {
        this.passThroughIn1.setEnabled(true);
        this.passThroughIn2.setEnabled(true);
        this.passThroughIn3.setEnabled(true);
        this.passThroughIn4.setEnabled(true);
    }

    /**
     * Deactivate the inputs ports
     */
    @Override
    public void deactivate() {
        this.passThroughIn1.setEnabled(false);
        this.passThroughIn2.setEnabled(false);
        this.passThroughIn3.setEnabled(false);
        this.passThroughIn4.setEnabled(false);
    }

    /**
     * Get the current state of the inputs ports.
     *
     * @return boolean
     */
    @Override
    public boolean isActivated() {
        return this.passThroughIn1.isEnabled()
                || this.passThroughIn2.isEnabled()
                || this.passThroughIn3.isEnabled()
                || this.passThroughIn4.isEnabled();
    }

    @Override
    public Potentiometer getAmplitudePotentiometerInput1() {
        return (Potentiometer) this.gainInput1;
    }

    @Override
    public Potentiometer getAmplitudePotentiometerInput2() {
        return (Potentiometer) this.gainInput2;
    }

    @Override
    public Potentiometer getAmplitudePotentiometerInput3() {
        return (Potentiometer) this.gainInput3;
    }

    @Override
    public Potentiometer getAmplitudePotentiometerInput4() {
        return (Potentiometer) this.gainInput4;
    }

}
