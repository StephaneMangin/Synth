package org.istic.synthlab.core.modules.filters;

import com.jsyn.unitgen.FilterHighPass;
import com.jsyn.unitgen.UnitFilter;
import org.istic.synthlab.components.IComponent;

/**
 * The type High pass filter adapter.
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class HighPassFilter implements IFilter {

    private UnitFilter unitFilter;

    /**
     * Instantiates a new High pass filter adapter.
     * @param component
     */
    public HighPassFilter(IComponent component) {
        this.unitFilter = new FilterHighPass();
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
