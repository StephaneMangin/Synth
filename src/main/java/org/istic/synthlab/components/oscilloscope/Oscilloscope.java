package org.istic.synthlab.components.oscilloscope;

import org.istic.synthlab.components.AbstractComponent;
import org.istic.synthlab.core.modules.visualizer.Visualizer;

import javax.swing.*;

/**
<<<<<<< HEAD
 * @author Sébastien François
=======
 * Created by seb on 04/02/16.
 *
 * This class represents the oscilloscope module
 * It displays a signal for analysis
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
>>>>>>> 8497725a496336bc7041b5139af56285bcd34f56
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

    public Number getValue() {
        return this.visualizer.getValue();
    }
}
