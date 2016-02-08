package org.istic.synthlab.core.modules.oscillators;

import org.istic.synthlab.core.IComponent;

/**
 * A saw teeth generator.
 *
 */
public class SawtoothOscillator extends AbstractOscillator {

    public SawtoothOscillator(IComponent component) {
        super(component, new com.jsyn.unitgen.SawtoothOscillator());
    }
}
