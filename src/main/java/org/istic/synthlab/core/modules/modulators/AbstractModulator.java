package org.istic.synthlab.core.modules.modulators;

import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * Create an abstraction to manage a gain potentiometer throught a filterAllpass
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public abstract class AbstractModulator implements IModulator {
    protected final IComponent component;
    protected Potentiometer potentiometer;
    protected IOutput output;
    protected IInput input;
    private final String name;

    public AbstractModulator(String name, IComponent component) {
        this.name = name;
        this.component = component;
    }

    @Override
    public IInput getInput() {
        return input;
    }

    @Override
    public IOutput getOutput() {
        return output;
    }

    @Override
    public double getOriginalValue() {
        return potentiometer.getOriginalValue();
    }
    @Override
    public double getValue() {
        return potentiometer.getValue();
    }

    @Override
    public void setValue(double value) {
        potentiometer.setValue(value);
    }

    public String getName() {
        return this.name;
    }
    
    public Potentiometer getPotentiometer() {
        return potentiometer;
    }

    @Override
    public double getMax() {
        return potentiometer.getMax();
    }

    @Override
    public double getMin() {
        return potentiometer.getMin();
    }

    @Override
    public void setMin(double value) {
        potentiometer.setMin(value);
    }

    @Override
    public void setMax(double value) {
        potentiometer.setMax(value);
    }
}
