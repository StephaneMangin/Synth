package org.istic.synthlab.core.modules.lineOuts;

import com.jsyn.ports.UnitInputPort;
import org.istic.synthlab.core.modules.parametrization.Potentiometer;
import org.istic.synthlab.core.Resource;
import org.istic.synthlab.core.modules.io.IInput;

/**
 * Created by stephane on 02/02/16.
 */
public interface ILineOut extends Resource {

    void setVolume(double value);

    Potentiometer getPotentiometer();

    void start();

    void stop();

    UnitInputPort getLineOut(); // Doute, on garde vraiment ça ?

    IInput getInput();

}
