package org.istic.synthlab.components.out;

import org.istic.synthlab.components.AbstractComponent;
import org.istic.synthlab.core.modules.lineOuts.ILineOut;
import org.istic.synthlab.core.modules.lineOuts.LineType;
import org.istic.synthlab.core.services.Factory;

/**
 * This class represents the OUT module
 * It transfers the audio produced by a mounting signal to an audio output.
 *
 * An Oscilloscope is composed of the following input and output :
 * - a frequency signal input
 *
 * An Oscilloscope is composed of the following potentiometer :
 * - An Amplitude potentiometer to set the audio volume of the output
 *
 * An Oscilloscope is composed of the following buttons :
 * - A button to either mute or unmute the audio output
 * - A button to set a file in which the signal produced by the circuit will be written
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class Out extends AbstractComponent {

    private final ILineOut lineOut;

    /**
     * Constructor of the Out component.
     *
     * @param name :String
     */
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

    /**
     * Method to start the component internal JSyn module, used to start the whole circuit.
     */
    public void start(){
        lineOut.start();
    }

    /**
     * Returns the internal ILineOut module of the component.
     *
     * @return ILineOut
     */
    public ILineOut getLineOut() {
        return this.lineOut;
    }

}
