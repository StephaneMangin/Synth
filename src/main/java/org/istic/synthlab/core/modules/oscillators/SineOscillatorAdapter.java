package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.SineOscillator;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.modulators.ModulatorType;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.services.ModulesFactory;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;


/**
 * A sinusoidal oscillator.
 *
 */
public class SineOscillatorAdapter extends AbstractOscillator {

    private SineOscillator sineoscillator;

    /**
     * Instantiates a new Sine oscillator adapter.
     */
    public SineOscillatorAdapter(IComponent component) {
        super(component);
        this.sineoscillator = new SineOscillator();
        // Declare the relation to the register
        Register.declare(component, this.sineoscillator);
        //this.input = ModulesFactory.createInput(component, sineoscillator.frequency);
        this.output = ModulesFactory.createOutput(component, sineoscillator.output);
        this.frequencyPotentiometer = new Potentiometer("Volume", sineoscillator.frequency, PotentiometerType.EXPONENTIAL, 20000.0, 20.0, 1000.0);
    }

    @Override
    public void activate() {
        this.sineoscillator.setEnabled(true);
    }

    @Override
    public void desactivate() {
        this.sineoscillator.setEnabled(false);
    }

}
