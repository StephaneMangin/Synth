package org.istic.synthlab.core.modules.oscillators;

import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;


/**
 * A sinusoidal oscillator.
 *
 */
public class SineOscillator extends AbstractOscillator {

    private com.jsyn.unitgen.SineOscillator sineOscillator;

    /**
     * Instantiates a new Sine oscillator adapter.
     */
    public SineOscillator(IComponent component) {
        super(component);
        this.sineOscillator = new com.jsyn.unitgen.SineOscillator();
        // Declare the relation to the register
        Register.declare(component, this.sineOscillator);
        // Link input to the frequency input of the oscillator to modulate it with the input signal
        this.fm = Factory.createInput("Fm", component, sineOscillator.frequency);
        this.am = Factory.createInput("Am", component, sineOscillator.amplitude);
        this.output = Factory.createOutput("Out", component, sineOscillator.output);
        this.frequencyPotentiometer = new Potentiometer("Volume", sineOscillator.frequency, PotentiometerType.EXPONENTIAL, 20000.0D, 20.0D, 1000.0D);
    }

    @Override
    public void activate() {
        this.sineOscillator.setEnabled(true);
    }

    @Override
    public void desactivate() {
        this.sineOscillator.setEnabled(false);
    }

}
