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

    public void setAmplitude(double value) { envelope.getAmplitudePotentiometer().setValue(value); }

    public double getAmplitude() { return envelope.getAmplitudePotentiometer().getValue(); }

    public double getAmplitudeMax() { return envelope.getAmplitudePotentiometer().getMax(); }

    public double getAmplitudeMin() { return envelope.getAmplitudePotentiometer().getMin(); }

    public void setDelay(double value) { envelope.getDelayPotentiometer().setValue(value); }

    public double getDelay() { return envelope.getDelayPotentiometer().getValue(); }

    public double getDelayMax() { return envelope.getDelayPotentiometer().getMax(); }

    public double getDelayMin() { return envelope.getDelayPotentiometer().getMin(); }

    public void setAttack(double value) { envelope.getAttackPotentiometer().setValue(value); }

    public double getAttack() { return envelope.getAttackPotentiometer().getValue(); }

    public double getAttackMax() { return envelope.getAttackPotentiometer().getMax(); }

    public double getAttackMin() { return envelope.getAttackPotentiometer().getMin(); }

    public void setHold(double value) { envelope.getHoldPotentiometer().setValue(value); }

    public double getHold() { return envelope.getHoldPotentiometer().getValue(); }

    public double getHoldMax() { return envelope.getHoldPotentiometer().getMax(); }

    public double getHoldMin() { return envelope.getHoldPotentiometer().getMin(); }

    public void setDecay(double value) { envelope.getDecayPotentiometer().setValue(value); }

    public double getDecay() { return envelope.getDecayPotentiometer().getValue(); }

    public double getDecayMax() { return envelope.getDecayPotentiometer().getMax(); }

    public double getDecayMin() { return envelope.getDecayPotentiometer().getMin(); }

    public void setSustain(double value) { envelope.getSustainPotentiometer().setValue(value); }

    public double getSustain() { return envelope.getSustainPotentiometer().getValue(); }

    public double getSustainMax() { return envelope.getSustainPotentiometer().getMax(); }

    public double getSustainMin() { return envelope.getSustainPotentiometer().getMin(); }

    public void setRelease(double value) { envelope.getReleasePotentiometer().setValue(value); }

    public double getRelease() { return envelope.getReleasePotentiometer().getValue(); }

    public double getReleaseMax() { return envelope.getReleasePotentiometer().getMax(); }

    public double getReleaseMin() { return envelope.getReleasePotentiometer().getMin(); }
}
