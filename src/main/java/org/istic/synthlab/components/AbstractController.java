package org.istic.synthlab.components;

/**
 * The type Abstract controller.
 *
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
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
