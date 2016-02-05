package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.PulseOscillator;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.parametrization.ValueType;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.services.ModulesFactory;
import org.istic.synthlab.core.modules.parametrization.Potentiometer;
import org.istic.synthlab.core.modules.parametrization.PotentiometerType;

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
        this.output = ModulesFactory.createOutput(component, pulseOscillator.output);
        this.amplitudePotentiometer = new Potentiometer("Amplitude", PotentiometerType.EXPONENTIAL, 20000.0, 20.0, 320.0);
    }

    @Override
    public void setAmplitude(double value) {
        pulseOscillator.amplitude.set(value);
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
