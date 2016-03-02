package org.istic.synthlab.core.modules.oscillators;

import org.istic.synthlab.core.components.IComponent;


/**
 * A sinusoidal oscillator.
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class SineOscillator extends AbstractOscillator {

    public SineOscillator(IComponent component) {
        super(component, new com.jsyn.unitgen.SineOscillator());
    }

}
