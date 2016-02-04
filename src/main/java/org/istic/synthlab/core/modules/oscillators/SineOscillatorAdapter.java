package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.SineOscillator;
import org.istic.synthlab.core.services.ModulesFactory;
import org.istic.synthlab.core.modules.parametrization.Potentiometer;
import org.istic.synthlab.core.modules.parametrization.PotentiometerType;
import com.jsyn.unitgen.UnitGenerator;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;


public class SineOscillatorAdapter implements IOscillator {

    private SineOscillator oscillator;
    private IOutput output;
    private Potentiometer potentiometer;

    public SineOscillatorAdapter() {
        this.oscillator = new SineOscillator();
        this.output = ModulesFactory.createOutput(oscillator.output);
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
        return potentiometer;
    }

    @Override
    public UnitGenerator getUnitGenerator() {
        return this.oscillator;
    }

    @Override
    public void activate() {
        this.oscillator.setEnabled(true);
    }

    @Override
    public void desactivate() {
        this.oscillator.setEnabled(false);
    }
}
