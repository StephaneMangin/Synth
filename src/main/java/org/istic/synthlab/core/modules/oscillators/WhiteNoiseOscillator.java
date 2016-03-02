package org.istic.synthlab.core.modules.oscillators;

import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.utils.functions.WhiteNoise;

/**
 * Created by blacknight on 14/02/16.
 */
public class WhiteNoiseOscillator extends AbstractOscillator {

    public WhiteNoiseOscillator(IComponent component) {
        super(component, new WhiteNoise());
    }
}
