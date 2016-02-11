package org.istic.synthlab.core.modules.functions;

import com.jsyn.unitgen.UnitBinaryOperator;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;

public class AbstractFunction implements IFunction {
    private IComponent component;
    /** Signal input*/
    private IInput inputA;
    /** Modulation input */
    private IInput inputB;
    /** Signal output */
    private IOutput output;
    private UnitBinaryOperator unitBinaryOperator;

    protected AbstractFunction(IComponent component, UnitBinaryOperator unitBinaryOperator) {
        this.component = component;
        this.unitBinaryOperator = unitBinaryOperator;
        // Declare the generator to the register
        Register.declare(component, unitBinaryOperator);
        // Link differents ports
        this.inputA = Factory.createInput("In", component, getOperator().inputA);
        this.inputB = Factory.createInput("Mod", component, getOperator().inputB);
        this.output = Factory.createOutput("Out", component, getOperator().output);
    }

    public UnitBinaryOperator getOperator() {
        return unitBinaryOperator;
    }


    public IComponent getComponent() {
        return component;
    }


    public IInput getInputB() {
        return this.inputB;
    }


    public IInput getInputA() {
        return this.inputA;
    }


    @Override
    public IOutput getOutput() {
        return this.output;
    }

    @Override
    public void setInputB(double value) {
        this.unitBinaryOperator.inputB.setDefault(value);
    }
}
