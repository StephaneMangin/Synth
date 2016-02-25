package org.istic.synthlab.components.seq;

import org.istic.synthlab.components.AbstractComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.sequencer.ISequencer;
import org.istic.synthlab.core.services.Factory;

/**
 * @author  Dechaud John Marc johnmarcdechaud[at]gmail[dot]com on 2/25/16.
 */
public class Sequencer extends AbstractComponent {

    private ISequencer iSequencer;

    /**
     * Instantiates a new component.
     *
     * @param name the name
     */
    public Sequencer(String name) {
        super(name);
        this.iSequencer = Factory.createSequencer(this);
        getSourceGate().connect(this.iSequencer.getInputGatePort());
        this.iSequencer.getOutputPort().connect(getSinkGate());
    }

    public IInput getInputgate(){
        return this.iSequencer.getInputGatePort();
    }

    public IOutput getOutputSeq(){
        return this.iSequencer.getOutputPort();
    }

    public void setStepValue(int step, double stepValue ){
        this.iSequencer.setStepValue(step, stepValue);
    }

    public double getStepValue(int step){
        return this.iSequencer.getStepValue(step);
    }

    public int getCurrentStep(){
        return this.iSequencer.getCurrentStep();
    }

    public void reset(){
        this.iSequencer.resetStep();
    }

    public double getMaxValue(){
        return this.iSequencer.getMaxValue();
    }

    public double getMinValue(){
        return this.iSequencer.getMinValue();
    }

    @Override
    public void activate() {
        this.iSequencer.activate();
    }

    @Override
    public void deactivate() {
        this.iSequencer.deactivate();
    }

    @Override
    public boolean isActivated() {
        return this.iSequencer.isActivated();
    }

}
