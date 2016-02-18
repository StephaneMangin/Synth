package org.istic.synthlab.components.eg;

import org.istic.synthlab.components.AbstractComponent;
import org.istic.synthlab.core.modules.envelope.IEnvelopeDAHDSR;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;

/**
 * The model of EG component.
 *
 * It creates an Envelope Generator that produce a "one shot" waveform
 *
 * @author <Ngassam Noumi Paola> npaolita.[ât]yahoo.fr
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 * @since <pre>Feb 8, 2016</pre>
 */
public class Eg extends AbstractComponent {

    private IEnvelopeDAHDSR envelope;

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

    public Potentiometer getAmplitudePotentiometer() { return envelope.getAmplitudePotentiometer(); }

    public Potentiometer getDelayPotentiometer() { return envelope.getDelayPotentiometer(); }

    public Potentiometer getAttackPotentiometer() { return envelope.getAttackPotentiometer(); }

    public Potentiometer getHoldPotentiometer() { return envelope.getHoldPotentiometer(); }

    public Potentiometer getDecayPotentiometer() { return envelope.getDecayPotentiometer(); }

    public Potentiometer getSustainPotentiometer() { return envelope.getSustainPotentiometer(); }

    public Potentiometer getReleasePotentiometer() { return envelope.getReleasePotentiometer(); }
}
