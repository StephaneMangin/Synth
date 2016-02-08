package org.istic.synthlab.core.modules.modulators;

import com.jsyn.unitgen.FilterBandPass;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;
import org.istic.synthlab.core.services.Register;

/**
 * Create an abstraction to manage a gain potentiometer throught a filterAllpass
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class AmplitudeModulator extends AbstractModulator {
    private FilterBandPass filter;

    public AmplitudeModulator(String name, IComponent component, PotentiometerType potentiometerType) {
        super(name, component, potentiometerType);
        filter = new FilterBandPass();
        potentiometer = new Potentiometer("Amplitude", filter.amplitude, PotentiometerType.LINEAR,
                10E5D, 0D, 1D
        );

        // Declare the relation to the mapping
        Register.declare(component, this.filter);
        input = Factory.createInput(name + "::ampIn", component, filter.input);
        output = Factory.createOutput(name + "::ampOut", component, filter.output);
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
