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

    public void setAmplitude(double amplitude) {
        getAmModulator().setValue(amplitude);
    }

    public IInput getInput() {
        return lineOut.getInput();
    }

    public void start(){
        lineOut.start();
    }

}
