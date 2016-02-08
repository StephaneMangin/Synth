package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.UnitOscillator;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * Manage the IComponent relation for all Oscillators
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
abstract class AbstractOscillator implements IOscillator {

    private IComponent component;
    private IInput am;
    private IInput fm;
    private IOutput output;
    private Potentiometer frequencyPotentiometer;
    private Potentiometer amplitudePotentiometer;
    private UnitOscillator unitOscillator;

    protected AbstractOscillator(IComponent component, UnitOscillator unitOscillator) {
        this.component = component;
        this.unitOscillator = unitOscillator;
        // Declare the generator to the register
        Register.declare(component, unitOscillator);
        // Link differents ports
        this.fm = Factory.createInput("Fm", component, getOscillator().frequency);
        this.am = Factory.createInput("Am", component, getOscillator().amplitude);
        this.output = Factory.createOutput("Out", component, getOscillator().output);
        // Link input to the frequency input of the oscillator to modulate it with the input signal
        this.frequencyPotentiometer = new Potentiometer("Frequency", getOscillator().frequency, PotentiometerType.EXPONENTIAL, 20000.0, 20.0, 1000.0);
        this.amplitudePotentiometer = new Potentiometer("Frequency", getOscillator().frequency, PotentiometerType.LINEAR, 20000.0, 20.0, 1000.0);
    }

    @Override
    public void activate() {
        getOscillator().setEnabled(true);
    }

    @Override
    public void desactivate() {
        getOscillator().setEnabled(false);
    }

    public IComponent getComponent() {
        return component;
    }

    public UnitOscillator getOscillator() {
        return unitOscillator;
    }

    @Override
    public IInput getFm() {
        return this.fm;
    }

    @Override
    public IInput getAm() {
        return this.am;
    }

    @Override
    public IOutput getOutput() {
        return this.output;
    }

    @Override
    public void setFrequency(double value) {
        frequencyPotentiometer.setValue(value);
    }

    @Override
    public double getFrequency() {
        return frequencyPotentiometer.getValue();
    }

    @Override
    public void setAmplitude(double value) {
        amplitudePotentiometer.setValue(value);
    }

    @Override
    public double getAmplitude() {
        return amplitudePotentiometer.getValue();
    }

}
