package org.istic.synthlab.components.vcfa;

import org.istic.synthlab.components.AbstractComponent;
import org.istic.synthlab.core.modules.functions.IFunction;
import org.istic.synthlab.core.modules.functions.Multiply;
import org.istic.synthlab.core.modules.modulators.IModulator;
import org.istic.synthlab.core.modules.modulators.ModulatorType;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * this class represents the VCFA (Voltage Controlled Frequency Amplifier)  module
 */
public class Vcfa extends AbstractComponent {

    private final IModulator vcaModulator;

    /**
     * Instantiates a new VCFA.
     *
     * @param name: String
     */
    public Vcfa(String name) {
        super(name);
        this.vcaModulator = Factory.createModulator("VCA", this, ModulatorType.VCA, PotentiometerType.EXPONENTIAL);
    }

}
