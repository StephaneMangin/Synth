package org.istic.synthlab.core.modules.modulators;

import com.jsyn.unitgen.FilterAllPass;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * Create an abstraction to manage a gain potentiometer throught a filterAllpass
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class GainModulator extends AbstractModulator {
    private FilterAllPass filter;

    public GainModulator(String name, IComponent component) {
        super(name, component);
        filter = new FilterAllPass();
        potentiometer = new Potentiometer(
                "Gain",
                filter.gain,
                PotentiometerType.LINEAR,
                1D,
                0D,
                0.8D
        );

        // Declare the relation to the mapping
        Register.declare(component, this.filter);
        input = Factory.createInput(name + "::gainIn", component, filter.input);
        output = Factory.createOutput(name + "::gainOut", component, filter.output);
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

}
