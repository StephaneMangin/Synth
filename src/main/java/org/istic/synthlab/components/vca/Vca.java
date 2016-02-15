package org.istic.synthlab.components.vca;

import org.istic.synthlab.components.AbstractComponent;
import org.istic.synthlab.core.modules.functions.IFunction;
import org.istic.synthlab.core.modules.functions.Multiply;
import org.istic.synthlab.core.modules.modulators.IModulator;
import org.istic.synthlab.core.modules.modulators.ModulatorType;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;
import org.istic.synthlab.ui.controls.Potentiometer;

/**
 * @author Dechaud John Marc on 2/8/16.
 * this class represents the VCA (Voltage Controlled Amplifier)  module
 *
 * It controls the amplitude of an incoming signal according to another modulating signal.
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class Vca extends AbstractComponent {

    private IModulator vcaModulator;
    private IFunction multiplyInAm;

    /**
     * Instantiates a new VCA.
     *
     * @param name: String
     */
    public Vca(String name) {
        super(name);

        this.multiplyInAm = new Multiply(this);
        this.vcaModulator = Factory.createModulator("VCA", this, ModulatorType.VCA, PotentiometerType.LINEAR);

        // Connect the source port with the input port modulator
        this.getSource().connect(this.multiplyInAm.getInput());

        // Connect the sourceAm port with the input port modulator
        this.getSourceAm().connect(this.vcaModulator.getInput());
        this.multiplyInAm.setVariableInput(0.0);

        // Connect the sum of modulation to input signal
        this.vcaModulator.getOutput().connect(this.multiplyInAm.getVariableInput());

        // Connect the gain output port with the external output
        this.multiplyInAm.getOutput().connect(this.getSink());

    }

    @Override
    public void activate() {
        vcaModulator.activate();
    }

    @Override
    public void deactivate() {
        vcaModulator.deactivate();
    }

    @Override
    public boolean isActivated() {
        return vcaModulator.isActivated();
    }

    public IModulator getGainModulator() {
        return vcaModulator;
    }

}
