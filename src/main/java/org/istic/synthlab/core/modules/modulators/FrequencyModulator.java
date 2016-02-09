package org.istic.synthlab.core.modules.modulators;

import com.jsyn.unitgen.Add;
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
public class FrequencyModulator extends AbstractModulator {
    private Add addFunction;

    public FrequencyModulator(String name, IComponent component, PotentiometerType potentiometerType) {
        super(name, component);
        addFunction = new Add();
        potentiometer = new Potentiometer(
                "Frequency",
                addFunction.inputB,
                potentiometerType,
                20000.0D,
                20.0D,
                440.0D
        );

        // Declare the relation to the mapping
        Register.declare(component, this.addFunction);
        input = Factory.createInput(name + "::freqIn", component, addFunction.inputA);
        output = Factory.createOutput(name + "::freqOut", component, addFunction.output);
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
