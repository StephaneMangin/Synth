package org.istic.synthlab.core.modules.modulators;

import com.jsyn.ports.UnitInputPort;
import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * Create an abstraction to manage a potentiometer throught a filterAllpass
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
    public double getValue() {
        return potentiometer.getValue();
    }

    @Override
    public void setValue(double value) {
        potentiometer.setValue(calculateStep(value));
    }

    public double calculateStep(double wheelInput) {
        double value;
        if (potentiometer.getType() == PotentiometerType.LINEAR) {
            value = (potentiometer.getMax() - potentiometer.getMin()) * wheelInput + potentiometer.getMin();
        } else if (potentiometer.getType() == PotentiometerType.EXPONENTIAL) {
            //128 à la place de 10 ?
            value = (potentiometer.getMax() - potentiometer.getMin())
                    / Potentiometer.POWER_SCALE
                    * Math.pow(Potentiometer.POWER_SCALE, wheelInput)
                    + potentiometer.getMin();
        } else {
            value = wheelInput;
        }
        return value;
    }

    public double restoreStep(double wheelInput) {
        // TODO: code the revert calculateStep function
        return wheelInput;
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
}
