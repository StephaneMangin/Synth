package org.istic.synthlab.core.modules.visualizer;

import org.istic.synthlab.core.modules.io.IOutput;

import javax.swing.*;

/**
 * Created by stephane on 08/02/16.
 */
public interface IVisualizer {

    /**
     * Return the view representation
     *
     * @return
     */
    JPanel getView();

    /**
     * Process this output
     *
     * @param output
     */
    void linkTo(IOutput output);
}
