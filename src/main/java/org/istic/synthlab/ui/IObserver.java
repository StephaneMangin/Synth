package org.istic.synthlab.ui;

import javafx.scene.shape.Line;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sebastien
 */
public interface IObserver {
    void update(Map<IOutput,IInput> arg);
    void drawLine(HashMap<Line, HashMap<IOutput, IInput>> arg);
}
