package org.istic.synthlab.components.out;

import org.istic.synthlab.core.AbstractComponent;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.lineOuts.ILineOut;
import org.istic.synthlab.core.modules.lineOuts.LineType;


public class Out extends AbstractComponent {

    private ILineOut lineOut;

    public Out(String name) {
        super(name);

        this.lineOut = Factory.createLineOut(this, LineType.OUT);
        getSourceFm().connect(getAmModulator().getInput());
        getAmModulator().getOutput().connect(this.lineOut.getInput());

    }

    @Override
    public void activate(){
        this.lineOut.activate();
    }

    @Override
    public void desactivate(){
        this.lineOut.desactivate();
    }

    @Override
    public void init() {

    }

    @Override
    public void run() {

    }

    public IInput getInput() {
        return this.lineOut.getInput();
    }

    public void start(){
        this.lineOut.start();
    }

    public boolean isEnable(){
       return this.lineOut.isEnable();
    }

}
