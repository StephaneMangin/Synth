package org.istic.synthlab.core.modules.functions;

import org.istic.synthlab.core.IComponent;

public class Substract extends AbstractFunction {
    public Substract(IComponent component) {
        super(component, new com.jsyn.unitgen.Subtract());
    }
}
