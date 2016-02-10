package org.istic.synthlab.core.modules.passThrough;

import org.istic.synthlab.core.Resource;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

/**
 * @author gottstein[dot]cyprien[at]gmail[dot]com on 09/02/16.
 */
public interface IPassThrough extends Resource {

    /**
     * Returns the input the Pass Through module
     *
     * @return IInput
     */
    IInput getInput();
    /**
     * Returns the output number one of the Pass Through module
     *
     * @return IOutput
     */
    IOutput getOutput1();

    /**
     * Returns the output number two of the Pass Through module
     *
     * @return IOutput
     */
    IOutput getOutput2();

    /**
     * Returns the output number three of the Pass Through module
     *
     * @return IOutput
     */
    IOutput getOutput3();
}
