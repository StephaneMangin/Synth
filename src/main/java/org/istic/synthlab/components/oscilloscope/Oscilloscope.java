package org.istic.synthlab.components.oscilloscope;

import org.istic.synthlab.core.AbstractComponent;
import org.istic.synthlab.core.modules.visualizer.Visualizer;

import javax.swing.*;

/**
 * @author Sébastien François
 */
public class Oscilloscope extends AbstractComponent {
    private final Visualizer visualizer;

    /**
     * Instantiates a new component.
     *
     * @param name the name
     */
    public Oscilloscope(String name) {
        super(name);
        // Connect the source to the visualizer
        visualizer = new Visualizer(this);
        visualizer.linkTo(getSource());
        // And follow the source to the sink too
        getSource().connect(getSink());
    }

    public JPanel getView() {
        this.activate();
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

    public Number getValue() {
        return this.visualizer.getValue();
    }
}
