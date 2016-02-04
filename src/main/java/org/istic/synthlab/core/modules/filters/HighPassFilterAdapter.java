package org.istic.synthlab.core.modules.filters;

import com.jsyn.unitgen.FilterHighPass;
import com.jsyn.unitgen.UnitFilter;

/**
 * @author Group1
 *
 * The type High pass filter adapter.
 */
public class HighPassFilterAdapter implements IFilter {

    private UnitFilter unitFilter;

    /**
     * Instantiates a new High pass filter adapter.
     */
    public HighPassFilterAdapter() {
        this.unitFilter = new FilterHighPass();
    }
}
