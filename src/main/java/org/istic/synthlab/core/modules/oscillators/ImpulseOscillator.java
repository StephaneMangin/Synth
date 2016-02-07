package org.istic.synthlab.core.modules.oscillators;

import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * An impulse shape generator.
 *
 */
public class ImpulseOscillator extends AbstractOscillator {

    private com.jsyn.unitgen.ImpulseOscillator impulseOscillator;

    public ImpulseOscillator(IComponent component) {
        super(component);
        this.impulseOscillator = new com.jsyn.unitgen.ImpulseOscillator();
        // Declare the relation to the register
        Register.declare(component, this.impulseOscillator);
        // Link input to the frequency input of the oscillator to modulate it with the input signal
        this.fm = Factory.createInput("Fm", component, impulseOscillator.frequency);
        this.am = Factory.createInput("Am", component, impulseOscillator.amplitude);
        this.output = Factory.createOutput("Out", component, impulseOscillator.output);
        this.frequencyPotentiometer = new Potentiometer("Frequency", impulseOscillator.frequency, PotentiometerType.EXPONENTIAL, 20000.0D, 20.0D, 1000.0D);

    }

    @Override
    public void activate() {
        this.impulseOscillator.setEnabled(true);
    }

    @Override
    public void desactivate() {
        this.impulseOscillator.setEnabled(false);
    }
}
