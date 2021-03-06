package org.istic.synthlab.core.modules.sequencer;

import org.istic.synthlab.core.Resource;
import org.istic.synthlab.core.modules.IModule;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

/**
 * @author Dechaud John Marc johnmarcdechaud[at]gmail[dot]com on 2/22/16.
 */
public interface ISequencer extends IModule, Resource {

    /**
     * Get the current step value of the sequencer
     * @param step
     * @return double
     * */
    double getStepValue(int step);

    /**
     * Get the current step value of the sequencer
     * @param step
     * @param value
     * */
    void setStepValue(int step, double value);

    /**
     * Get the min value per step
     * @return  double
     * */
    double getMinValue();

    /**
     * Get the max value per step
     * @return  double
     * */
    double getMaxValue();

    /**
     * Get the current step of the sequencer
     * @return int
     * */
    int getCurrentStep();

    /**
     * Reinitialize the step of the sequencer
     * */
    void resetStep();

    /**
     * Switch to the next step
     * */
    void nextStep();

    /**
     * Get the input port
     * @return IInput
     * */
    IInput getInputGatePort();

    /**
     * Get the output port
     * @return IInput
     * */
    IOutput getOutputPort();

}
