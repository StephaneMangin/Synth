package org.istic.synthlab.components.mixer;

import org.istic.synthlab.components.AbstractComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.mix.IMix;
import org.istic.synthlab.core.services.Factory;

/**
 * @author  Dechaud John Marc johnmarcdechaud[at]gmail[dot]com on 2/13/16.
 */

/**
 * The model of Mixer component
 * It creates an Mixer that produce one signal coming from different inputs ports
 * */
public class Mixer extends AbstractComponent{

    private IMix mixer;

    /**
     * Instantiates a new component.
     *
     * @param name the name
     */
    public Mixer(String name) {
        super(name);
        this.mixer = Factory.createMixer(this);
        getSourceInputMix1().connect(getInput1());
        getSourceInputMix2().connect(getInput2());
        getSourceInputMix3().connect(getInput3());
        getSourceInputMix4().connect(getInput4());
        getOutputMixer().connect(this.getSink());
    }

    public IOutput getOutputMixer(){ return this.mixer.getOutput(); }

    public IInput getInput1(){ return this.mixer.getInput1(); }

    public IInput getInput2(){ return this.mixer.getInput2(); }

    public IInput getInput3(){ return this.mixer.getInput3(); }

    public IInput getInput4(){ return this.mixer.getInput4(); }

    public IInput getInputPortByIndex(int i){
        return this.mixer.getInputPortByIndex(i);
    }

    public double getGainValueInput1() {
        return this.getInputModulatorMixer1().getValue();
    }

    public void setGainValueInput1(double valueInput1) {
        this.getInputModulatorMixer1().setValue(valueInput1);
    }

    public double getGainValueInput2() {
        return this.getInputModulatorMixer2().getValue();
    }

    public void setGainValueInput2(double valueInput2) {
        this.getInputModulatorMixer2().setValue(valueInput2);
    }

    public double getGainValueInput3() {
        return this.getInputModulatorMixer3().getValue();
    }

    public void setGainValueInput3(double valueInput3) {
        this.getInputModulatorMixer3().setValue(valueInput3);
    }

    public double getGainValueInput4() {
        return this.getInputModulatorMixer4().getValue();
    }

    public void setGainValueInput4(double valueInput4) {
        this.getInputModulatorMixer4().setValue(valueInput4);
    }

    public double getMaxValue(){
       return this.getInputModulatorMixer1().getMax();
    }

    public double getMinValue(){
        return this.getInputModulatorMixer1().getMin();
    }

    @Override
    public void activate() {
        this.mixer.activate();
    }

    @Override
    public void deactivate() {
        this.mixer.deactivate();
    }

    @Override
    public boolean isActivated() {
        return this.mixer.isActivated();
    }

    @Override
    public void init() {
    }

    @Override
    public void run() {
    }

}
