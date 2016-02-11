package org.istic.synthlab.core.modules.modulators;

import com.jsyn.ports.UnitInputPort;
import org.istic.synthlab.core.Resource;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

/**
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public interface IModulator extends Resource {

    /**
     * Return the input
     *
     * @return IInput
     */
    IInput getInput();

    /**
     * Return the output
     *
     * @return IOutput
     */
    IOutput getOutput();

    /**
     * Returns an amplitude.
     *
     * @return double
     */
    double getValue();

    /**
     * Set the amplitude.
     *
     */
    void setValue(double value);

    /**
     * The maximum value defined by the potentiometer of the modulator
     *
     * @return double
     */
    double getMax();

    /**
     * The minimum value defined by the potentiometer of the modulator
     *
     * @return double
     */
    double getMin();

    /**
     * Returns the name of the modulator
     *
     * @return String
     */
    String getName();
}
