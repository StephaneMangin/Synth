package org.istic.synthlab.core.modules.sequencer;

import com.jsyn.ports.UnitGatePort;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.Multiply;
import com.jsyn.unitgen.UnitGate;
import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;

/**
 * @author Dechaud John Marc johnmarcdechaud[at]gmail[dot]com on 2/22/16.
 */
public class Sequencer extends UnitGate implements ISequencer {

    // A UnitGatePort from JSYN
    private UnitGatePort inputGate;
    UnitInputPort p;
    // The iInput port for the sequencer
    private IInput iInput;
    // The iOutput port for the sequencer
    private IOutput iOutput;
    // The out signal
    private Multiply outputMultiply;
    // Current Step
    protected int currentStep;
    // A table of the different step (8 step)
    private double[] steps;
    // Producer of voltage
    private VoltageProducer voltageProducer;


    public Sequencer(IComponent component) {
        this.outputMultiply = new Multiply();
        Register.declare(component, this.outputMultiply);

        this.inputGate = new UnitGatePort("InputGate");
        this.iInput = Factory.createInput("InputGate", component, this.inputGate);
        this.iOutput = Factory.createOutput("OutputPort", component, this.outputMultiply.output);

        this.voltageProducer = new VoltageProducer();
        this.voltageProducer.input.set(1.0/5);
        this.voltageProducer.output.connect(this.outputMultiply.inputB);

        this.currentStep = 1;
        this.steps = new double[8];
        for (int i = 0; i < this.steps.length; i++){
            this.steps[i] = 0;
        }
    }

    @Override
    public void generate(int start, int limit) {
        double[] inputs = inputGate.getValues();
        double[] outputs = output.getValues();

        for (int i = start; i < limit; i++) {
            if(inputGate.checkGate(i))
                nextStep();
            outputs[i] = inputs[i];
        }
    }

    /**
     * Get the current step of the sequencer
     *
     * @return int
     */
    @Override
    public int getCurrentStep() {
        return this.currentStep;
    }

    /**
     * Get the step value of the sequencer
     * @param step
     * @return double
     */
    @Override
    public double getStepValue(int step) {
        return this.steps[step - 1];
    }

    /**
     * Get the current step value of the sequencer
     *
     * @param step
     * @param value
     */
    @Override
    public void setStepValue(int step, double value) {
        this.steps[step - 1] = value;
        if (this.currentStep == step)
            this.outputMultiply.inputA.set(value);
    }

    /**
     * Reinitialize the step of the sequencer
     */
    @Override
    public void resetStep() {
        this.currentStep = 1;
    }

    /**
     * Switch to the next step
     */
    @Override
    public void nextStep() {
        if (this.currentStep == 8)
            this.currentStep = 1;
        else
            this.currentStep++;
        this.outputMultiply.inputA.set(steps[currentStep - 1]);
    }

    /**
     * Get the iInput port
     *
     * @return IInput
     */
    @Override
    public IInput getInputGatePort() {
        return this.iInput;
    }

    /**
     * Get the iOutput port
     *
     * @return IInput
     */
    @Override
    public IOutput getOutputPort() {
        return this.iOutput;
    }

}
