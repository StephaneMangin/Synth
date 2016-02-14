package org.istic.synthlab.core.modules.mix;

import org.istic.synthlab.core.Resource;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;

/**
 * @author  Dechaud John Marc johnmarcdechaud[at]gmail[dot]com on 2/12/16.
 */

/**
 * Interface that define the property of the Mixer
 * */
public interface IMix extends Resource{

    /**
     * Return the input number one of the mixer
     *
     * @return IInput
     */
    IInput getInput1();

    /**
     * Return the input number two of the mixer
     *
     * @return IInput
     */
    IInput getInput2();

    /**
     * Return the input number three of the mixer
     *
     * @return IInput
     */
    IInput getInput3();

    /**
     * Return the input number four of the mixer
     *
     * @return IInput
     */
    IInput getInput4();

    /**
     * Return the ieme input port
     *
     * @param i
     * @return IInput
     */
    IInput getInputPort(int i);

    /**
     * Return the output of the mixer
     *
     * @return IOutput
     */
    IOutput getOutput();


    /**
     * Add a new input port
     *
     * @param input
     */
    void addInputPort(IInput input);

    Potentiometer getAmmplitudePotentiometerInput1();
    Potentiometer getAmmplitudePotentiometerInput2();
    Potentiometer getAmmplitudePotentiometerInput3();
    Potentiometer getAmmplitudePotentiometerInput4();

}
