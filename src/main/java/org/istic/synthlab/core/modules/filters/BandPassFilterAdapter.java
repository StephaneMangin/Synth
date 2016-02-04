package org.istic.synthlab.core.modules.filters;

import com.jsyn.unitgen.FilterBandPass;
import com.jsyn.unitgen.UnitFilter;

/**
 * @author Group1
 *
 * The type Band pass filter adapter.
 *
 */
public class BandPassFilterAdapter implements IFilter {

    private UnitFilter unitFilter;

    /**
     * Instantiates a new Band pass filter adapter.
     */
    public BandPassFilterAdapter() {
        this.unitFilter = new FilterBandPass();
    }
}
