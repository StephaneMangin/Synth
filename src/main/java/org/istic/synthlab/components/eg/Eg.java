package org.istic.synthlab.components.eg;

import org.istic.synthlab.core.AbstractComponent;
import org.istic.synthlab.core.modules.envelope.IEnvelopeDAHDSR;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;

/**
 * Module EG.
 *
 * @author <Ngassam Noumi Paola> npaolita.[ât]yahoo.fr
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

    public IInput getInput(){ return envelope.getInput(); }

    public IOutput getOutput(){ return envelope.getOutput(); }

    public void setAmplitude(double value) { envelope.setAmplitude(value); }

    public double getAmplitude() { return envelope.getAmplitude(); }

    public double getAmplitudeMax() { return envelope.getAmplitudeMax(); }

    public double getAmplitudeMin() { return envelope.getAmplitudeMin(); }

    public void setDelay(double value) { envelope.setDelay(value); }

    public double getDelay() { return envelope.getDelay(); }

    public double getDelayMax() { return envelope.getDelayMax(); }

    public double getDelayMin() { return envelope.getDelayMin(); }

    public void setAttack(double value) { envelope.setAttack(value); }

    public double getAttack() { return envelope.getAttack(); }

    public double getAttackMax() { return envelope.getAttackMax(); }

    public void setHold(double value) { envelope.setHold(value); }

    public double getHold() { return envelope.getHold(); }

    public double getHoldMax() { return envelope.getHoldMax(); }

    public double getHoldMin() { return envelope.getHoldMin(); }

    public void setDecay(double value) { envelope.setDecay(value); }

    public double getDecay() { return envelope.getDecay(); }

    public double getDecayMax() { return envelope.getDecayMax(); }

    public double getDecayMin() { return envelope.getDecayMin(); }

    public void setSustain(double value) { envelope.setSustain(value); }

    public double getSustain() { return envelope.getSustain(); }

    public double getSustainMax() { return envelope.getSustainMax(); }

    public double getSustainMin() { return envelope.getSustainMin(); }

    public void setRelease(double value) { envelope.setRelease(value); }

    public double getRelease() { return envelope.getRelease(); }

    public double getReleaseMax() { return envelope.getReleaseMax(); }

    public double getReleaseMin() { return envelope.getReleaseMin(); }
}
