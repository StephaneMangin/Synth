package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.SineOscillator;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.parametrization.ValueType;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.services.ModulesFactory;
import org.istic.synthlab.core.modules.parametrization.Potentiometer;
import org.istic.synthlab.core.modules.parametrization.PotentiometerType;


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
        this.input = ModulesFactory.createInput(component, sineoscillator.frequency);
        this.output = ModulesFactory.createOutput(component, sineoscillator.output);
        this.amplitudePotentiometer = new Potentiometer("Frequency", PotentiometerType.EXPONENTIAL, 20000.0, 20.0, 320.0);
    }

    @Override
    public void activate() {
        this.sineoscillator.setEnabled(true);
    }

    @Override
    public void desactivate() {
        this.sineoscillator.setEnabled(false);
    }

    @Override
    public void setAmplitude(double value) {
        sineoscillator.amplitude.set(value);
    }
}
