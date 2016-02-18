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
        Register.declare(component, envelope);
        input = Factory.createInput("In", component, envelope.input);
        output = Factory.createOutput("Out", component, envelope.output);


        // WARNING : The configuration of the potentiometers here is only decided by me to make it easier to move forward.
        // It is absolutely NOT DEFINITIVE !!!
        // FIXME : default values are now from the jsyn defaults
        delayPotentiometer = new Potentiometer("Delay", envelope.delay, PotentiometerType.LINEAR, 1.0D, 0.0D, 0.0D);
        attackPotentiometer = new Potentiometer("Attack", envelope.attack, PotentiometerType.LINEAR, 1.0D, 0.0D, 0.1D);
        holdPotentiometer = new Potentiometer("Hold", envelope.hold, PotentiometerType.LINEAR, 1.0D, 0.0D, 0.0D);
        decayPotentiometer = new Potentiometer("Decay", envelope.decay, PotentiometerType.LINEAR, 1.0D, 0.0D, 0.2D);
        sustainPotentiometer = new Potentiometer("Sustain", envelope.sustain, PotentiometerType.LINEAR, 1.0D, 0.0D, 0.5D);
        releasePotentiometer = new Potentiometer("Release", envelope.release, PotentiometerType.LINEAR, 1.0D, 0.0D, 0.3D);
        amplitudePotentiometer = new Potentiometer("Amplitude", envelope.amplitude, PotentiometerType.LINEAR, 1.0D, 0.0D, 1.0D);


    }

    public IInput getInput(){ return input; }

    public IOutput getOutput(){ return output; }

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

    public Potentiometer getAmplitudePotentiometer() {
        return amplitudePotentiometer;
    }

    public Potentiometer getDelayPotentiometer() {
        return delayPotentiometer;
    }

    public Potentiometer getAttackPotentiometer() {
        return attackPotentiometer;
    }

    public Potentiometer getHoldPotentiometer() {
        return holdPotentiometer;
    }

    public Potentiometer getDecayPotentiometer() {
        return decayPotentiometer;
    }

    public Potentiometer getSustainPotentiometer() {
        return sustainPotentiometer;
    }

    public Potentiometer getReleasePotentiometer() {
        return releasePotentiometer;
    }
}
