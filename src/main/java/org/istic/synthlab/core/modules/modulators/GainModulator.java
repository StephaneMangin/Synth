package org.istic.synthlab.core.modules.modulators;

import org.istic.synthlab.core.components.IComponent;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.utils.functions.GainFunction;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * Create an abstraction to manage a gain potentiometer through a function that compute a gain in decibel
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class GainModulator extends AbstractModulator {
    private GainFunction function;

    public GainModulator(String name, IComponent component, PotentiometerType potentiometerType) {
        super(name, component);
        function = new GainFunction();
        setPotentiometer(new Potentiometer(
                "Gain",
                function.potentiometer,
                potentiometerType,
                1D,
                0D,
                0.8D
        ));

        // Declare the relation to the mapping
        Register.declare(component, this.function);
        input = Factory.createInput(name + "::gainIn", component, function.input);
        output = Factory.createOutput(name + "::gainOut", component, function.output);
    }

    @Override
    public void activate() {
        function.setEnabled(true);
    }

    @Override
    public void deactivate() {
        function.setEnabled(false);
    }

    @Override
    public boolean isActivated() {
        return function.isEnabled();
    }

}
