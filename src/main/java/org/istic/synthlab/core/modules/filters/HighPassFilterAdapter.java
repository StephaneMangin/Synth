package org.istic.synthlab.core.modules.filters;

import com.jsyn.unitgen.FilterHighPass;
import com.jsyn.unitgen.UnitFilter;
import org.istic.synthlab.core.IComponent;

/**
 *
 *
 * The type High pass filter adapter.
 */
public class HighPassFilterAdapter implements IFilter {

    private UnitFilter unitFilter;

    /**
     * Instantiates a new High pass filter adapter.
     * @param component
     */
    public HighPassFilterAdapter(IComponent component) {
        this.unitFilter = new FilterHighPass();
    }
}
