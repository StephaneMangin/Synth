package org.istic.synthlab.core.modules.whitenoise;
import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;

public class WhiteNoise implements IWhiteNoise {

    private com.jsyn.unitgen.WhiteNoise whiteNoise;
    private IOutput output;

    public WhiteNoise(IComponent component) {
        super();
        whiteNoise = new com.jsyn.unitgen.WhiteNoise();
        Register.declare(component, whiteNoise);
        whiteNoise.output.setName("noise_output");
        output = Factory.createOutput("Out", component, this.whiteNoise.output);
    }

    @Override
    public IOutput getOutput() {
        return this.output;
    }

    public void activate() {
        whiteNoise.setEnabled(true);
    }

    public void deactivate() {
        whiteNoise.setEnabled(false);
    }

    public boolean isActivated() {
        return whiteNoise.isEnabled();
    }

}