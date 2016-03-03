package org.istic.synthlab.core.modules.keyboard;

import org.istic.synthlab.core.Resource;
import org.istic.synthlab.core.modules.IModule;
import org.istic.synthlab.core.modules.io.IOutput;

/**
 * @author  Dechaud John Marc johnmarcdechaud[at]gmail[dot]com on 3/2/16.
 */
public interface IKeyboard extends IModule, Resource{

    /**
     * Get the CV output port
     * @return IOutput
     * */
    IOutput getOutputCV();

    /**
     * Get the gate output port
     * @return IOutput
     * */
    IOutput getOutputGate();

    /**
     * Play the note
     * */
    void note(NOTE note);
}
