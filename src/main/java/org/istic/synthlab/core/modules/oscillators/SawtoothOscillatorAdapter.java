package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.SawtoothOscillator;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.parametrization.ValueType;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.services.ModulesFactory;
import org.istic.synthlab.core.modules.parametrization.Potentiometer;
import org.istic.synthlab.core.modules.parametrization.PotentiometerType;

/**
 * A saw teeth generator.
 *
 */
public class SawtoothOscillatorAdapter extends AbstractOscillator {

    private SawtoothOscillator sawtoothOscillator;

    /*
    * the constructor
    */
    public SawtoothOscillatorAdapter(IComponent component) {
        super(component);
        this.sawtoothOscillator = new SawtoothOscillator();
        // Declare the relation to the register
        Register.declare(component, this.sawtoothOscillator);
        this.output = ModulesFactory.createOutput(component, sawtoothOscillator.output);
        this.amplitudePotentiometer = new Potentiometer("Amplitude", PotentiometerType.EXPONENTIAL, 20000.0, 20.0, 320.0);

    }
    @Override
    public void setAmplitude(double value) {
        sawtoothOscillator.amplitude.set(value);
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
