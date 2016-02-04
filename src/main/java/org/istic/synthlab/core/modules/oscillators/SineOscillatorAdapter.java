package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.SineOscillator;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.parametrization.Potentiometer;
import org.istic.synthlab.core.modules.parametrization.PotentiometerType;
import org.istic.synthlab.core.services.IOMapping;
import org.istic.synthlab.core.services.ModulesFactory;


/**
 * A sinusoidal oscillator.
 *
 */
public class SineOscillatorAdapter extends AbstractOscillator {
    private SineOscillator sineOscillator;
    private IOutput output;
    private Potentiometer potentiometer;

    /**
     * Instantiates a new Sine oscillator adapter.
     */
    public SineOscillatorAdapter(IComponent component) {
        super(component);
        this.sineOscillator = new SineOscillator();
        this.output = ModulesFactory.createOutput(component, sineOscillator.output);
        this.potentiometer = new Potentiometer("Amplitude", PotentiometerType.LINEAR, 1.0, 0.0, 0.0);

        // Declare the relation to the mapping
        IOMapping.declare(component, this.sineOscillator);
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
        return potentiometer;
    }

    @Override
    public void setAmplitude(double value) {
        sineOscillator.amplitude.set(value);
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
