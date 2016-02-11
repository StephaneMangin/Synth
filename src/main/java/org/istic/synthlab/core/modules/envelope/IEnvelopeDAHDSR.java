package org.istic.synthlab.core.modules.envelope;

import org.istic.synthlab.core.Resource;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

/**
 * The interface IEnvelopeDAHDSR
 *
 * @author gottstein[dot]cyprien[at]gmail[dot]com on 09/02/16.
 */
public interface IEnvelopeDAHDSR extends Resource {

    /**
     * Returns the input of the envelope.
     *
     * @return the input
     */
    IInput getInput();

    /**
     * Returns the output of the envelope.
     *
     * @return IOutput
     */
    IOutput getOutput();

    /**
     * Sets the amplitude .
     *
     * @param value double
     */
    void setAmplitude(double value);

    /**
     * Gets the amplitude.
     *
     * @return the amplitude : double
     */
    double getAmplitude();

    /**
     * obtains the maximum amplitude of the envelope
     *
     * @return the max : double
     */
    double getAmplitudeMax();

    /**
     * obtains the minimum amplitude of the envelope
     *
     * @return the min : double
     */
    double getAmplitudeMin();

    /**
     * Sets the decay to the DAHDSR
     *
     * @param value double
     */
    void setDelay(double value);

    /**
     * Gets the delay.
     *
     * @return the delay : double
     */
    double getDelay();

    /**
     * Gets the maximum delay
     *
     * @return the delay max : double
     */
    double getDelayMax();

    /**
     * Gets the minimum delay
     *
     * @return the min : double
     */
    double getDelayMin();

    /**
     * Sets the attack value to the DAHDSR
     *
     * @param value double
     */
    void setAttack(double value);

    /**
     * Gets the attack value.
     *
     * @return the attack : double
     */
    double getAttack();

    /**
     * Gets the maximum value of the attack.
     *
     * @return the attack max : double
     */
    double getAttackMax();

    /**
     * Gets the minimum value of the attack.
     *
     * @return the attack min : double
     */
    double getAttackMin();

    /**
     * Sets the hold time to the DAHDSR
     *
     * @param value : double
     */
    void setHold(double value);

    /**
     * Gets the hold value.
     *
     * @return the hold : double
     */
    double getHold();

    /**
     * Gets the maximum hold.
     *
     * @return the hold max : double
     */
    double getHoldMax();

    /**
     * Gets the minimum hold.
     *
     * @return the hold min : double
     */
    double getHoldMin();

    /**
     * Sets the decay time to the DAHDSR. .
     *
     * @param value : double
     */
    void setDecay(double value);

    /**
     * Gets the decay.
     *
     * @return the decay : double
     */
    double getDecay();

    /**
     * Gets the maximum decay .
     *
     * @return the decay max : double
     */
    double getDecayMax();

    /**
     * Gets the minimum decay.
     *
     * @return the decay min : double
     */
    double getDecayMin();

    /**
     * Sets the sustain time to the DAHDSR..
     *
     * @param value : double
     */
    void setSustain(double value);

    /**
     * Gets the sustain value.
     *
     * @return the sustain : double
     */
    double getSustain();

    /**
     * Gets  the maximum sustain .
     *
     * @return the sustain max : double
     */
    double getSustainMax();

    /**
     * Gets the minimum sustain.
     *
     * @return the sustain min : double
     */
    double getSustainMin();

    /**
     * Sets the release time to the DAHDSR.
     *
     * @param value : double
     */
    void setRelease(double value);

    /**
     * Gets the release value.
     *
     * @return the release : double
     */
    double getRelease();

    /**
     * Gets the maximum  release.
     *
     * @return the release max : double
     */
    double getReleaseMax();

    /**
     * Gets the minimum release.
     *
     * @return the release min : double
     */
    double getReleaseMin();

}
