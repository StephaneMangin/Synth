package org.istic.synthlab.core.modules.modulators;

import com.jsyn.unitgen.FilterStateVariable;
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
public class FrequencyModulatorAdapter implements IModulator {
    private Potentiometer potentiometer;
    private IOutput output;
    private IInput input;
    private FilterStateVariable filter;

    public FrequencyModulatorAdapter(IComponent component) {
        filter = new FilterStateVariable();
        potentiometer = new Potentiometer(
                "Gain",
                filter.frequency,
                PotentiometerType.EXPONENTIAL,
                20000.0,
                20.0,
                20.0
        );

        // Declare the relation to the mapping
        Register.declare(component, this.filter);
        input = ModulesFactory.createInput("In", component, filter.input);
        output = ModulesFactory.createOutput("Out", component, filter.output);
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
