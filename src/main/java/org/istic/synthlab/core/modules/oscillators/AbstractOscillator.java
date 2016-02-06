package org.istic.synthlab.core.modules.oscillators;

import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;

/**
 * Manage the IComponent relation for all Oscillators
 *
 * @author Stéphane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
abstract class AbstractOscillator implements IOscillator {

    protected IComponent component;
    protected IInput am;
    protected IInput fm;
    protected IOutput output;
    protected Potentiometer frequencyPotentiometer;

    protected AbstractOscillator(IComponent component) {
        this.component = component;
    }

    public IComponent getComponent() {
        return component;
    }

    @Override
    public Potentiometer getFrequencyPotentiometer() {
        return this.frequencyPotentiometer;
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
}
