package org.istic.synthlab.components.oscilloscope;

import org.istic.synthlab.core.components.AbstractComponent;
import org.istic.synthlab.core.modules.visualizer.Visualizer;

import javax.swing.*;

/**
 *
 * This class represents the oscilloscope module
 * It displays a signal to help for visual analysis.
 *
 * An Oscilloscope is composed of the following input and output :
 * - a frequency signal input
 * - a frequency signal output
 *
 * An Oscilloscope is composed of the following potentiometer :
 * - A scale potentiometer to set the size of the signal screen
 *
 * @author Sébastien François
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */

public class Oscilloscope extends AbstractComponent {
    private final Visualizer visualizer;

    /**
     * Instantiates a new oscilloscope.
     *
     * @param name :String
     */
    public Oscilloscope(String name) {
        super(name);
        // Connect the source to the visualizer
        visualizer = new Visualizer(this);
        visualizer.linkTo(getSource());
        // And follow the source to the sink too
        getSource().connect(getSink());
    }

    /*
    * This methods shows the oscilloscope view
    */
    public JPanel getView() {
        this.activate();
        return this.visualizer.getView();
    }

    @Override
    public void activate() {
        this.visualizer.activate();
    }

    @Override
    public void deactivate() {
        this.visualizer.deactivate();
    }

    /**
     * Returns the current value hold by the internal visualizer
     *
     * @return Number
     */
    public Number getValue() {
        return this.visualizer.getValue();
    }

    /**
     * Returns the current width used by the internal visualizer to display the signal
     *
     * @return double
     */
    public double getWidth() {
        return this.visualizer.getWidth();
    }
}
