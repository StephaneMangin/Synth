package org.istic.synthlab.core.modules.filters;

import com.jsyn.unitgen.FilterLowPass;
import com.jsyn.unitgen.UnitFilter;
import org.istic.synthlab.core.IComponent;

/**
 *
 *
 * The adapter of the low pass filter Jsyn
 */
public class LowPassFilterAdapter implements IFilter {

    private UnitFilter unitFilter;

    /**
     * Instantiates a new Low pass filter adapter.
     * @param component
     */
    public LowPassFilterAdapter(IComponent component) {
        this.unitFilter = new FilterLowPass();
    }
}
