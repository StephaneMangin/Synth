package org.istic.synthlab.core.modules.envelope;

import org.istic.synthlab.core.Resource;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;

/**
 * @author gottstein[dot]cyprien[at]gmail[dot]com on 09/02/16.
 */
public interface IEnvelopeDAHDSR extends Resource {

    IInput getInput();

    IOutput getOutput();

    Potentiometer getAmplitudePotentiometer();

    Potentiometer getDelayPotentiometer();

    Potentiometer getAttackPotentiometer();

    Potentiometer getHoldPotentiometer();

    Potentiometer getDecayPotentiometer();

    Potentiometer getSustainPotentiometer();

    Potentiometer getReleasePotentiometer();

}
