package org.istic.synthlab.core.modules.filters;

import org.istic.synthlab.core.Resource;
import org.istic.synthlab.core.modules.IModule;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;

/**
 * The interface filter
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public interface IFilter extends IModule, Resource {
    IInput getFm();

    IInput getInput();

    IOutput getOutput();

    Potentiometer getResonancePotentiometer();
}
