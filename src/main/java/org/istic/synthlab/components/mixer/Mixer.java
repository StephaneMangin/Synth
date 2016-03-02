package org.istic.synthlab.components.mixer;

import org.istic.synthlab.components.AbstractComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.mix.IMix;
import org.istic.synthlab.core.modules.modulators.IModulator;
import org.istic.synthlab.core.modules.modulators.ModulatorType;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * The model of Mixer component
 * It creates an Mixer that produce one signal coming from different inputs ports
 *
 * A Mixer is composed of the following input and output :
 * - 4 frequency signal input
 * - 1 frequency output
 *
 * A Mixer is composed of the following potentiometer :
 * - A gain potentiometer for each of the input to set the weight of each signal on the final output.
 *
 * @author  Dechaud John Marc johnmarcdechaud[at]gmail[dot]com on 2/13/16.
 *
 **/
public class Mixer extends AbstractComponent {

    private IMix mixer;
    private IModulator inputGainMixer1;
    private IModulator inputGainMixer2;
    private IModulator inputGainMixer3;
    private IModulator inputGainMixer4;

    /**
     * Constructor of the Mixer component.
     *
     * @param name :String
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

    /**
     * Returns the input access used for the first input of the Mixer component
     *
     * @return IInput
     */
    public IInput getInput1(){ return this.inputGainMixer1.getInput(); }

    /**
     * Returns the input access used for the second input of the Mixer component
     *
     * @return IInput
     */
    public IInput getInput2(){ return this.inputGainMixer2.getInput(); }

    /**
     * Returns the input access used for the third input of the Mixer component
     *
     * @return IInput
     */
    public IInput getInput3(){ return this.inputGainMixer3.getInput(); }

    /**
     * Returns the input access used for the fourth input of the Mixer component
     *
     * @return IInput
     */
    public IInput getInput4(){ return this.inputGainMixer4.getInput(); }

    /**
     * Return the value of the first input
     *
     * @return double
     */
    public double getGainValueInput1() {
        return this.inputGainMixer1.getValue();
    }

    /**
     * Return the value of the second input
     *
     * @return double
     */
    public double getGainValueInput2() {
        return this.inputGainMixer2.getValue();
    }

    /**
     * Return the value of the third input
     *
     * @return double
     */
    public double getGainValueInput3() {
        return this.inputGainMixer3.getValue();
    }

    /**
     * Return the value of the fourth input
     *
     * @return double
     */
    public double getGainValueInput4() {
        return this.inputGainMixer4.getValue();
    }

    /**
     * Set the value of the first input
     *
     * @param valueInput1 double
     */
    public void setGainValueInput1(double valueInput1) {
        this.inputGainMixer1.setValue(valueInput1);
    }

    /**
     * Set the value of the second input
     *
     * @param valueInput2 double
     */
    public void setGainValueInput2(double valueInput2) {
        this.inputGainMixer2.setValue(valueInput2);
    }

    /**
     * Set the value of the third input
     *
     * @param valueInput3 double
     */
    public void setGainValueInput3(double valueInput3) {
        this.inputGainMixer3.setValue(valueInput3);
    }

    /**
     * Set the value of the fourth input
     *
     * @param valueInput4 double
     */
    public void setGainValueInput4(double valueInput4) {
        this.inputGainMixer4.setValue(valueInput4);
    }

    /**
     * Returns the maximum value for the Gain Mixer
     *
     * @return double
     */
    public double getMaxValue(){
       return this.inputGainMixer1.getMax();
    }

    /**
     * Returns the minimum value for the Gain Mixer
     *
     * @return double
     */
    public double getMinValue(){
        return this.inputGainMixer1.getMin();
    }

    @Override
    public void activate() {
        mixer.activate();
    }

    @Override
    public void deactivate() {
        mixer.deactivate();
    }

    @Override
    public boolean isActivated(){
        return mixer.isActivated();
    }

}
