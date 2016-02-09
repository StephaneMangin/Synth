package org.istic.synthlab.components.replicator;

import org.istic.synthlab.core.AbstractComponent;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.modulators.IModulator;
import org.istic.synthlab.core.modules.modulators.ModulatorType;
import org.istic.synthlab.core.modules.passThrough.IPassThrough;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

/**
 * @author gottstein[dot]cyprien[at]gmail[dot]com on 09/02/16.
 */
public class Replicator extends AbstractComponent {

    private IPassThrough passThrough;

    private IModulator outputModulatorReplicated1;
    private IModulator outputModulatorReplicated2;


    /**
     * Instantiates a new component.
     *
     * @param name the name
     */
    public Replicator(String name) {
        super(name);
        passThrough = Factory.createPassThrough(this);

        outputModulatorReplicated1 = Factory.createModulator(
                "modOut1", this,
                ModulatorType.AMPLITUDE,
                PotentiometerType.EXPONENTIAL);

        outputModulatorReplicated2 = Factory.createModulator(
                "modOut2", this,
                ModulatorType.AMPLITUDE,
                PotentiometerType.EXPONENTIAL);

        getSourceFm().connect(passThrough.getInput());
        passThrough.getOutput1().connect(getSink());
        passThrough.getOutput2().connect(outputModulatorReplicated1.getInput());
        passThrough.getOutput3().connect(outputModulatorReplicated2.getInput());


    }

    @Override
    public void activate() {
        passThrough.activate();
    }

    @Override
    public void desactivate() {
        passThrough.desactivate();
    }

    @Override
    public boolean isActivated() { return passThrough.isEnable(); }

    public IOutput getOutputReplicated1(){ return outputModulatorReplicated1.getOutput(); }

    public IOutput getOutputReplicated2(){ return outputModulatorReplicated2.getOutput(); }

    public IPassThrough getPassThrough(){ return passThrough; }
}
