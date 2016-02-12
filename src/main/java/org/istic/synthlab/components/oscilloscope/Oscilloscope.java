package org.istic.synthlab.components.oscilloscope;

import org.istic.synthlab.components.AbstractComponent;
import org.istic.synthlab.core.modules.visualizer.Visualizer;

import javax.swing.*;

/**
 * Created by seb on 04/02/16.
 *
 * This class represents the oscilloscope module
 * It displays a signal for analysis
 *
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

<<<<<<< HEAD
    /*
    * This methods shows the oscilloscope view
    */
    public JPanel getVIew() {
=======
    public JPanel getView() {
        this.activate();
>>>>>>> master
        return this.visualizer.getView();
    }

    @Override
    public void activate() {
        this.visualizer.start();
    }

    @Override
    public void deactivate() {
        this.visualizer.stop();
    }
}
