package org.istic.synthlab.components.out;

import org.istic.synthlab.core.AComponent;
import org.istic.synthlab.core.services.ModulesFactory;
import org.istic.synthlab.core.services.IOMapping;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.lineOuts.ILineOut;
import org.istic.synthlab.core.modules.lineOuts.LineType;


public class Out extends AComponent {

    private ILineOut lineOut;
    private IInput input;

    public Out(String name) {
        super(name);
        lineOut = ModulesFactory.createLineOut(this, LineType.OUT);
        input = lineOut.getInput();
    }

    @Override
    public void activate() {
        lineOut.activate();
    }

    @Override
    public void desactivate() {
        lineOut.desactivate();
    }

    @Override
    public void init() {
    }

    @Override
    public void run() {
        //?
    }

    public IInput getIInput() {
        return input;
    }

    public ILineOut getLineOut() {
        return this.lineOut;
    }

}
