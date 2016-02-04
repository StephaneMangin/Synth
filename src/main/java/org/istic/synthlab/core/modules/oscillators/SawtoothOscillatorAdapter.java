package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.SawtoothOscillator;
import com.jsyn.unitgen.UnitGenerator;
import org.istic.synthlab.core.services.ModulesFactory;
import org.istic.synthlab.core.modules.parametrization.Potentiometer;
import org.istic.synthlab.core.modules.parametrization.PotentiometerType;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

/**
 * @author Group1
 *
 * The adapter  SawtoothOscillator
 *
 */
public class SawtoothOscillatorAdapter implements IOscillator {

    private SawtoothOscillator sawtoothOscillator;
    private IOutput output;
    private Potentiometer potentiometer;

    /*
    * the constructor
    */
    public SawtoothOscillatorAdapter() {
        this.sawtoothOscillator = new SawtoothOscillator();
        this.output = ModulesFactory.createOutput(sawtoothOscillator.output);
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
        return this.getUnitGenerator();
    }

    @Override
    public void activate() {
        this.sawtoothOscillator.setEnabled(true);
    }

    @Override
    public void desactivate() {
        this.sawtoothOscillator.setEnabled(false);
    }
}
