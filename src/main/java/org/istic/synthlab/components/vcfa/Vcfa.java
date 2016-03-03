package org.istic.synthlab.components.vcfa;

import org.istic.synthlab.components.AbstractComponent;
import org.istic.synthlab.core.modules.filters.FilterType;
import org.istic.synthlab.core.modules.filters.IFilter;
import org.istic.synthlab.core.modules.modulators.IModulator;
import org.istic.synthlab.core.modules.modulators.ModulatorType;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * this class represents the VCF (Voltage Controlled Filter) module type A (Lowpass) or LP24
 *
 * It keeps a range of frequencies in a signal under a threshold frequency.
 * @author Thibaud Hulin
 */
public class Vcfa extends AbstractComponent {
    private IFilter filter = Factory.createFilter(this, FilterType.LOWPASS);

    private IModulator cutOffModulator = Factory.createModulator("Cutoff Modulator", this, ModulatorType.VCOA, PotentiometerType.EXPONENTIAL);
    /**
     * Instantiates a new VCFA.
     *
     * @param name: String
     */
    public Vcfa(String name) {
        super(name);
        getSourceFm().connect(cutOffModulator.getInput());
        cutOffModulator.getOutput().connect(filter.getFm());
        filter.getInput().connect(getSource());
        filter.getOutput().connect(getSink());
    }

    @Override
    public void activate() {
        filter.activate();
    }

    @Override
    public void deactivate() {
        filter.deactivate();
    }

    @Override
    public boolean isActivated() {
        return filter.isActivated();
    }

    public void setCutoff(double value) {
        cutOffModulator.setValue(value);
    }

    public void setResonance(double value) {
        filter.getResonancePotentiometer().setValue(value);
    }

    public double getCutoff() {
        return cutOffModulator.getValue();
    }

    public double getResonance() {
        return filter.getResonancePotentiometer().getValue();
    }
}
