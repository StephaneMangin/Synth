package org.istic.synthlab.core.modules.io;

import com.jsyn.ports.UnitInputPort;

/**
 * Created by stephane on 02/02/16.
 */
public interface IInput {

    void connect(IOutput out);
    void deconnect();

    UnitInputPort getUnitInputPort();

}
