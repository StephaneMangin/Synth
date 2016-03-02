package org.istic.synthlab.core.modules.mix;

import com.jsyn.unitgen.Add;
import com.jsyn.unitgen.PassThrough;
import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;

import java.util.List;

/**
 * Class that define a mixer that able to mix multiple signals
 * from different modules and send the mixture on an output port
 *
 * @author Dechaud John Marc johnmarcdechaud[at]gmail[dot]com on 2/12/16.
 * */
public class Mix implements IMix {

    private PassThrough passThroughIn1;
    private PassThrough passThroughIn2;
    private PassThrough passThroughIn3;
    private PassThrough passThroughIn4;
    private PassThrough passThroughOut;

    private Add addInput12;
    private Add addInput34;
    private Add totalAddInput;

    private IInput input1;
    private IInput input2;
    private IInput input3;
    private IInput input4;

    private IOutput output;

    public Mix(IComponent component) {

        // Instantiate the PassThrough for the inputs ports
        passThroughIn1 = new PassThrough();
        passThroughIn2 = new PassThrough();
        passThroughIn3 = new PassThrough();
        passThroughIn4 = new PassThrough();
        passThroughOut = new PassThrough();

        // Instantiate the adds class to perform a signed addition on its two inputs
        addInput12 = new Add();
        addInput34 = new Add();
        totalAddInput = new Add();

        // Create the inputs by the Factory method
        input1 = Factory.createInput("In1", component, this.passThroughIn1.input);
        input2 = Factory.createInput("In2", component, this.passThroughIn2.input);
        input3 = Factory.createInput("In3", component, this.passThroughIn3.input);
        input4 = Factory.createInput("In4", component, this.passThroughIn4.input);

        // Create the output port by the Factory method
        output = Factory.createOutput("Out", component, this.passThroughOut.output);

        // Declare the generator to the register
        Register.declare(component, addInput12);
        Register.declare(component, addInput34);
        Register.declare(component, totalAddInput);

        // Link different ports
        addInput12.inputA.connect(this.passThroughIn1.output);
        addInput12.inputB.connect(this.passThroughIn2.output);
        addInput34.inputA.connect(this.passThroughIn3.output);
        addInput34.inputB.connect(this.passThroughIn4.output);
        totalAddInput.inputA.connect(this.addInput12.output);
        totalAddInput.inputB.connect(this.addInput34.output);

        totalAddInput.output.connect(this.passThroughOut.input);
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
}
