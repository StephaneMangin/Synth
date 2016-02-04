package org.istic.synthlab.core.modules.io;

import com.jsyn.ports.UnitOutputPort;

/**
 * Created by stephane on 02/02/16.
 */
public interface IOutput {

    void connect(IInput in);

    UnitOutputPort getUnitInputPort();
}
