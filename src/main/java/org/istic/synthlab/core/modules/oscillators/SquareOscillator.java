package org.istic.synthlab.core.modules.oscillators;

import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * A square shape generator.
 *
 */
public class SquareOscillator extends AbstractOscillator {

    private com.jsyn.unitgen.SquareOscillator squareOscillator;

    public SquareOscillator(IComponent component) {
        super(component);
        this.squareOscillator = new com.jsyn.unitgen.SquareOscillator();
        // Declare the relation to the register
        Register.declare(component, this.squareOscillator);
        // Link input to the frequency input of the oscillator to modulate it with the input signal
        this.fm = Factory.createInput("Fm", component, squareOscillator.frequency);
        this.am = Factory.createInput("Am", component, squareOscillator.amplitude);
        this.output = Factory.createOutput("Out", component, squareOscillator.output);
        this.frequencyPotentiometer = new Potentiometer("Frequency", squareOscillator.frequency, PotentiometerType.EXPONENTIAL, 20000.0D, 20.0D, 1000.0D);
    }

    @Override
    public void activate() {
        this.squareOscillator.setEnabled(true);
    }

    @Override
    public void desactivate() {
        this.squareOscillator.setEnabled(false);
    }
}
