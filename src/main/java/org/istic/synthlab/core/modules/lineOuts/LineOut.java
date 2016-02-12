package org.istic.synthlab.core.modules.lineOuts;

import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;

/**
 * The class Line adapter.
 *
 * The class Line adapter that implements the interface ILineOut
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class LineOut implements ILineOut {

    private com.jsyn.unitgen.LineOut lineOut;
    private com.jsyn.unitgen.PassThrough passThrough;
    private IInput input;

    /**
     * Instantiates a new Line adapter.
     * @param component
     */
    public LineOut(IComponent component) {
        lineOut = new com.jsyn.unitgen.LineOut();
        passThrough = new com.jsyn.unitgen.PassThrough();
        input = Factory.createInput("In", component, passThrough.input);
        // First declare the mappings
        Register.declare(component, lineOut);
        Register.declare(component, passThrough);
        Register.declare(component, input, passThrough.getInput());

        passThrough.output.connect(0, lineOut.input, 0);
        passThrough.output.connect(0, lineOut.input, 1);
    }

    /**
     * Start the filter and the lineOut
     */
    public void start() {
        lineOut.start();
        Factory.createSynthesizer().start();
    }

    /**
     * Stop the filter and the lineOut
     */
    public void stop() {
        lineOut.stop();
        Factory.createSynthesizer().stop();
    }

    @Override
    public IInput getInput() {
        return input;
    }

    @Override
    public void activate() {
        passThrough.setEnabled(true);
        lineOut.setEnabled(true);
    }

    @Override
    public void deactivate() {
        passThrough.setEnabled(false);
        lineOut.setEnabled(false);
    }

    @Override
    public boolean isActivated() {
        return this.lineOut.isEnabled() && this.passThrough.isEnabled();
    }
}
