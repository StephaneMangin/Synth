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
    private IInput input;

    /**
     * Instantiates a new Line adapter.
     * @param component
     */
    public LineOut(IComponent component) {
        lineOut = new com.jsyn.unitgen.LineOut();
        input = Factory.createInput("In", component, lineOut.input);
        // First declare the mappings
        Register.declare(component, lineOut);
        Register.declare(component, input, lineOut.getInput());
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
        lineOut.setEnabled(true);
    }

    @Override
    public void deactivate() {
        lineOut.setEnabled(false);
    }

    @Override
    public boolean isActivated() {
        return this.lineOut.isEnabled();
    }
}
