package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.SquareOscillator;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.parametrization.ValueType;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.services.ModulesFactory;
import org.istic.synthlab.core.modules.parametrization.Potentiometer;
import org.istic.synthlab.core.modules.parametrization.PotentiometerType;

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
        this.output = ModulesFactory.createOutput(component, squareOscillator.output);
        this.amplitudePotentiometer = new Potentiometer("Amplitude", PotentiometerType.EXPONENTIAL,20000.0, 20.0, 320.0);

    }

    @Override
    public void setAmplitude(double value) {
        squareOscillator.amplitude.set(value);
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
