package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.PulseOscillator;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.modulators.ModulatorType;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.services.ModulesFactory;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * A pulse generator.
 *
 */
public class PulseOscillatorAdapter extends AbstractOscillator {

    private PulseOscillator pulseOscillator;

    /*
    * the constructor
    */
    public PulseOscillatorAdapter(IComponent component) {
        super(component);
        this.pulseOscillator = new PulseOscillator();
        // Declare the relation to the register
        Register.declare(component, this.pulseOscillator);
        // Link input to the frequency input of the oscillator to modulate it with the input signal
        this.fm = ModulesFactory.createInput("Fm", component, pulseOscillator.frequency);
        this.am = ModulesFactory.createInput("Am", component, pulseOscillator.amplitude);
        this.output = ModulesFactory.createOutput("Out", component, pulseOscillator.output);
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
