package org.istic.synthlab.core.modules.filters;

import com.jsyn.unitgen.FilterBandPass;
import com.jsyn.unitgen.UnitFilter;

/**
 * Created by stephane on 02/02/16.
 */
public class BandPassFilterAdapter implements IFilter {

    private UnitFilter unitFilter;

    public BandPassFilterAdapter() {
        this.unitFilter = new FilterBandPass();
    }
}
