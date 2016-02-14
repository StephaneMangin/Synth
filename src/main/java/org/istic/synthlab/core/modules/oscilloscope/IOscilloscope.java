package org.istic.synthlab.core.modules.oscilloscope;

import com.jsyn.scope.swing.AudioScopeView;
import javafx.embed.swing.SwingNode;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

import javax.swing.*;

/**
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public interface IOscilloscope {

    /**
     * Returns a swing node representing the oscilloscope
     *
     * @return JPanel
     */
    JPanel getNode();

    /**
     * Set the oscilloscope amplitude length
     *
     * @param value
     */
    void setAmplitude(double value);

    /**
     * Set the oscilloscope period lngth
     *
     * @param value
     */
    void setPeriod(double value);

    IInput getInput();

    IOutput getOutput();
}
