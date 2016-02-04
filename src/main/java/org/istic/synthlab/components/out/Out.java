package org.istic.synthlab.components.out;

import org.istic.synthlab.core.AComponent;
import org.istic.synthlab.core.AdapterFactory;
import org.istic.synthlab.core.IOMappingService;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.lineOuts.ILineOut;
import org.istic.synthlab.core.modules.lineOuts.LineType;


public class Out extends AComponent {

    private ILineOut lineOut;
    private IInput input;

    public Out(String name) {
        super(name);
        lineOut = AdapterFactory.createLineOut(this, LineType.OUT);
        IOMappingService.declare(this, this.lineOut.getUnitGeneratorFilter());
        IOMappingService.declare(this, this.lineOut.getUnitGeneratorLineOut());

        input = lineOut.getInput();
        IOMappingService.declare(this, this.input, this.input.getUnitInputPort());
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
