package org.istic.synthlab.core.modules.io;

/**
 * Created by stephane on 02/02/16.
 */
public interface IInput {

    void connect(IOutput out);
    void deconnect();

}
