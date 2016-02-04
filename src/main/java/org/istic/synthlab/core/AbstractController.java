package org.istic.synthlab.core;

/**
 * The type Abstract controller.
 *
 * @author Group1
 */
public abstract class AbstractController {

    private IComponent component;

    /**
     * Controller.
     *
     * @param component the component
     */
    public void Controller(IComponent component) {
        this.component = component;
    }

    /**
     * Gets component.
     *
     * @return the component
     */
    public IComponent getComponent() {
        return component;
    }
}
