package org.istic.synthlab.components.out;

import org.istic.synthlab.components.AbstractComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.lineOuts.ILineOut;
import org.istic.synthlab.core.modules.lineOuts.LineType;
import org.istic.synthlab.core.services.Factory;

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
        getSourceAm().connect(lineOut.getInput());
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

    // The criminal was here
    // Overriding the getInput() function of the component made a bug in the wiring
    // Calling out.getInput() became the same as out.getLineOut() which has absolutely NOT WHAT WE WANT

    public void start(){
        lineOut.start();
    }

    public ILineOut getLineOut() {
        return this.lineOut;
    }

}
