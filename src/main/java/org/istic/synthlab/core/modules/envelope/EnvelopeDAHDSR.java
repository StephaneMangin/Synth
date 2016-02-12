package org.istic.synthlab.core.modules.envelope;

import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * @author gottstein[dot]cyprien[at]gmail[dot]com on 09/02/16.
 */
public class EnvelopeDAHDSR implements IEnvelopeDAHDSR {

    private com.jsyn.unitgen.EnvelopeDAHDSR envelope;

    private IInput input;
    private IOutput output;

    private Potentiometer amplitudePotentiometer;

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
