package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.TriangleOscillator;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.parametrization.ValueType;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.services.ModulesFactory;
import org.istic.synthlab.core.modules.parametrization.Potentiometer;
import org.istic.synthlab.core.modules.parametrization.PotentiometerType;

/**
 * A triangle shape generator
 *
 */
public class TriangleOscillatorAdapter extends AbstractOscillator {

    private TriangleOscillator triangleOscillator;

    public TriangleOscillatorAdapter(IComponent component) {
        super(component);
        this.triangleOscillator = new TriangleOscillator();
        // Declare the relation to the register
        Register.declare(component, this.triangleOscillator);
        this.output = ModulesFactory.createOutput(component, triangleOscillator.output);
        this.amplitudePotentiometer = new Potentiometer("Frequency", PotentiometerType.EXPONENTIAL, 20000.0, 20.0, 320.0);
    }

    @Override
    public void activate() {
        this.triangleOscillator.setEnabled(true);
    }

    @Override
    public void desactivate() {
        this.triangleOscillator.setEnabled(false);
    }

    @Override
    public void setAmplitude(double value) {
        triangleOscillator.amplitude.set(value);
    }
}
