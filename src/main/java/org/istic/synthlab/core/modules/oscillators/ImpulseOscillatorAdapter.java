package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.ImpulseOscillator;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.parametrization.ValueType;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.services.ModulesFactory;
import org.istic.synthlab.core.modules.parametrization.Potentiometer;
import org.istic.synthlab.core.modules.parametrization.PotentiometerType;

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
        this.output = ModulesFactory.createOutput(component, impulseOscillator.output);
        this.amplitudePotentiometer = new Potentiometer("Frequency", PotentiometerType.EXPONENTIAL, 20000.0, 20.0, 320.0);

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
    public void setAmplitude(double value) {
        impulseOscillator.amplitude.set(value);
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
