package org.istic.synthlab.core.modules.filters;

import com.jsyn.unitgen.FilterHighPass;
import com.jsyn.unitgen.FilterStateVariable;
import com.jsyn.unitgen.UnitFilter;
import org.istic.synthlab.components.IComponent;

/**
 * The adapter of the high pass filter Jsyn
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class HighPassFilter extends AbstractFilter {
    /**
     * Instantiates a new High pass filter adapter.
     * @param component
     */
    public HighPassFilter(IComponent component) {
        super(component, new FilterStateVariable(), FilterType.HIGHPASS);
    }
}
