package org.istic.synthlab.core.modules.modulators;

import com.jsyn.unitgen.PassThrough;
import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * Create an abstraction to manage an bypass through a PassThrough
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class ByPassModulator extends AbstractModulator {
    private PassThrough bypass;

    public ByPassModulator(String name, IComponent component, PotentiometerType potentiometerType) {
        super(name, component);
        bypass = new PassThrough();

        // Declare the relation to the mapping
        Register.declare(component, bypass);
        input = Factory.createInput(name + "::bpIn", component, bypass.input);
        output = Factory.createOutput(name + "::bpOut", component, bypass.output);
    }

    @Override
    public void activate() {
        bypass.setEnabled(true);
    }

    @Override
    public void deactivate() {
        bypass.setEnabled(false);
    }

    @Override
    public boolean isActivated() {
        return bypass.isEnabled();
    }

}
