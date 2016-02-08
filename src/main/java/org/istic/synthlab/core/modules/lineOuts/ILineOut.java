package org.istic.synthlab.core.modules.lineOuts;


import org.istic.synthlab.core.Resource;
import org.istic.synthlab.core.modules.io.IInput;

/**
 * An output line
 *
 */
public interface ILineOut extends Resource {

    /**
     * Set the output volume
     *
     * @param value double
     */
    void setVolume(double value);

    /**
     * Returns the current output volume
     *
     * @return double
     */
    double getVolume();

    /**
     * Launch the line pulling
     */
    void start();

    /**
     * Stop the line pulling
     *
     */
    void stop();

    /**
     * Returns the input of this line
     *
     * @return IInput
     */
    IInput getInput();

}
