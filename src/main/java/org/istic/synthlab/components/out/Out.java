package org.istic.synthlab.components.out;

import org.istic.synthlab.core.AbstractComponent;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.lineOuts.ILineOut;
import org.istic.synthlab.core.modules.lineOuts.LineType;


public class Out extends AbstractComponent {

    private ILineOut lineOut;
    private IInput input;

    public Out(String name) {
        super(name);
        lineOut = Factory.createLineOut(this, LineType.OUT);
        input = lineOut.getInput();
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
    public void init() {
    }

    @Override
    public void run() {
        //?
    }

    public IInput getInput() {
        return input;
    }

    public ILineOut getLineOut() {
        return this.lineOut;
    }

}
