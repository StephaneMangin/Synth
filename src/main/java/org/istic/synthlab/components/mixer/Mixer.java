package org.istic.synthlab.components.mixer;

import org.istic.synthlab.components.AbstractComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.mix.IMix;
import org.istic.synthlab.core.modules.modulators.IModulator;
import org.istic.synthlab.core.modules.modulators.ModulatorType;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * @author  Dechaud John Marc johnmarcdechaud[at]gmail[dot]com on 2/13/16.
 */

/**
 * The model of Mixer component
 * It creates an Mixer that produce one signal coming from different inputs ports
 * */
public class Mixer extends AbstractComponent {

    private IMix mixer;
    private IModulator inputGainMixer1;
    private IModulator inputGainMixer2;
    private IModulator inputGainMixer3;
    private IModulator inputGainMixer4;

    /**
     * Instantiates a new component.
     *
     * @param name the name
     */
    public Mixer(String name) {
        super(name);
        this.mixer = Factory.createMixer(this);

        this.inputGainMixer1 = Factory.createModulator(
                "mixerModIn1", this,
                ModulatorType.GAIN,
                PotentiometerType.LINEAR);
        this.inputGainMixer2 = Factory.createModulator(
                "mixerModIn2", this,
                ModulatorType.GAIN,
                PotentiometerType.LINEAR);
        this.inputGainMixer3 = Factory.createModulator(
                "mixerModIn3", this,
                ModulatorType.GAIN,
                PotentiometerType.LINEAR);
        this.inputGainMixer4 = Factory.createModulator(
                "mixerModIn4", this,
                ModulatorType.GAIN,
                PotentiometerType.LINEAR);

        this.inputGainMixer1.getOutput().connect(this.mixer.getInput1());
        this.inputGainMixer2.getOutput().connect(this.mixer.getInput2());
        this.inputGainMixer3.getOutput().connect(this.mixer.getInput3());
        this.inputGainMixer4.getOutput().connect(this.mixer.getInput4());

        this.mixer.getOutput().connect(getSink());
    }

    public IInput getInput1(){ return this.inputGainMixer1.getInput(); }

    public IInput getInput2(){ return this.inputGainMixer2.getInput(); }

    public IInput getInput3(){ return this.inputGainMixer3.getInput(); }

    public IInput getInput4(){ return this.inputGainMixer4.getInput(); }

    public double getGainValueInput1() {
        return this.inputGainMixer1.getValue();
    }

    public double getGainValueInput2() {
        return this.inputGainMixer2.getValue();
    }

    public double getGainValueInput3() {
        return this.inputGainMixer3.getValue();
    }

    public double getGainValueInput4() {
        return this.inputGainMixer4.getValue();
    }

    public void setGainValueInput1(double valueInput1) {
        this.inputGainMixer1.setValue(valueInput1);
    }

    public void setGainValueInput2(double valueInput2) {
        this.inputGainMixer2.setValue(valueInput2);
    }

    public void setGainValueInput3(double valueInput3) {
        this.inputGainMixer3.setValue(valueInput3);
    }

    public void setGainValueInput4(double valueInput4) {
        this.inputGainMixer4.setValue(valueInput4);
    }

    public double getMaxValue(){
       return this.inputGainMixer1.getMax();
    }

    public double getMinValue(){
        return this.inputGainMixer1.getMin();
    }

}
