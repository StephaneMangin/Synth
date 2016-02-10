package org.istic.synthlab.core.modules.envelope;

import org.istic.synthlab.core.Resource;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

/**
 * @author gottstein[dot]cyprien[at]gmail[dot]com on 09/02/16.
 */
public interface IEnvelopeDAHDSR extends Resource {

    IInput getInput();

    IOutput getOutput();

    void setAmplitude(double value);

    double getAmplitude();

    double getAmplitudeMax();

    double getAmplitudeMin();

    void setDelay(double value);

    double getDelay();

    double getDelayMax();

    double getDelayMin();

    void setAttack(double value);

    double getAttack();

    double getAttackMax();

    double getAttackMin();

    void setHold(double value);

    double getHold();

    double getHoldMax();

    double getHoldMin();

    void setDecay(double value);

    double getDecay();

    double getDecayMax();

    double getDecayMin();

    void setSustain(double value);

    double getSustain();

    double getSustainMax();

    double getSustainMin();

    void setRelease(double value);

    double getRelease();

    double getReleaseMax();

    double getReleaseMin();

}
