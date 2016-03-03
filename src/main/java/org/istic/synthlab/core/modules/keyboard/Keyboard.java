package org.istic.synthlab.core.modules.keyboard;

import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.utils.functions.VoltageProducer;

/**
 * @author  Dechaud John Marc johnmarcdechaud[at]gmail[dot]com on 3/3/16.
 */
public class Keyboard implements IKeyboard {

    private IOutput outputCv;
    private IOutput outputGate;
    private VoltageProducer voltageGate;
    private VoltageProducer voltageCv;
    private double octave = 0;
    private double noteVoltage = 0;

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
    public void noteOn(Note note) {
        double steps = 0;
        double noteStep = Math.pow(2, 1/12)/12;
        switch (note) {
            case X:
                octave++;
                if (octave == 5) {
                    octave = 4;
                }
                break;
            case W:
                octave--;
                if (octave == -1) {
                    octave = 0;
                }
                break;
            case DOs:
                steps += noteStep;
            case SI:
                steps += noteStep;
            case LAd:
                steps += noteStep;
            case LA:
                steps += noteStep;
            case SOLd:
                steps += noteStep;
            case SOL:
                steps += noteStep;
            case FAd:
                steps += noteStep;
            case FA:
                steps += noteStep;
            case MI:
                steps += noteStep;
            case REd:
                steps += noteStep;
            case RE:
                steps += noteStep;
            case DOd:
                steps += noteStep;
            case DO:
            default:
                noteVoltage = steps;
                break;
        }
        voltageCv.getInput().set((octave+noteVoltage)/5);
        voltageGate.getInput().set(5.0);
    }

    @Override
    public void noteOff() {
        voltageGate.getInput().set(0.0);
        voltageCv.getInput().set(0.0);
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
