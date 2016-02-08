package org.istic.synthlab.core.modules.oscillators;

import org.istic.synthlab.core.IComponent;

/**
 * A pulse generator.
 *
 */
public class PulseOscillator extends AbstractOscillator {

    public PulseOscillator(IComponent component) {
        super(component, new com.jsyn.unitgen.PulseOscillator());
    }

}
