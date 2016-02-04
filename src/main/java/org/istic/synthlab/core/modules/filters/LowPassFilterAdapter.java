package org.istic.synthlab.core.modules.filters;

import com.jsyn.unitgen.FilterLowPass;
import com.jsyn.unitgen.UnitFilter;

/**
 * Created by stephane on 02/02/16.
 */
public class LowPassFilterAdapter implements IFilter {

    private UnitFilter unitFilter;

    public LowPassFilterAdapter() {
        this.unitFilter = new FilterLowPass();
    }
}
