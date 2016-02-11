package org.istic.synthlab.core.modules.filters;

import com.jsyn.unitgen.FilterLowPass;
import com.jsyn.unitgen.UnitFilter;
import org.istic.synthlab.components.IComponent;

/**
 * The adapter of the low pass filter Jsyn
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class LowPassFilter implements IFilter {

    private UnitFilter unitFilter;

    /**
     * Instantiates a new Low pass filter adapter.
     * @param component
     */
    public LowPassFilter(IComponent component) {
        this.unitFilter = new FilterLowPass();
    }

    @Override
    public void activate() {
        unitFilter.setEnabled(true);
    }

    @Override
    public void deactivate() {
        unitFilter.setEnabled(false);
    }

    @Override
    public boolean isActivated() {
        return unitFilter.isEnabled();
    }
}
