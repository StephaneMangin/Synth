package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.SquareOscillator;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.services.IOMapping;
import org.istic.synthlab.core.services.ModulesFactory;
import org.istic.synthlab.core.modules.parametrization.Potentiometer;
import org.istic.synthlab.core.modules.parametrization.PotentiometerType;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

/**
 * A square shape generator.
 *
 */
public class SquareOscillatorAdapter extends AbstractOscillator {

    private SquareOscillator squareOscillator;
    private IOutput output;
    private Potentiometer potentiometer;

    public SquareOscillatorAdapter(IComponent component) {
        super(component);
        this.squareOscillator = new SquareOscillator();
        // Declare the relation to the mapping
        IOMapping.declare(component, this.squareOscillator);
        this.output = ModulesFactory.createOutput(component, squareOscillator.output);
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
        this.squareOscillator.setEnabled(true);
    }

    @Override
    public void desactivate() {
        this.squareOscillator.setEnabled(false);
    }
}
