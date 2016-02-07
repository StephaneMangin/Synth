package org.istic.synthlab.core.modules.oscillators;

import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * A saw teeth generator.
 *
 */
public class SawtoothOscillator extends AbstractOscillator {

    private com.jsyn.unitgen.SawtoothOscillator sawtoothOscillator;

    /*
    * the constructor
    */
    public SawtoothOscillator(IComponent component) {
        super(component);
        this.sawtoothOscillator = new com.jsyn.unitgen.SawtoothOscillator();
        // Declare the relation to the register
        Register.declare(component, this.sawtoothOscillator);
        // Link input to the frequency input of the oscillator to modulate it with the input signal
        this.fm = Factory.createInput("Fm", component, sawtoothOscillator.frequency);
        this.am = Factory.createInput("Am", component, sawtoothOscillator.amplitude);
        this.output = Factory.createOutput("Out", component, sawtoothOscillator.output);
        this.frequencyPotentiometer = new Potentiometer("Frequency", sawtoothOscillator.frequency, PotentiometerType.EXPONENTIAL, 20000.0D, 20.0D, 1000.0D);

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
