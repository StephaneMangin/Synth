package org.istic.synthlab.core.modules.io;

/**
 * Created by stephane on 02/02/16.
 */
public interface IOutput {

    void connect(IInput in);
    void deconnect();

}
