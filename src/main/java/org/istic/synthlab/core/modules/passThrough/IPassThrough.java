package org.istic.synthlab.core.modules.passThrough;

import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

/**
 * Created by cyprien on 08/02/16.
 */
public interface IPassThrough {

    /**
     * Returns the input the Pass Through module
     *
     * @return
     */
    IInput getInput();
    /**
     * Returns the output number one of the Pass Through module
     *
     * @return
     */
    IOutput getOutput1();

    /**
     * Returns the output number two of the Pass Through module
     *
     * @return
     */
    IOutput getOutput2();

    /**
     * Returns the output number three of the Pass Through module
     *
     * @return
     */
    IOutput getOutput3();

    /**
     * Activates the line out
     *
     */
    public void activate();

    /**
     *  deactivates the line out
     *
     */
    public void desactivate();

    /**
     * Returns true if the lineout is activate or false
     *
     * @return boolean
     */
    boolean isEnable();
}
