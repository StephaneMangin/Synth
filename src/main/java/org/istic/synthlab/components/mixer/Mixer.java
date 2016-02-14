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
        this.mixer.getOutput().connect(this.getSink());
    }

    public IOutput getOutput(){ return this.mixer.getOutput(); }

    public IInput getInput1(){ return this.mixer.getInput1(); }

    public IInput getInput2(){ return this.mixer.getInput2(); }

    public IInput getInput3(){ return this.mixer.getInput3(); }

    public IInput getInput4(){ return this.mixer.getInput4(); }

    public IInput getInputPortByIndex(int i){
        return this.mixer.getInputPortByIndex(i);
    }

    public double getGainValueInput1() {
        return this.mixer.getAmplitudePotentiometerInput1().getValue();
    }
    public void setGainValueInput1(double valueInput1) {
        this.mixer.getAmplitudePotentiometerInput1().setValue(valueInput1);
    }

    public double getGainValueInput2() {
        return this.mixer.getAmplitudePotentiometerInput2().getValue();
    }
    public void setGainValueInput2(double valueInput2) {
        this.mixer.getAmplitudePotentiometerInput2().setValue(valueInput2);
    }

    public double getGainValueInput3() {
        return this.mixer.getAmplitudePotentiometerInput2().getValue();
    }
    public void setGainValueInput3(double valueInput3) {
        this.mixer.getAmplitudePotentiometerInput3().setValue(valueInput3);
    }

    public double getGainValueInput4() {
        return this.mixer.getAmplitudePotentiometerInput4().getValue();
    }
    public void setGainValueInput4(double valueInput4) {
        this.mixer.getAmplitudePotentiometerInput4().setValue(valueInput4);
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
