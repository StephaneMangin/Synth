package org.istic.synthlab.core;

/**
 * Created by stephane on 02/02/16.
 */
public abstract class AbstractController {

    private IComponent component;

    public void Controller(IComponent component) {
        this.component = component;
    }

    public IComponent getComponent() {
        return component;
    }
}
