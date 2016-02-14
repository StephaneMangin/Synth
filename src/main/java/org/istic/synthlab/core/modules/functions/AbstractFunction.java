package org.istic.synthlab.core.modules.functions;

import com.jsyn.unitgen.UnitBinaryOperator;
import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;

public class AbstractFunction implements IFunction {
    private IComponent component;
    /** Signal input*/
    private IInput input;
    /** Modulation input */
    private IInput variableInput;
    /** Signal output */
    private IOutput output;
    private UnitBinaryOperator unitBinaryOperator;

    protected AbstractFunction(IComponent component, UnitBinaryOperator unitBinaryOperator) {
        this.component = component;
        this.unitBinaryOperator = unitBinaryOperator;
        // Declare the generator to the register
        Register.declare(component, unitBinaryOperator);
        // Link differents ports
        this.input = Factory.createInput("In", component, unitBinaryOperator.inputA);
        this.variableInput = Factory.createInput("Mod", component, unitBinaryOperator.inputB);
        this.output = Factory.createOutput("Out", component, unitBinaryOperator.output);
    }

    public IComponent getComponent() {
        return component;
    }


    public IInput getVariableInput() {
        return this.variableInput;
    }


    public IInput getInput() {
        return this.input;
    }


    @Override
    public IOutput getOutput() {
        return this.output;
    }

    @Override
    public void setVariableInput(double value) {
        this.unitBinaryOperator.inputB.set(value);
    }

}
