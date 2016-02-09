package org.istic.synthlab.components.vca;

import org.istic.synthlab.core.AbstractComponent;
import org.istic.synthlab.core.modules.modulators.GainModulator;
import org.istic.synthlab.core.modules.modulators.IModulator;
import org.istic.synthlab.core.modules.modulators.ModulatorType;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Dechaud John Marc on 2/8/16.
 */
public class Vca extends AbstractComponent{

    private IModulator amplitudeModulator;
    private GainModulator gainModulator;
    private double oldValueAm;

    /**
     * Instantiates a new component.
     *
     * @param name the name
     */
    public Vca(String name) {
        super(name);
        // Instanciate GainModulator
        this.gainModulator = new GainModulator("Gain", this, PotentiometerType.LINEAR);
        // Connect the source port with the input port modulator
        this.getSource().connect(this.gainModulator.getInput());
        // Connect the gain output port with the
        this.gainModulator.getOutput().connect(this.getSink());

        // Create the amplitude modulator
        this.amplitudeModulator = Factory.createModulator("AM", this, ModulatorType.AMPLITUDE, PotentiometerType.LINEAR);
        // Connect the sourceAm port with the input port modulator
        this.getSourceAm().connect(this.amplitudeModulator.getInput());
        // Save the old value of amplitude modulator
        this.oldValueAm = this.getAmplitudeModulatorValue();
        // Call periodic
        this.activePeriod();
    }

    @Override
    public void activate() {
        super.activate();
    }

    @Override
    public void desactivate() {
        super.desactivate();
    }

    public double getAmplitudeModulatorValue() {
        return this.amplitudeModulator.getValue();
    }

    public void setAmplitudeModulatorValue(double value) {
        this.amplitudeModulator.setValue(value);
    }

    public double getGainModulatorValue() {
        return this.gainModulator.getValue();
    }

    public void setGainModulatorValue(double value) {
        this.gainModulator.setValue(value);
    }

    private void calculateGainValue(){
        if (!this.getSourceAm().getUnitOutputPort().isConnected() || this.getSourceAm() == null){
            this.desactivate();
        }

        if (this.getAmplitudeModulatorValue() == 1.0){
        }

        if (this.getAmplitudeModulatorValue() == (this.oldValueAm + 1)){
            double newVal = this.getGainModulatorValue() + 12;
            this.setGainModulatorValue(newVal);
        }

        if (this.amplitudeModulator.getValue() == (this.oldValueAm - 1)){
            double newVal = this.getGainModulatorValue() - 12;
            this.setGainModulatorValue(newVal);
        }
    }

    private void activePeriod(){
        Timer time = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
               calculateGainValue();
            }
        };
        time.schedule(task, 100);
    }

}
