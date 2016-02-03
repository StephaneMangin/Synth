package org.istic.synthlab.core.modules.lineOuts;

import com.jsyn.ports.UnitInputPort;

/**
 * Created by stephane on 02/02/16.
 */
public interface ILineOut {

    void setVolume(double value);
    double getVolume();
    void start();
    void stop();
    UnitInputPort getLineOut();
}
