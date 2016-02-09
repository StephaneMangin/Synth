package org.istic.synthlab.core.modules.passThrough;

import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;

/**
 * Created by cyprien on 08/02/16.
 */
public class PassThrough implements IPassThrough {

    private com.jsyn.unitgen.PassThrough passThroughIn;
    private com.jsyn.unitgen.PassThrough passThroughOut1;
    private com.jsyn.unitgen.PassThrough passThroughOut2;
    private com.jsyn.unitgen.PassThrough passThroughOut3;
    private IInput input;
    private IOutput output1;
    private IOutput output2;
    private IOutput output3;

    public PassThrough(IComponent component){

        passThroughIn = new com.jsyn.unitgen.PassThrough();

        passThroughOut1 = new com.jsyn.unitgen.PassThrough();
        passThroughOut2 = new com.jsyn.unitgen.PassThrough();
        passThroughOut3 = new com.jsyn.unitgen.PassThrough();

        input = Factory.createInput("In", component, passThroughIn.input);

        output1 = Factory.createOutput("Out1", component, passThroughOut1.output);
        output2 = Factory.createOutput("Out2", component, passThroughOut2.output);
        output3 = Factory.createOutput("Out3", component, passThroughOut3.output);

        // First declare the components
        // In
        Register.declare(component, passThroughIn);

        // Out
        Register.declare(component, passThroughOut1);
        Register.declare(component, passThroughOut2);
        Register.declare(component, passThroughOut3);

        // Then declare the mappings
        Register.declare(component, input, passThroughIn.input);

        Register.declare(component, output1, passThroughOut1.output);
        Register.declare(component, output2, passThroughOut2.output);
        Register.declare(component, output3, passThroughOut3.output);

        // Internal mapping to make the replicator work.
        passThroughIn.output.connect(passThroughOut1.input);
        passThroughIn.output.connect(passThroughOut2.input);
        passThroughIn.output.connect(passThroughOut3.input);


    }

    @Override
    public IInput getInput() { return input; }

    @Override
    public IOutput getOutput1() {
        return output1;
    }

    @Override
    public IOutput getOutput2() {
        return output2;
    }

    @Override
    public IOutput getOutput3() {
        return output3;
    }

    /**
     * Activates the line out
     *
     */

    @Override
    public void activate() {
        passThroughIn.setEnabled(true);
    }

    /**
     *  deactivates the line out
     *
     */
    @Override
    public void deactivate() {
        passThroughIn.setEnabled(false);
    }


    @Override
    public boolean isEnable() {
        return passThroughIn.isEnabled();
    }
}
