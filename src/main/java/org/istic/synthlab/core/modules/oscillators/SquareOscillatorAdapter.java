package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.SquareOscillator;
import org.istic.synthlab.core.AdapterFactory;
import org.istic.synthlab.core.Potentiometer;
import org.istic.synthlab.core.PotentiometerType;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

/**
 * @author  Group1
 * the adapter class SquareOscillator
 */
public class SquareOscillatorAdapter implements IOscillator{

    private SquareOscillator squareOscillator;
    private IOutput output;
    private Potentiometer potentiometer;

    /**
     * Instantiates a new Square oscillator adapter.
     */
    public SquareOscillatorAdapter() {
        this.squareOscillator = new SquareOscillator();
        this.output = AdapterFactory.createOutput(squareOscillator.output);
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
