package org.istic.synthlab.components;

/**
 * @author  Group1
 *
 * The interface Component
 */
public interface IComponent {

    void activate();
    void deactivate();
    boolean isActivated();
    void init();
    void run();

}
