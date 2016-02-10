package org.istic.synthlab.components.generic;

import org.istic.synthlab.core.AbstractComponent;

import javax.swing.*;

/**
 * Created by seb on 04/02/16.
 */
public class generic extends AbstractComponent {

    /**
     * Instantiates a new component.
     *
     * @param name the name
     */
    public generic(String name) {
        super(name);
        // Connect the source to the visualizer but follow the source to the sink too
        getSource().connect(getSink());
    }
}
