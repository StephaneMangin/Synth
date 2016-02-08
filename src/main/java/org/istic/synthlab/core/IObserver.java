package org.istic.synthlab.core;

import javafx.scene.shape.Line;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.ui.Connection;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sebastien
 */
public interface IObserver {
    void update(Map<IOutput,IInput> arg);
    void drawLine(HashMap<Line, Connection> arg);
    void unDrawLine(HashMap<Line, Connection> arg);
}
