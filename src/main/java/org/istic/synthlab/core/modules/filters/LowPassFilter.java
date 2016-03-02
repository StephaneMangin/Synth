package org.istic.synthlab.core.modules.filters;

import com.jsyn.unitgen.FilterLowPass;
import com.jsyn.unitgen.FilterStateVariable;
import org.istic.synthlab.components.IComponent;

/**
 * The adapter of the low pass filter Jsyn
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class LowPassFilter extends AbstractFilterBiquad {
    /**
     * Instantiates a new Low pass filter adapter.
     * @param component
     */
    public LowPassFilter(IComponent component) {
        super(component, new FilterLowPass());
    }
}
