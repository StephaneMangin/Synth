package org.istic.synthlab.core.modules.keyboard;

import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.utils.jsyn.VoltageProducer;

/**
 * @author  Dechaud John Marc johnmarcdechaud[at]gmail[dot]com on 3/3/16.
 */
public class Keyboard implements IKeyboard {

    private IOutput outputCv;
    private IOutput outputGate;
    private VoltageProducer voltageGate;
    private VoltageProducer voltageCv;

    public Keyboard(IComponent component) {
        this.voltageGate = new VoltageProducer();
        this.voltageCv = new VoltageProducer();

        this.outputCv = Factory.createOutput("outputCv", component, this.voltageCv.output);
        this.outputGate = Factory.createOutput("outputGate", component, this.voltageGate.output);
    }

    /**
     * Get the CV output port
     *
     * @return IOutput
     */
    @Override
    public IOutput getOutputCV() {
        return this.outputCv;
    }

    /**
     * Get the gate output port
     *
     * @return IOutput
     */
    @Override
    public IOutput getOutputGate() {
        return this.outputGate;
    }

    /**
     * Play the note
     *
     * @param note
     */
    @Override
    public void note(NOTE note) {

    }

    @Override
    public void activate() {
        this.voltageCv.setEnabled(true);
        this.voltageGate.setEnabled(true);
    }

    @Override
    public void deactivate() {
        this.voltageCv.setEnabled(false);
        this.voltageGate.setEnabled(false);
    }

    @Override
    public boolean isActivated() {
        return ( this.voltageCv.isEnabled() || this.voltageGate.isEnabled() );
    }
}
