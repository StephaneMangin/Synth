package org.istic.synthlab.core.modules.filters;

import com.jsyn.unitgen.FilterHighPass;
import com.jsyn.unitgen.UnitFilter;

/**
 * Created by stephane on 02/02/16.
 */
public class HighPassFilterAdapter implements IFilter {

    private UnitFilter unitFilter;

    public HighPassFilterAdapter() {
        this.unitFilter = new FilterHighPass();
    }
}
