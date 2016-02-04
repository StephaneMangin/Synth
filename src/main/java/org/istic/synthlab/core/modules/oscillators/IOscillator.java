package org.istic.synthlab.core.modules.oscillators;


import com.jsyn.unitgen.UnitGenerator;
import org.istic.synthlab.core.modules.parametrization.Potentiometer;
import org.istic.synthlab.core.Resource;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

/**
 * The interface Oscillator.
 *
 * @author Group1
 */

public interface IOscillator extends Resource {

    IInput getInput();

    /**
     * Gets output.
     *
     * @return the output
     */
    IOutput getOutput();

    /**
     * Gets the potientiometer
     *
     * @return the potientiometer
     */
    Potentiometer getPotentiometer();


    UnitGenerator getUnitGenerator();
}
