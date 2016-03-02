package org.istic.synthlab.core.modules.filters;

import com.jsyn.unitgen.FilterStateVariable;
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
public class AbstractFilterStateVariable implements IFilter {
    private IComponent component;
    private IInput fm;
    private IInput input;
    private IOutput output;
    private Potentiometer resonancePotentiometer;

    private FilterStateVariable filter;

    protected AbstractFilterStateVariable(IComponent component, FilterStateVariable filter, FilterType filterType) {
        this.component = component;
        this.filter = filter;
        // Declare the generator to the register
        Register.declare(component, filter);
        // Link different ports
        fm = Factory.createInput("Fm", component, filter.frequency);
        input = Factory.createInput("In", component, filter.input);
        switch (filterType) {
            case ALLPASS:
                output = Factory.createOutput("Out", component, filter.bandPass);
                break;
            case HIGHPASS:
                output = Factory.createOutput("Out", component, filter.highPass);
                break;
            case LOWPASS:
            default:
                output = Factory.createOutput("Out", component, filter.lowPass);
        }

        resonancePotentiometer = new Potentiometer("Resonance", filter.resonance, PotentiometerType.LINEAR, 1.0, 0.0, 0.0);
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

    public IComponent getComponent() {
        return component;
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
}
