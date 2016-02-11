package org.istic.synthlab.core.modules.modulators;

import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.utils.jsyn.VcoaFunction;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;
import org.istic.synthlab.core.services.Register;

/**
 *
 * @author Thibaud Hulin <thibaud[dot]hulin[dot]cl[at]gmail[dot]com>
 */
public class VcoaFrequencyModulator extends AbstractModulator {
    private VcoaFunction algorithm;

    public VcoaFrequencyModulator(String name, IComponent component, PotentiometerType potentiometerType) {
        super(name, component);
        algorithm = new VcoaFunction();
        potentiometer = new Potentiometer(
                "Frequency",
                algorithm.potentiometer,
                potentiometerType,
                20000.0D,
                20.0D,
                1000.0D
        );

        // Declare the relation to the mapping
        Register.declare(component, this.algorithm);
        input = Factory.createInput(name + "::freqIn", component, algorithm.frequencyModulation);
        output = Factory.createOutput(name + "::freqOut", component, algorithm.output);
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
        algorithm.setEnabled(true);
    }

    @Override
    public void deactivate() {
        algorithm.setEnabled(false);
    }

    @Override
    public boolean isActivated() {
        return algorithm.isEnabled();
    }

}
