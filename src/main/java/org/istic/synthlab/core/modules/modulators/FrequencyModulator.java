package org.istic.synthlab.core.modules.modulators;

import com.jsyn.unitgen.Add;
import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * Create an abstraction to manage a frequency potentiometer through a filterAll-pass
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class FrequencyModulator extends AbstractModulator {
    private Add addFunction;

    public FrequencyModulator(String name, IComponent component, PotentiometerType potentiometerType) {
        super(name, component);
        addFunction = new Add();
        setPotentiometer(new Potentiometer(
                "Frequency",
                addFunction.inputB,
                potentiometerType,
                20000.0D,
                0.0D,
                0.0D
        ));

        // Declare the relation to the mapping
        Register.declare(component, this.addFunction);
        input = Factory.createInput(name + "::freqIn", component, addFunction.inputA);
        output = Factory.createOutput(name + "::freqOut", component, addFunction.output);
    }

    @Override
    public void activate() {
        addFunction.setEnabled(true);
    }

    @Override
    public void deactivate() {
        addFunction.setEnabled(false);
    }

    @Override
    public boolean isActivated() {
        return addFunction.isEnabled();
    }

}
