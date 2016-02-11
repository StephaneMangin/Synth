package org.istic.synthlab.core.modules.envelope;

import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * The class EnvelopeDAHDSR
 * @author gottstein[dot]cyprien[at]gmail[dot]com on 09/02/16.
 *
 * Six stage envelope similar to an ADSR.
 * DAHDSR is like an ADSR but with an additional Delay stage before the attack,and a Hold stage after the Attack.
 * If Delay and Hold are both set to zero then it will act like an ADSR.
 * The envelope is triggered when the input goes above threshold.
 * The envelope is released when the input goes below threshold.
 * The input signal go from 0 to 1.
 */
public class EnvelopeDAHDSR implements IEnvelopeDAHDSR {

    /*
     * the jsyn DAHDSR envelope
     */
    private com.jsyn.unitgen.EnvelopeDAHDSR envelope;

    /*
     * the input port of the envelope
     */
    private IInput input;
    /*
     * the output port of the envelope
     */
    private IOutput output;

    private Potentiometer amplitudePotentiometer;

    /*
    * all the parameters of the envelope
    */
    private Potentiometer delayPotentiometer;
    private Potentiometer attackPotentiometer;
    private Potentiometer holdPotentiometer;
    private Potentiometer decayPotentiometer;
    private Potentiometer sustainPotentiometer;
    private Potentiometer releasePotentiometer;


    public EnvelopeDAHDSR(IComponent component){

        envelope = new com.jsyn.unitgen.EnvelopeDAHDSR();

        input = Factory.createInput("In", component, envelope.input);
        output = Factory.createOutput("Out", component, envelope.output);

        amplitudePotentiometer = new Potentiometer("Amplitude", envelope.amplitude, PotentiometerType.LINEAR, 1.0, 0.0, 1.0);

        // WARNING : The configuration of the potentiometers here is only decided by me to make it easier to move forward.
        // It is absolutely NOT DEFINITIVE !!!
        delayPotentiometer = new Potentiometer("Delay", envelope.delay, PotentiometerType.LINEAR, 1.0, 0.0, 0.0);
        attackPotentiometer = new Potentiometer("Attack", envelope.attack, PotentiometerType.LINEAR, 1.0, 0.0, 0.1);
        holdPotentiometer = new Potentiometer("Hold", envelope.hold, PotentiometerType.LINEAR, 1.0, 0.0, 0.3);
        decayPotentiometer = new Potentiometer("Decay", envelope.decay, PotentiometerType.LINEAR, 1.0, 0.0, 0.1);
        sustainPotentiometer = new Potentiometer("Sustain", envelope.sustain, PotentiometerType.LINEAR, 1.0, 0.0, 0.5);
        releasePotentiometer = new Potentiometer("Release", envelope.release, PotentiometerType.LINEAR, 1.0, 0.0, 0.1);

        Register.declare(component, envelope);

    }

    public IInput getInput(){ return input; }

    public IOutput getOutput(){ return output; }

    public void setAmplitude(double value) { amplitudePotentiometer.setValue(value); }

    public double getAmplitude() { return amplitudePotentiometer.getValue(); }

    public double getAmplitudeMax() { return amplitudePotentiometer.getMax(); }

    public double getAmplitudeMin() { return amplitudePotentiometer.getMin(); }

    public void setDelay(double value) { delayPotentiometer.setValue(value); }

    public double getDelay() { return delayPotentiometer.getValue(); }

    public double getDelayMax() { return delayPotentiometer.getMax(); }

    public double getDelayMin() { return delayPotentiometer.getMin(); }

    public void setAttack(double value) { attackPotentiometer.setValue(value); }

    public double getAttack() { return attackPotentiometer.getValue(); }

    public double getAttackMax() { return attackPotentiometer.getMax(); }

    public double getAttackMin() { return attackPotentiometer.getMin(); }

    public void setHold(double value) { holdPotentiometer.setValue(value); }

    public double getHold() { return holdPotentiometer.getValue(); }

    public double getHoldMax() { return holdPotentiometer.getMax(); }

    public double getHoldMin() { return holdPotentiometer.getMin(); }

    public void setDecay(double value) { decayPotentiometer.setValue(value); }

    public double getDecay() { return decayPotentiometer.getValue(); }

    public double getDecayMax() { return decayPotentiometer.getMax(); }

    public double getDecayMin() { return decayPotentiometer.getMin(); }

    public void setSustain(double value) { sustainPotentiometer.setValue(value); }

    public double getSustain() { return sustainPotentiometer.getValue(); }

    public double getSustainMax() { return sustainPotentiometer.getMax(); }

    public double getSustainMin() { return sustainPotentiometer.getMin(); }

    public void setRelease(double value) { releasePotentiometer.setValue(value); }

    public double getRelease() { return releasePotentiometer.getValue(); }

    public double getReleaseMax() { return releasePotentiometer.getMax(); }

    public double getReleaseMin() { return releasePotentiometer.getMin(); }

    @Override
    public void activate() {
        envelope.setEnabled(true);
    }

    @Override
    public void deactivate() {
        envelope.setEnabled(false);
    }

    @Override
    public boolean isActivated() {
        return envelope.isEnabled();
    }
}
