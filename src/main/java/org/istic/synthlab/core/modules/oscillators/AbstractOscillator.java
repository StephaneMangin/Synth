package org.istic.synthlab.core.modules.oscillators;

import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.parametrization.Potentiometer;

/**
 * Manage the IComponent relation for all Oscillators
 *
 * @author Stéphane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
abstract class AbstractOscillator implements IOscillator {

    protected IComponent component;
    protected IInput input;
    protected IOutput output;
    protected Potentiometer amplitudePotentiometer;

    protected AbstractOscillator(IComponent component) {
        this.component = component;
    }

    public IComponent getComponent() {
        return component;
    }

    @Override
    public Potentiometer getAmplitudePotentiometer() {
        return this.amplitudePotentiometer;
    }

    @Override
    public IInput getInput() {
        return this.input;
    }

    @Override
    public IOutput getOutput() {
        return this.output;
    }
}
