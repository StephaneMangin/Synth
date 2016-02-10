package org.istic.synthlab.core;

import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.ui.Connection;
import org.istic.synthlab.ui.plugins.cable.OurCubicCurve;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sebastien
 */
public interface IObserver {
    void update(Map<IOutput,IInput> arg);
    void drawLine(HashMap<OurCubicCurve, Connection> arg);
    void unDrawLine(HashMap<OurCubicCurve, Connection> arg);
}
