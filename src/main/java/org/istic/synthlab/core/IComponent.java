package org.istic.synthlab.core;

/**
 * Created by stephane on 02/02/16.
 */
public interface IComponent {

    void activate();
    void deactivate();
    void init();
    void run();

}
