package org.istic.synthlab.core.modules.oscillators;

import com.jsyn.unitgen.UnitOscillator;
import org.istic.synthlab.components.IComponent;
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
        fm = Factory.createInput("Fm", component, unitOscillator.frequency);
        am = Factory.createInput("Am", component, unitOscillator.amplitude);
        output = Factory.createOutput("Out", component, unitOscillator.output);
        // Link input to the frequency input of the oscillator to modulate it with the input signal

        frequencyPotentiometer = new Potentiometer("Frequency", unitOscillator.frequency, PotentiometerType.EXPONENTIAL, 30000.0, 0.0, 0.0);
        amplitudePotentiometer = new Potentiometer("Amplitude", unitOscillator.amplitude, PotentiometerType.LINEAR, 1.0, 0.0, 1.0);
    }

    @Override
    public void activate() {
        unitOscillator.setEnabled(true);
    }

    @Override
    public void deactivate() {
        unitOscillator.setEnabled(false);
    }

    @Override
    public boolean isActivated() {
        return unitOscillator.isEnabled();
    }

    public IComponent getComponent() {
        return component;
    }

    @Override
    public IInput getFm() {
        return fm;
    }

    @Override
    public IInput getAm() {
        return am;
    }

    @Override
    public IOutput getOutput() {
        return output;
    }

    @Override
    public Potentiometer getFrequencyPotentiometer() {
        return frequencyPotentiometer;
    }

    @Override
    public Potentiometer getAmplitudePotentiometer() {
        return amplitudePotentiometer;
    }

}
