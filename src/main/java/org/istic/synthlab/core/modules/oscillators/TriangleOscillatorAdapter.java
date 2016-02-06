package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.TriangleOscillator;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.modulators.ModulatorType;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.services.ModulesFactory;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * A triangle shape generator
 *
 */
public class TriangleOscillatorAdapter extends AbstractOscillator {

    private TriangleOscillator triangleOscillator;

    public TriangleOscillatorAdapter(IComponent component) {
        super(component);
        this.triangleOscillator = new TriangleOscillator();
        // Declare the relation to the register
        Register.declare(component, this.triangleOscillator);
        // Link input to the frequency input of the oscillator to modulate it with the input signal
        this.fm = ModulesFactory.createInput("Fm", component, triangleOscillator.frequency);
        this.am = ModulesFactory.createInput("Am", component, triangleOscillator.amplitude);
        this.output = ModulesFactory.createOutput("Out", component, triangleOscillator.output);
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
