package org.istic.synthlab.core.modules.filters;

import com.jsyn.unitgen.FilterLowPass;
import com.jsyn.unitgen.UnitFilter;

/**
 * @author Group1
 *
 * The adapter of the low pass filter Jsyn
 */
public class LowPassFilterAdapter implements IFilter {

    private UnitFilter unitFilter;

    /**
     * Instantiates a new Low pass filter adapter.
     */
    public LowPassFilterAdapter() {
        this.unitFilter = new FilterLowPass();
    }
}
