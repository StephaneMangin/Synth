package org.istic.synthlab.core.modules.oscillators;

import org.istic.synthlab.core.IComponent;

/**
 * A triangle shape generator
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class TriangleOscillator extends AbstractOscillator {

    public TriangleOscillator(IComponent component) {
        super(component, new com.jsyn.unitgen.TriangleOscillator());
    }

    @Override
    public boolean isActivated() {
        return getOscillator().isEnabled();
    }
}
