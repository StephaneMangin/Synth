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
 * this class represents the Replicator module
 *
 * It links an output port to 3 input ports (such as a power strip)
 */
public class Replicator extends AbstractComponent {

    private IPassThrough passThrough;

    /*
    * the first replicated output
    */
    private IModulator outputModulatorReplicated1;
    /*
    * the second replicated output
    */
    private IModulator outputModulatorReplicated2;


    /**
     * Instantiates a new replicator.
     *
     * @param name String
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

        getSource().connect(passThrough.getInput());
        passThrough.getOutput1().connect(getSink());
        passThrough.getOutput2().connect(outputModulatorReplicated1.getInput());
        passThrough.getOutput3().connect(outputModulatorReplicated2.getInput());


    }

    @Override
    public void activate() {
        passThrough.activate();
    }

    @Override
    public void deactivate() {
        passThrough.deactivate();
    }

    @Override
    public boolean isActivated() { return passThrough.isActivated(); }

    /**
     * Return the first replicated output, corresponding to output number two.
     *
     * @return IOutput
     */
    public IOutput getOutputReplicated1(){ return outputModulatorReplicated1.getOutput(); }

    /**
     * Return the second replicated output, corresponding to output number three.
     *
     * @return IOutput
     */
    public IOutput getOutputReplicated2(){ return outputModulatorReplicated2.getOutput(); }

    /**
     * Return the module handling the signal replication.
     *
     * @return IPassThrough
     */
    public IPassThrough getPassThrough(){ return passThrough; }
}
