package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.TriangleOscillator;
import org.istic.synthlab.core.AdapterFactory;
import org.istic.synthlab.core.Potentiometer;
import org.istic.synthlab.core.PotentiometerType;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

/**
 * @author Group1
 *
 * The adapter TriangleOscillator
 *
 */
public class TriangleOscillatorAdapter implements IOscillator{

    private TriangleOscillator triangleOscillator;
    private IOutput output;
    private Potentiometer potentiometer;

    /**
     * Instantiates a new Triangle oscillator adapter.
     */
    public TriangleOscillatorAdapter() {
        this.triangleOscillator = new TriangleOscillator();
        this.output = AdapterFactory.createOutput(triangleOscillator.output);
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
