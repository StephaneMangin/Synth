package org.istic.synthlab.core.modules.oscillators;

import org.istic.synthlab.core.IComponent;

/**
 * An impulse shape generator.
 *
 */
public class ImpulseOscillator extends AbstractOscillator {

    public ImpulseOscillator(IComponent component) {
        super(component, new com.jsyn.unitgen.ImpulseOscillator());

    }
}
