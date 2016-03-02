package org.istic.synthlab.components.eg;

import org.istic.synthlab.core.components.AbstractComponent;
import org.istic.synthlab.core.modules.envelope.IEnvelopeDAHDSR;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;

/**
 * The model of EG component.
 *
 * It creates an Envelope Generator that produce a "one shot" waveform
 *
 * An Envelope Generator is composed of the following input and output :
 * - A gate input
 * - A signal output (value between 0.0 and 1.0)
 *
 * An Envelope Generator is composed of the following potentiometer :
 * - An amplitude potentiometer (amplitude of the output)
 * - A delay potentiometer : how much delay do we want to start the envelope when the signal reach 1
 *      and trigger the envelope
 * - An attack potentiometer : how long last the rising phase to reach the peak of the envelope
 * - A hold potentiometer : how long last the peak phase of the envelope
 * - A decay potentiometer : how long last the decay phase of the envelope
 * - A sustain potentiometer : what is the level of sustain while the signal is still active
 * - A release potentiometer : how long last the release phase of the envelope
 *
 * @author <Ngassam Noumi Paola> npaolita.[ât]yahoo.fr
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 * @since <pre>Feb 8, 2016</pre>
 */
public class Eg extends AbstractComponent {

    private IEnvelopeDAHDSR envelope;

    /**
     * Constructor of the Envelope Generator component.
     *
     * @param name the name of the Component.
     */
    public Eg(String name) {
        super(name);
        envelope = Factory.createEnvelopeDAHDSR(this);
        getSource().connect(envelope.getInput());
        envelope.getOutput().connect(getSink());
    }

    @Override
    public void activate() {
        envelope.activate();
    }

    @Override
    public void deactivate() {
        envelope.deactivate();
    }

    @Override
    public boolean isActivated(){
        return envelope.isActivated();
    }

    @Override
    public void init() {
    }

    @Override
    public void run() {
        //?
    }

    /**
     * Getter to access the amplitude potentiometer of the Envelope Generator.
     *
     * @return Potentiometer the amplitude potentiometer
     */
    public Potentiometer getAmplitudePotentiometer() { return envelope.getAmplitudePotentiometer(); }

    /**
     * Getter to access the delay potentiometer of the Envelope Generator.
     *
     * @return Potentiometer the delay potentiometer
     */
    public Potentiometer getDelayPotentiometer() { return envelope.getDelayPotentiometer(); }

    /**
     * Getter to access the attack potentiometer of the Envelope Generator.
     *
     * @return Potentiometer the attack potentiometer
     */
    public Potentiometer getAttackPotentiometer() { return envelope.getAttackPotentiometer(); }

    /**
     * Getter to access the hold potentiometer of the Envelope Generator.
     *
     * @return Potentiometer the hold potentiometer
     */
    public Potentiometer getHoldPotentiometer() { return envelope.getHoldPotentiometer(); }

    /**
     * Getter to access the decay potentiometer of the Envelope Generator.
     *
     * @return Potentiometer the decay potentiometer
     */
    public Potentiometer getDecayPotentiometer() { return envelope.getDecayPotentiometer(); }

    /**
     * Getter to access the sustain potentiometer of the Envelope Generator.
     *
     * @return Potentiometer the sustain potentiometer
     */
    public Potentiometer getSustainPotentiometer() { return envelope.getSustainPotentiometer(); }

    /**
     * Getter to access the release potentiometer of the Envelope Generator.
     *
     * @return Potentiometer the release potentiometer
     */
    public Potentiometer getReleasePotentiometer() { return envelope.getReleasePotentiometer(); }
}
