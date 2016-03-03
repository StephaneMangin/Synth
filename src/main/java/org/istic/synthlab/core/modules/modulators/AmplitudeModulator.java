package org.istic.synthlab.core.modules.modulators;

import com.jsyn.unitgen.Multiply;
import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * Create an abstraction to manage an amplitude potentiometer through a filter all-pass
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class AmplitudeModulator extends AbstractModulator {
    private Multiply multiply;

    public AmplitudeModulator(String name, IComponent component, PotentiometerType potentiometerType) {
        super(name, component);
        multiply = new Multiply();
        // Declare the relation to the mapping
        Register.declare(component, this.multiply);
        input = Factory.createInput(name + "::ampIn", component, multiply.inputA);
        output = Factory.createOutput(name + "::ampOut", component, multiply.output);
        setPotentiometer(new Potentiometer("Amplitude", multiply.inputB, potentiometerType,
                1, 0, 1
        ));

        // WARNING :
        // By setting the initial value of the amplitude Modulator, you are modifying the initial value
        // of TWO modulator on any component, at both sourceAm and output (which is also using
        // an amplitudeModulator
    }

    @Override
    public void activate() {
        multiply.setEnabled(true);
    }

    @Override
    public void deactivate() {
        multiply.setEnabled(false);
    }

    @Override
    public boolean isActivated() {
        return multiply.isEnabled();
    }

}
