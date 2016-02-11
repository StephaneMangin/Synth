package org.istic.synthlab.core.modules.lineOuts;


import org.istic.synthlab.core.Resource;
import org.istic.synthlab.core.modules.io.IInput;

/**
 * An output line
 *
 */
public interface ILineOut extends Resource {

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
