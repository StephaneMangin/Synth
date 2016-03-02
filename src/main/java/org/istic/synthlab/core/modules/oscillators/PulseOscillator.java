package org.istic.synthlab.core.modules.oscillators;

import org.istic.synthlab.core.components.IComponent;

/**
 * A pulse generator.
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class PulseOscillator extends AbstractOscillator {

    public PulseOscillator(IComponent component) {
        super(component, new com.jsyn.unitgen.PulseOscillator());
    }

}
