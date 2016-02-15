package org.istic.synthlab.core.modules.modulators;

import org.istic.synthlab.core.Resource;
import org.istic.synthlab.core.modules.IModule;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

/**
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public interface IModulator extends IModule, Resource {

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
     * Returns the name of the modulator
     *
     * @return String
     */
    String getName();

    /**
     * Returns the raw value from the potentiometer
     *
     * @return double
     */
    double getRawValue();

    /**
     * Returns the modulation value
     *
     * @return double
     */
    double getValue();

    /**
     * Set the modulation value
     *
     */
    void setValue(double value);


    /**
     * Returns the maximum modulation value
     *
     * @return double
     */
    double getMax();

    /**
     * Returns the minimum modulation value
     *
     * @return double
     */
    double getMin();
    /**
     * Set the maximum modulation value
     *
     * @return double
     */
    void setMax(double value);

    /**
     * Set the minimum modulation value
     *
     * @return double
     */
    void setMin(double value);

    /**
     * Bypass the convertion when setting a value
     *
     * @param value
     */
    void setRawValue(double value);
}
