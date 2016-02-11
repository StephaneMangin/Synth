package org.istic.synthlab.components.out;

import org.istic.synthlab.core.AbstractComponent;
import org.istic.synthlab.core.modules.functions.FunctionType;
import org.istic.synthlab.core.modules.functions.IFunction;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.lineOuts.ILineOut;
import org.istic.synthlab.core.modules.lineOuts.LineType;

/**
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class Out extends AbstractComponent {

    private final IFunction multiply;
    private final ILineOut lineOut;

    public Out(String name) {
        super(name);
        this.multiply = Factory.createFunction(this, FunctionType.MULTIPLY);
        this.lineOut = Factory.createLineOut(this, LineType.OUT);
        getSource().connect(multiply.getInput());
        getSourceAm().connect(multiply.getVariableInput());
        lineOut.getInput().connect(multiply.getOutput());
    }

    @Override
    public void activate() {
        lineOut.activate();
    }

    @Override
    public void deactivate() {
        lineOut.deactivate();
    }

    @Override
    public boolean isActivated() { return this.lineOut.isActivated(); }

    @Override
    public void init() {

    }

    @Override
    public void run() {

    }

    public IInput getInput() {
        return lineOut.getInput();
    }

    public void start(){
        lineOut.start();
    }

}
