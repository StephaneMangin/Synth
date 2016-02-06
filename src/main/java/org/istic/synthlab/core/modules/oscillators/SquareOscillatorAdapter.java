package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.SquareOscillator;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.modulators.ModulatorType;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.services.ModulesFactory;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * A square shape generator.
 *
 */
public class SquareOscillatorAdapter extends AbstractOscillator {

    private SquareOscillator squareOscillator;

    public SquareOscillatorAdapter(IComponent component) {
        super(component);
        this.squareOscillator = new SquareOscillator();
        // Declare the relation to the register
        Register.declare(component, this.squareOscillator);
        // Link input to the frequency input of the oscillator to modulate it with the input signal
        this.fm = ModulesFactory.createInput("Fm", component, squareOscillator.frequency);
        this.am = ModulesFactory.createInput("Am", component, squareOscillator.amplitude);
        this.output = ModulesFactory.createOutput("Out", component, squareOscillator.output);
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
