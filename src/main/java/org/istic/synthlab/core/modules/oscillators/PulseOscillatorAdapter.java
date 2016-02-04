package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.PulseOscillator;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.services.IOMapping;
import org.istic.synthlab.core.services.ModulesFactory;
import org.istic.synthlab.core.modules.parametrization.Potentiometer;
import org.istic.synthlab.core.modules.parametrization.PotentiometerType;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

/**
 * A pulse generator.
 *
 */
public class PulseOscillatorAdapter extends AbstractOscillator {

    private PulseOscillator pulseOscillator;
    private IOutput output;
    private Potentiometer potentiometer;

    public PulseOscillatorAdapter(IComponent component) {
        super(component);
        this.pulseOscillator = new PulseOscillator();
        // Declare the relation to the mapping
        IOMapping.declare(component, this.pulseOscillator);
        this.output = ModulesFactory.createOutput(component, pulseOscillator.output);
        this.potentiometer = new Potentiometer("Frequency", PotentiometerType.EXPONENTIAL, 20000.0, 20.0, 320.0);

    }

    @Override
    public IInput getInput() {
        return null;
    }

    @Override
    public IOutput getOutput() {
        return this.output;
    }

    @Override
    public Potentiometer getPotentiometer() {
        return this.potentiometer;
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
