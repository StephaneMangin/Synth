package org.istic.synthlab.core.modules.functions;

import org.istic.synthlab.components.IComponent;

public class Add extends AbstractFunction {
    public Add(IComponent component) {
        super(component, new com.jsyn.unitgen.Add());
    }
}
