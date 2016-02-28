package org.istic.synthlab.components.mixer;

import org.istic.synthlab.components.AbstractComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
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

        inputGainMixer1 = Factory.createModulator(
                "mixerModIn1", this,
                ModulatorType.GAIN,
                PotentiometerType.LINEAR);
        inputGainMixer2 = Factory.createModulator(
                "mixerModIn2", this,
                ModulatorType.GAIN,
                PotentiometerType.LINEAR);
        inputGainMixer3 = Factory.createModulator(
                "mixerModIn3", this,
                ModulatorType.GAIN,
                PotentiometerType.LINEAR);
        inputGainMixer4 = Factory.createModulator(
                "mixerModIn4", this,
                ModulatorType.GAIN,
                PotentiometerType.LINEAR);

        inputGainMixer1.getOutput().connect(mixer.getInput1());
        inputGainMixer2.getOutput().connect(mixer.getInput2());
        inputGainMixer3.getOutput().connect(mixer.getInput3());
        inputGainMixer4.getOutput().connect(mixer.getInput4());
        getOutput().connect(getSink());
    }

    public IInput getInput1(){ return inputGainMixer1.getInput(); }

    public IInput getInput2(){ return inputGainMixer2.getInput(); }

    public IInput getInput3(){ return inputGainMixer3.getInput(); }

    public IInput getInput4(){ return inputGainMixer4.getInput(); }

    public IInput getInputPortByIndex(int i){
        return mixer.getInputPortByIndex(i);
    }

    public double getGainValueInput1() {
        return inputGainMixer1.getValue();
    }

    public double getGainValueInput2() {
        return inputGainMixer2.getValue();
    }

    public double getGainValueInput3() {
        return inputGainMixer3.getValue();
    }

    public double getGainValueInput4() {
        return inputGainMixer4.getValue();
    }

    public void setGainValueInput1(double valueInput1) {
        inputGainMixer1.setValue(valueInput1);
    }

    public void setGainValueInput2(double valueInput2) {
        inputGainMixer2.setValue(valueInput2);
    }

    public void setGainValueInput3(double valueInput3) {
        inputGainMixer3.setValue(valueInput3);
    }

    public void setGainValueInput4(double valueInput4) {
        inputGainMixer4.setValue(valueInput4);
    }

    public double getMaxValue(){
       return inputGainMixer1.getMax();
    }

    public double getMinValue(){
        return inputGainMixer1.getMin();
    }

}
