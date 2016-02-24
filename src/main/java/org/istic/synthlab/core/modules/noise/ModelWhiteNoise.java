package org.istic.synthlab.core.modules.noise;
import com.jsyn.unitgen.WhiteNoise;
import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;

public class ModelWhiteNoise implements  IModelWhiteNoise{

    private WhiteNoise whiteNoise;
    private IOutput output;

    public ModelWhiteNoise(IComponent component) {
        super();
        whiteNoise = new WhiteNoise();
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