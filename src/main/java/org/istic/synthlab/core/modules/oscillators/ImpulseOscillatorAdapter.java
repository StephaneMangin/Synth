package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.ImpulseOscillator;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.modulators.ModulatorType;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.services.ModulesFactory;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * An impulse shape generator.
 *
 */
public class ImpulseOscillatorAdapter extends AbstractOscillator {

    private ImpulseOscillator impulseOscillator;

    public ImpulseOscillatorAdapter(IComponent component) {
        super(component);
        this.impulseOscillator = new ImpulseOscillator();
        // Declare the relation to the register
        Register.declare(component, this.impulseOscillator);
        // Link input to the frequency input of the oscillator to modulate it with the input signal
        this.fm = ModulesFactory.createInput("Fm", component, impulseOscillator.frequency);
        this.am = ModulesFactory.createInput("Am", component, impulseOscillator.amplitude);
        this.output = ModulesFactory.createOutput("Out", component, impulseOscillator.output);
        this.frequencyPotentiometer = new Potentiometer("Frequency", impulseOscillator.frequency, PotentiometerType.EXPONENTIAL, 20000.0, 20.0, 1000.0);

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
