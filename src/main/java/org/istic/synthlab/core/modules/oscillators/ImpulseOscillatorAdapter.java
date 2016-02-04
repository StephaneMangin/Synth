package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.ImpulseOscillator;
import org.istic.synthlab.core.services.ModulesFactory;
import org.istic.synthlab.core.modules.parametrization.Potentiometer;
import org.istic.synthlab.core.modules.parametrization.PotentiometerType;
import com.jsyn.unitgen.UnitGenerator;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

/**
 *
 */
public class ImpulseOscillatorAdapter implements IOscillator{

    private ImpulseOscillator impulseOscillator;
    private IOutput output;
    private Potentiometer potentiometer;

    public ImpulseOscillatorAdapter() {
        this.impulseOscillator = new ImpulseOscillator();
        this.output = ModulesFactory.createOutput(impulseOscillator.output);
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
    public UnitGenerator getUnitGenerator() {
        return this.impulseOscillator;
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
