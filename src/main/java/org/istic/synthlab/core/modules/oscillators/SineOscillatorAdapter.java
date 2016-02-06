package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.SineOscillator;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.services.ModulesFactory;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;


/**
 * A sinusoidal oscillator.
 *
 */
public class SineOscillatorAdapter extends AbstractOscillator {

    private SineOscillator sineOscillator;

    /**
     * Instantiates a new Sine oscillator adapter.
     */
    public SineOscillatorAdapter(IComponent component) {
        super(component);
        this.sineOscillator = new SineOscillator();
        // Declare the relation to the register
        Register.declare(component, this.sineOscillator);
        // Link input to the frequency input of the oscillator to modulate it with the input signal
        this.fm = ModulesFactory.createInput("Fm", component, sineOscillator.frequency);
        this.am = ModulesFactory.createInput("Am", component, sineOscillator.amplitude);
        this.output = ModulesFactory.createOutput("Out", component, sineOscillator.output);
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
