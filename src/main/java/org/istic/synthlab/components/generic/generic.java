package org.istic.synthlab.components.generic;

import org.istic.synthlab.core.AbstractComponent;

/**
 * Created by seb on 04/02/16.
 * This class represents the generic class of component
 */
public class generic extends AbstractComponent {

    /**
     * Instantiates a new Generic.
     *
     * @param name : String
     */
    public generic(String name) {
        super(name);
        // Connect the source to the visualizer but follow the source to the sink too
        getSource().connect(getSink());
    }
}
