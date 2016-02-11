package org.istic.synthlab.components.out;

import org.istic.synthlab.components.AbstractComponent;
import org.istic.synthlab.core.modules.functions.FunctionType;
import org.istic.synthlab.core.modules.functions.IFunction;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.lineOuts.ILineOut;
import org.istic.synthlab.core.modules.lineOuts.LineType;

/**
 * this class represents the OUT module
 *
 * It transfers the audio produced by a mounting signal to an audio output
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class Out extends AbstractComponent {

    private final ILineOut lineOut;

    public Out(String name) {
        super(name);
        this.lineOut = Factory.createLineOut(this, LineType.OUT);
        getSource().connect(getAm());
        lineOut.getInput().connect(getSourceAm());
    }

    @Override
    public void activate(){
        this.lineOut.activate();
    }

    @Override
    public void deactivate(){
        this.lineOut.deactivate();
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
        return this.lineOut.getInput();
    }

    public void start(){
        this.lineOut.start();
    }

    public ILineOut getLineOut() {
        return this.lineOut;
    }

}
