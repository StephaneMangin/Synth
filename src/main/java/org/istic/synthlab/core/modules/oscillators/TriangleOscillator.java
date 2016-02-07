package org.istic.synthlab.core.modules.oscillators;

import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * A triangle shape generator
 *
 */
public class TriangleOscillator extends AbstractOscillator {

    private com.jsyn.unitgen.TriangleOscillator triangleOscillator;

    public TriangleOscillator(IComponent component) {
        super(component);
        this.triangleOscillator = new com.jsyn.unitgen.TriangleOscillator();
        // Declare the relation to the register
        Register.declare(component, this.triangleOscillator);
        // Link input to the frequency input of the oscillator to modulate it with the input signal
        this.fm = Factory.createInput("Fm", component, triangleOscillator.frequency);
        this.am = Factory.createInput("Am", component, triangleOscillator.amplitude);
        this.output = Factory.createOutput("Out", component, triangleOscillator.output);
        this.frequencyPotentiometer = new Potentiometer("Frequency", triangleOscillator.frequency, PotentiometerType.EXPONENTIAL, 20000.0, 20.0, 1000.0);
    }

    @Override
    public void activate() {
        this.triangleOscillator.setEnabled(true);
    }

    @Override
    public void desactivate() {
        this.triangleOscillator.setEnabled(false);
    }
}
