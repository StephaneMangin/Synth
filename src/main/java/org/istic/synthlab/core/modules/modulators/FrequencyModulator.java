package org.istic.synthlab.core.modules.modulators;

import com.jsyn.unitgen.Multiply;
import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * Create an abstraction to manage a frequency potentiometer throught a filterAllpass
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class FrequencyModulator extends AbstractModulator {
    private Multiply multiplyFunction;

    public FrequencyModulator(String name, IComponent component, PotentiometerType potentiometerType) {
        super(name, component);
        multiplyFunction = new Multiply();
        setPotentiometer(new Potentiometer(
                "Frequency",
                multiplyFunction.inputB,
                potentiometerType,
                20000.0D,
                0.0D,
                0.0D
        ));

        // Declare the relation to the mapping
        Register.declare(component, this.multiplyFunction);
        input = Factory.createInput(name + "::freqIn", component, multiplyFunction.inputA);
        output = Factory.createOutput(name + "::freqOut", component, multiplyFunction.output);
    }

    @Override
    public void activate() {
        multiplyFunction.setEnabled(true);
    }

    @Override
    public void deactivate() {
        multiplyFunction.setEnabled(false);
    }

    @Override
    public boolean isActivated() {
        return multiplyFunction.isEnabled();
    }

}
