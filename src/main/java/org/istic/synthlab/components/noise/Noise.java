package org.istic.synthlab.components.noise;

import org.istic.synthlab.components.AbstractComponent;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.noise.IModelWhiteNoise;
import org.istic.synthlab.core.services.Factory;

/**
 * @author paola
 */
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

    @Override
    public IOutput getOutput(){
        return noise.getOutput();
    }
}
