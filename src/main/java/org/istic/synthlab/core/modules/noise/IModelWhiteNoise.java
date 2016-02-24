package org.istic.synthlab.core.modules.noise;

import org.istic.synthlab.core.Resource;
import org.istic.synthlab.core.modules.IModule;
import org.istic.synthlab.core.modules.io.IOutput;

/**
 * Created by paola on 22/02/16.
 */
public interface IModelWhiteNoise extends IModule, Resource {

    /**
     * Return the output of the white noise
     *
     * @return IOutput
     */
    IOutput getOutput();

}
