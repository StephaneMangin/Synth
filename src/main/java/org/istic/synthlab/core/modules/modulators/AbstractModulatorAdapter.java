package org.istic.synthlab.core.modules.modulators;

import com.jsyn.unitgen.FilterBandPass;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.ModulesFactory;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * Create an abstraction to manage a gain potentiometer throught a filterAllpass
 *
 * @author Stéphane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public abstract class AbstractModulatorAdapter implements IModulator {
    private Potentiometer potentiometer;
    private IOutput output;
    private IInput input;

    public AbstractModulatorAdapter(IComponent component) {
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
}
