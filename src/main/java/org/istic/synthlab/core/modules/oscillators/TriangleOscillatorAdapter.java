package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.TriangleOscillator;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.services.ModulesFactory;
import org.istic.synthlab.core.modules.parametrization.Potentiometer;
import org.istic.synthlab.core.modules.parametrization.PotentiometerType;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

/**
 * A triangle shape generator
 *
 */
public class TriangleOscillatorAdapter extends AbstractOscillator {

    private TriangleOscillator triangleOscillator;
    private IOutput output;
    private Potentiometer potentiometer;

    public TriangleOscillatorAdapter(IComponent component) {
        super(component);
        this.triangleOscillator = new TriangleOscillator();
        this.output = ModulesFactory.createOutput(triangleOscillator.output);
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
    public void activate() {
        this.triangleOscillator.setEnabled(true);
    }

    @Override
    public void desactivate() {
        this.triangleOscillator.setEnabled(false);
    }
}
