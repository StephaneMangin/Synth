package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.RedNoise;
import org.istic.synthlab.core.IComponent;

/**
 * A triangle shape generator
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class RedNoiseOscillator extends AbstractOscillator {

    public RedNoiseOscillator(IComponent component) {
        super(component, new RedNoise());
    }

    @Override
    public boolean isActivated() {
        return getOscillator().isEnabled();
    }
}
