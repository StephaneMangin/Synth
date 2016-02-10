package org.istic.synthlab.components.oscilloscope;

import org.istic.synthlab.core.AbstractComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.services.Factory;

import javax.swing.*;

/**
 * Created by seb on 04/02/16.
 */
public class Oscilloscope extends AbstractComponent {
    private final org.istic.synthlab.core.modules.oscilloscope.Oscilloscope oscilloscope;

    /**
     * Instantiates a new component.
     *
     * @param name the name
     */
    public Oscilloscope(String name) {
        super(name);
        // Connect the source to the oscilloscope but follow the source to the sink too
        this.oscilloscope = new org.istic.synthlab.core.modules.oscilloscope.Oscilloscope(this, getSource());
        getSource().connect(getSink());
    }

    public JPanel getView() {
        return this.oscilloscope.getView();
    }
}
