package org.istic.synthlab.core.modules.lineOuts;


import com.jsyn.ports.UnitInputPort;
import org.istic.synthlab.core.modules.parametrization.Potentiometer;
import com.jsyn.unitgen.UnitGenerator;
import org.istic.synthlab.core.Resource;
import org.istic.synthlab.core.modules.io.IInput;

/**
 * @author  Group1
 * The interface Line out.
 */
public interface ILineOut extends Resource {

    void setVolume(double value);

    Potentiometer getPotentiometer();

    void start();

    void stop();

    UnitInputPort getLineOut(); // Doute, on garde vraiment ça ?

    IInput getInput();

    UnitGenerator getUnitGeneratorLineOut();

    UnitGenerator getUnitGeneratorFilter();

}
