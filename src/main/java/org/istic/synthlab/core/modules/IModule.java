package org.istic.synthlab.core.modules;

/**
 * Created by blacknight on 15/02/16.
 */
public interface IModule {

    void activate();

    void deactivate();

    boolean isActivated();

}
