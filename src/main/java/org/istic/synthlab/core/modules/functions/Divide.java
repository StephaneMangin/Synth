package org.istic.synthlab.core.modules.functions;

import org.istic.synthlab.core.components.IComponent;

public class Divide extends AbstractFunction {
    public Divide(IComponent component) {
        super(component, new com.jsyn.unitgen.Divide());
    }
}
