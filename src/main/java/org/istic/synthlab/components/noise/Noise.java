package org.istic.synthlab.components.noise;

import org.istic.synthlab.components.AbstractComponent;
import org.istic.synthlab.core.modules.noise.IModelWhiteNoise;
import org.istic.synthlab.core.services.Factory;

/**
 * @author  Ngassam Noumi Paola npaolita[at]yahoo[dot]fr
 */

/**
 * The model of White noise component
 * It creates a  white noise module to produce a signal conforming to the definition of white noise.
 * */
public class Noise extends AbstractComponent  {

    private IModelWhiteNoise noise;

    /**
     * Instantiates a new component.
     *
     * @param name the name
     */
    public Noise(String name) {
        super(name);
        noise = Factory.createWhiteNoise(this);
        noise.getOutput().connect(getSink());
    }

    @Override
    public boolean isActivated(){
        return noise.isActivated();
    }

    @Override
    public void activate() {
        noise.activate();
    }

    @Override
    public void deactivate() {
        noise.deactivate();
    }

}
