package org.istic.synthlab.core;

import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

import java.util.Map;

/**
 * Created by seb on 03/02/16.
 */
public interface IObserver {
    void update(Map<IOutput,IInput> arg);
}
