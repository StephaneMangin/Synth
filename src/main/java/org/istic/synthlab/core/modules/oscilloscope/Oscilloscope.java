package org.istic.synthlab.core.modules.oscilloscope;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.scope.AudioScope;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;

/**
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class Oscilloscope implements IOscilloscope {

    private IComponent component;
    private IInput input;
    private IOutput output;
    private AudioScope scope;

    public Oscilloscope(IComponent component) {
        this.component = component;
        scope = new AudioScope(Factory.createSynthesizer());
        // Declare the generator to the register
        //Register.declare(component, scope);
        // Link differents ports
        UnitInputPort probeInput = new UnitInputPort("probeIn");
        UnitOutputPort probeOutput = new UnitOutputPort("probeOut");
        input = Factory.createInput("In", component, probeInput);
        output = Factory.createOutput("Out", component, probeOutput);
        input.connect(output);
        scope.addProbe(probeOutput);
    }

    public IComponent getComponent() {
        return component;
    }

    @Override
    public IInput getInput() {
        return input;
    }

    @Override
    public IOutput getOutput() {
        return output;
    }

    @Override
    public JPanel getNode() {
        return scope.getView();
    }

    @Override
    public void setAmplitude(double value) {
        throw new NotImplementedException();
    }

    @Override
    public void setPeriod(double value) {
        throw new NotImplementedException();
    }
}