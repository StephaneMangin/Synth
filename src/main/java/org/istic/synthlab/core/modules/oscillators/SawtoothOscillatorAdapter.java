package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.SawtoothOscillator;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.modulators.ModulatorType;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.services.ModulesFactory;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

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
        // Link input to the frequency input of the oscillator to modulate it with the input signal
        this.fm = ModulesFactory.createInput(component, sawtoothOscillator.frequency);
        this.am = ModulesFactory.createInput(component, sawtoothOscillator.amplitude);
        this.output = ModulesFactory.createOutput(component, sawtoothOscillator.output);
        this.frequencyPotentiometer = new Potentiometer("Frequency", sawtoothOscillator.frequency, PotentiometerType.EXPONENTIAL, 20000.0, 20.0, 1000.0);

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
