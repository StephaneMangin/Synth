package org.istic.synthlab.core.modules.modulators;

import org.istic.synthlab.components.IComponent;
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
        setPotentiometer(new Potentiometer(
                "Frequency",
                algorithm.potentiometer,
                potentiometerType,
                10.0D,
                0.0D,
                0.0D
        ));

        // Declare the relation to the mapping
        Register.declare(component, this.algorithm);
        input = Factory.createInput(name + "::freqIn", component, algorithm.frequencyModulation);
        output = Factory.createOutput(name + "::freqOut", component, algorithm.output);
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
