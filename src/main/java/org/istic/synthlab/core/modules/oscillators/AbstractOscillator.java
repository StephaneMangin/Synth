package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.UnitOscillator;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * Manage the IComponent relation for all Oscillators
 *
 * 'Abstract oscillator' abstract representation
 * ---------------------------------------------
 *
 *        External View (public access to inputs and outputs)
 * INPUTS +---------------------------------------------------------------+   OUTPUTS
 * PORTS  |                                                               |   PORTS
 *        |     +-----------------+                                     +-+-+
 *        |     |Potentiometer    |                                   +->   | Output
 *        |     |Exponential      |                                   | +-+-+
 *        |     +-------+---------+  +-----------------------------+  |   |
 *      +-+-+           |            |Frequency              output+--+   |
 *   fm |   +-----------v------------>                             |      |
 *      +-+-+                        |                             |      |
 *        |                          |    UnitOscillator (JSyn)    |      |
 *        |                          |                             |      |
 *      +-+-+                        |                             |      |
 *   am |   +-----------^------------>                             |      |
 *      +-+-+           |            |Amplitude                    |      |
 *        |     +-------+---------+  +-----------------------------+      |
 *        |     |Potentiometer    |                                       |
 *        |     |Linear           |                                       |
 *        |     +-----------------+                                       |
 *        |                                                               |
 *        +---------------------------------------------------------------+
 *                                        Made with : http://asciiflow.com/
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public abstract class AbstractOscillator implements IOscillator {

    private IComponent component;
    private IInput am;
    private IInput fm;
    private IOutput output;
    private Potentiometer frequencyPotentiometer;
    private Potentiometer amplitudePotentiometer;
    private UnitOscillator unitOscillator;

    protected AbstractOscillator(IComponent component, UnitOscillator unitOscillator) {
        this.component = component;
        this.unitOscillator = unitOscillator;
        // Declare the generator to the register
        Register.declare(component, unitOscillator);
        // Link differents ports
        this.fm = Factory.createInput("Fm", component, getOscillator().frequency);
        this.am = Factory.createInput("Am", component, getOscillator().amplitude);
        this.output = Factory.createOutput("Out", component, getOscillator().output);
        // Link input to the frequency input of the oscillator to modulate it with the input signal
        this.frequencyPotentiometer = new Potentiometer("Frequency", getOscillator().frequency, PotentiometerType.EXPONENTIAL, 20000.0, 20.0, 1000.0);
        this.amplitudePotentiometer = new Potentiometer("Amplitude", getOscillator().amplitude, PotentiometerType.LINEAR, 10.0, 0.0, 1.0);
    }

    @Override
    public void activate() {
        getOscillator().setEnabled(true);
    }

    @Override
    public void deactivate() {
        getOscillator().setEnabled(false);
    }

    @Override
    public boolean isActivated() {
        return getOscillator().isEnabled();
    }

    public IComponent getComponent() {
        return component;
    }

    public UnitOscillator getOscillator() {
        return unitOscillator;
    }

    @Override
    public IInput getFm() {
        return this.fm;
    }

    @Override
    public IInput getAm() {
        return this.am;
    }

    @Override
    public IOutput getOutput() {
        return this.output;
    }

    @Override
    public void setFrequency(double value) {
        frequencyPotentiometer.setValue(value);
    }

    @Override
    public double getFrequency() {
        return frequencyPotentiometer.getValue();
    }

    public Potentiometer getFrequencyPotentiometer() {
        return frequencyPotentiometer;
    }

    public Potentiometer getAmplitudePotentiometer() {
        return amplitudePotentiometer;
    }

    @Override
    public void setAmplitude(double value) {
        amplitudePotentiometer.setValue(value);
    }

    @Override
    public double getAmplitude() {
        return amplitudePotentiometer.getValue();
    }

    @Override
    public double getAmplitudeMax() { return amplitudePotentiometer.getMax(); }

    @Override
    public double getAmplitudeMin() { return amplitudePotentiometer.getMin(); }
}
