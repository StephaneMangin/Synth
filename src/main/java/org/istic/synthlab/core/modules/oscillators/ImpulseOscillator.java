package org.istic.synthlab.core.modules.oscillators;

import org.istic.synthlab.components.IComponent;

/**
 * An impulse shape generator.
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class ImpulseOscillator extends AbstractOscillator {

    public ImpulseOscillator(IComponent component) {
        super(component, new com.jsyn.unitgen.ImpulseOscillator());
    }

}
