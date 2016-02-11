package org.istic.synthlab.core.modules.filters;

import com.jsyn.unitgen.FilterBandPass;
import com.jsyn.unitgen.UnitFilter;
import org.istic.synthlab.core.IComponent;

/**
 * The type Band pass filter adapter.
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 *
 */
public class BandPassFilter implements IFilter {

    private UnitFilter unitFilter;

    /**
     * Instantiates a new Band pass filter adapter.
     * @param component
     */
    public BandPassFilter(IComponent component) {
        this.unitFilter = new FilterBandPass();
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
