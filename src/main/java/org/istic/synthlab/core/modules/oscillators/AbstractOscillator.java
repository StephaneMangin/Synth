package org.istic.synthlab.core.modules.oscillators;

import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.services.IOMapping;

/**
 * Manage the IComponent relation for all Oscillators
 *
 * @author Stéphane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public abstract class AbstractOscillator implements IOscillator {

    private IComponent component;

    protected AbstractOscillator(IComponent component) {
        this.component = component;
    }

    public IComponent getComponent() {
        return component;
    }
}
