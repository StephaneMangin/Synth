package org.istic.synthlab.core.modules.oscillators;

import org.istic.synthlab.core.IComponent;

/**
 * A square shape generator.
 *
 */
public class SquareOscillator extends AbstractOscillator {

    public SquareOscillator(IComponent component) {
        super(component, new com.jsyn.unitgen.SquareOscillator());
    }
}
