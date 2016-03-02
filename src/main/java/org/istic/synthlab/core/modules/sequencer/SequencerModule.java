package org.istic.synthlab.core.modules.sequencer;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.Multiply;
import com.jsyn.unitgen.UnitGate;
import org.istic.synthlab.core.components.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.utils.functions.VoltageProducer;

/**
 * @author Dechaud John Marc johnmarcdechaud[at]gmail[dot]com on 2/22/16.
 */
public class SequencerModule extends UnitGate implements ISequencer {

    private UnitInputPort inputSignal;
    // The iInput port for the sequencer
    private IInput iInput;
    // The iOutput port for the sequencer
    private IOutput iOutput;
    // The out signal
    private Multiply ioMultiply;
    // Current Step
    protected int currentStep;
    // A table of the different step (8 step)
    private double[] steps;
    // Producer of voltage
    private VoltageProducer voltageProducer;
    // Input voltage MIN and MAX
    private final double MIN = -1;
    private final double MAX = 1;


    public SequencerModule(IComponent component) {
        this.ioMultiply = new Multiply();

        this.inputSignal = new UnitInputPort("inputSignal");
        this.inputSignal.setDefault(0.0);
        addPort(this.inputSignal);

        Register.declare(component, this);
        Register.declare(component, this.ioMultiply);

        this.output.connect(this.ioMultiply.inputB);

        this.iInput = Factory.createInput("InputGate", component, this.inputSignal);
        this.iOutput = Factory.createOutput("OutputPort", component, this.ioMultiply.output);

        this.voltageProducer = new VoltageProducer();
        this.voltageProducer.input.set(1.0/5);
        this.voltageProducer.output.connect(this.ioMultiply.inputB);

        // Start the gate
        this.start();

        this.currentStep = 1;
        this.steps = new double[8];
        for (int i = 0; i < this.steps.length; i++){
            this.steps[i] = 0.0;
        }
    }

    @Override
    public void generate(int start, int limit) {
        double[] samples = this.inputSignal.getValues();
        double[] outputs = output.getValues();

        for (int i = start; i < limit; i++ ){
            boolean triggered = input.checkGate(i);
            if (triggered) {
                this.nextStep();
            }
            outputs[i] = samples[i];
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
        if (value >= this.MIN && value <= this.MAX){
            this.steps[step - 1] = value;
            if (this.currentStep == step)
                this.ioMultiply.inputA.set(value);
            System.out.println(ioMultiply.output.getValue());
        }
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
        this.ioMultiply.inputA.set(steps[currentStep - 1]);
        System.out.println("BOUYA");
    }

    /**
     * Get the max value per step
     *
     * @return double
     */
    @Override
    public double getMaxValue() {
        return this.MAX;
    }

    /**
     * Get the min value per step
     *
     * @return double
     */
    @Override
    public double getMinValue() {
        return this.MIN;
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

    @Override
    public void activate() {
        this.input.on();
    }

    @Override
    public void deactivate() {
        this.input.off();
    }

    @Override
    public boolean isActivated() {
        if(this.input.isOff())
            return true;
        else
            return false;
    }
}
