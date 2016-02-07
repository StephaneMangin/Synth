package org.istic.synthlab.core.modules.oscillators;

import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * A pulse generator.
 *
 */
public class PulseOscillator extends AbstractOscillator {

    private com.jsyn.unitgen.PulseOscillator pulseOscillator;

    /*
    * the constructor
    */
    public PulseOscillator(IComponent component) {
        super(component);
        this.pulseOscillator = new com.jsyn.unitgen.PulseOscillator();
        // Declare the relation to the register
        Register.declare(component, this.pulseOscillator);
        // Link input to the frequency input of the oscillator to modulate it with the input signal
        this.fm = Factory.createInput("Fm", component, pulseOscillator.frequency);
        this.am = Factory.createInput("Am", component, pulseOscillator.amplitude);
        this.output = Factory.createOutput("Out", component, pulseOscillator.output);
        this.frequencyPotentiometer = new Potentiometer("Frequency", pulseOscillator.frequency, PotentiometerType.EXPONENTIAL, 20000.0D, 20.0D, 1000.0D);
    }

    @Override
    public void activate() {
        this.pulseOscillator.setEnabled(true);
    }

    @Override
    public void desactivate() {
        this.pulseOscillator.setEnabled(false);
    }

}
