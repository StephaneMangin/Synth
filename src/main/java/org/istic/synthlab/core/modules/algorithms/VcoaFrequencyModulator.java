package org.istic.synthlab.core.modules.algorithms;

import org.istic.synthlab.core.IComponent;
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
public class VcoaFrequencyModulator implements IVcoaFrequencyModulator {
    private final Potentiometer potentiometer;
    private IOutput output;
    private IInput input;
    private VcoaFrequencyModulatorGenerator algorithm;

    public VcoaFrequencyModulator(IComponent component) {
        this.algorithm = new VcoaFrequencyModulatorGenerator();
        this.potentiometer = new Potentiometer("Frequency", algorithm.potentiometer, PotentiometerType.EXPONENTIAL, 20000.0, 20.0, 320.0);

        // Declare the relation to the mapping
        Register.declare(component, this.algorithm);
        this.output = Factory.createOutput("Out", component, algorithm.output);
        this.input = Factory.createInput("In", component, algorithm.frequencyModulation);
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
    public Potentiometer getFrequency() {
        return potentiometer;
    }

    @Override
    public void setFrequency(double value) {
        algorithm.potentiometer.set(value);
    }

}
