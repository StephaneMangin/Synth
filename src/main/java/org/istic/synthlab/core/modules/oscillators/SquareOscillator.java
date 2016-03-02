package org.istic.synthlab.core.modules.oscillators;

import org.istic.synthlab.core.components.IComponent;

/**
 * A square shape generator.
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class SquareOscillator extends AbstractOscillator {

    public SquareOscillator(IComponent component) {
        super(component, new com.jsyn.unitgen.SquareOscillator());
    }

}
