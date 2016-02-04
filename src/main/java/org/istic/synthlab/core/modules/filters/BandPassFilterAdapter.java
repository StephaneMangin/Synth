package org.istic.synthlab.core.modules.filters;

import com.jsyn.unitgen.FilterBandPass;
import com.jsyn.unitgen.UnitFilter;
import org.istic.synthlab.core.IComponent;

/**
 *
 *
 * The type Band pass filter adapter.
 *
 */
public class BandPassFilterAdapter implements IFilter {

    private UnitFilter unitFilter;

    /**
     * Instantiates a new Band pass filter adapter.
     * @param component
     */
    public BandPassFilterAdapter(IComponent component) {
        this.unitFilter = new FilterBandPass();
    }
}
