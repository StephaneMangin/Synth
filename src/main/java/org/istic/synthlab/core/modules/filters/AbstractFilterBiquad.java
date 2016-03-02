package org.istic.synthlab.core.modules.filters;

import com.jsyn.unitgen.FilterBiquadCommon;
import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 *
 */
public class AbstractFilterBiquad implements IFilter {
    private IComponent component;
    private IInput fm;
    private IInput input;
    private IOutput output;
    private Potentiometer resonancePotentiometer;

    private FilterBiquadCommon filter;

    protected AbstractFilterBiquad(IComponent component, FilterBiquadCommon filter) {
        this.component = component;
        this.filter = filter;
        // Declare the generator to the register
        Register.declare(component, filter);
        // Link different ports
        fm = Factory.createInput("Fm", component, filter.frequency);
        input = Factory.createInput("In", component, filter.input);
        output = Factory.createOutput("Out", component, filter.output);

        resonancePotentiometer = new Potentiometer("Resonance", filter.Q, PotentiometerType.LINEAR, 10.0, 0.0, 0.0);
    }
    @Override
    public IInput getFm() {
        return fm;
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
    public Potentiometer getResonancePotentiometer() {
        return resonancePotentiometer;
    }

    @Override
    public void activate() {
        filter.setEnabled(true);
    }

    @Override
    public void deactivate() {
        filter.setEnabled(false);
    }

    @Override
    public boolean isActivated() {
        return filter.isEnabled();
    }
}
