package org.istic.synthlab.core.modules.modulators;

import com.jsyn.unitgen.Multiply;
import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * Create an abstraction to manage an amplitude potentiometer throught a filterAllpass
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class AmplitudeModulator extends AbstractModulator {
    private Multiply multiply;

    public AmplitudeModulator(String name, IComponent component, PotentiometerType potentiometerType) {
        super(name, component);
        multiply = new Multiply();
        potentiometer = new Potentiometer("Amplitude", multiply.inputB, potentiometerType,
                1D, 0D, 1D
        );

        // Declare the relation to the mapping
        Register.declare(component, this.multiply);
        input = Factory.createInput(name + "::ampIn", component, multiply.inputA);
        output = Factory.createOutput(name + "::ampOut", component, multiply.output);
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
        potentiometer.setValue(value);
    }

    @Override
    public void activate() {
        multiply.setEnabled(true);
    }

    @Override
    public void deactivate() {
        multiply.setEnabled(false);
    }

    @Override
    public boolean isActivated() {
        return multiply.isEnabled();
    }

}
