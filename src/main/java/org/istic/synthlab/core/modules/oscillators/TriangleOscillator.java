package org.istic.synthlab.core.modules.oscillators;

import org.istic.synthlab.core.IComponent;

/**
 * A triangle shape generator
 *
 */
public class TriangleOscillator extends AbstractOscillator {

    public TriangleOscillator(IComponent component) {
        super(component, new com.jsyn.unitgen.TriangleOscillator());
    }
}
