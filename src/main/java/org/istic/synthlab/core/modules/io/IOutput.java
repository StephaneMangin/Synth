package org.istic.synthlab.core.modules.io;

/**
 *
 *
 * The interface Output.
 */
public interface IOutput {

    /**
     * Connect the output to an input port
     *
     * @param in:IInput
     */
    void connect(IInput in);
    void deconnect();

    String getName();
}
