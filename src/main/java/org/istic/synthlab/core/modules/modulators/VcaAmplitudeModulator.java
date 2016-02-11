package org.istic.synthlab.core.modules.modulators;

import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.utils.jsyn.VcaFunction;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;

public class VcaAmplitudeModulator extends AbstractModulator {
    private VcaFunction function;

    public VcaAmplitudeModulator(String name, IComponent component, PotentiometerType type) {
        super(name, component);
        function = new VcaFunction();
        potentiometer = new Potentiometer(
                "Amplitude",
                function.inputB,
                type,
                2D,
                0.0,
                0.0
        );

        // Declare the relation to the mapping
        Register.declare(component, this.function);
        input = Factory.createInput(name + "::amIn", component, function.inputA);
        output = Factory.createOutput(name + "::amOut", component, function.output);
    }

    @Override
    public void activate() {
        function.setEnabled(true);
    }

    @Override
    public void deactivate() {
        function.setEnabled(false);
    }

    @Override
    public boolean isActivated() {
        return function.isEnabled();
    }

/*    @Override
    public void setValue(double value){
        System.out.println("intercepted !");
        potentiometer.setValue(calculateStep(value));
        System.out.println("calculatedValue : " + potentiometer.calculateStep(value));
        System.out.println("value : " + potentiometer.getValue());
    }*/
}
