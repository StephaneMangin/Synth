package org.istic.synthlab.components.rednoise;

import org.istic.synthlab.components.AbstractComponent;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.oscillators.IOscillator;
import org.istic.synthlab.core.modules.whitenoise.IWhiteNoise;
import org.istic.synthlab.core.services.Factory;

/**
 * @author  Ngassam Noumi Paola npaolita[at]yahoo[dot]fr
 */

/**
 * The model of White noise component
 * It creates a  white noise module to produce a signal conforming to the definition of white noise.
 * */
public class RedNoise extends AbstractComponent  {

    private IOscillator noise;

    /**
     * Instantiates a new component.
     *
     * @param name the name
     */
    public RedNoise(String name) {
        super(name);
        noise = Factory.createRedNoise(this);
        noise.getAmplitudePotentiometer().setValue(1);
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
