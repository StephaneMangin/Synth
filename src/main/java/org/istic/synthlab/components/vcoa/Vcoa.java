package org.istic.synthlab.components.vcoa;

import org.istic.synthlab.core.AComponent;
import org.istic.synthlab.core.AdapterFactory;
import org.istic.synthlab.core.IOMappingService;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.oscillators.IOscillator;
import org.istic.synthlab.core.modules.oscillators.OscillatorType;

public class Vcoa extends AComponent {

    private IOscillator sineOscillator;
    private IOutput output;

    public Vcoa(String name) {
        super(name);
        this.sineOscillator = AdapterFactory.createOscillator(this, OscillatorType.SINE);
        IOMappingService.declare(this, this.sineOscillator.getUnitGenerator());

        this.output = this.sineOscillator.getOutput();
        IOMappingService.declare(this, output, output.getUnitInputPort());

    }

    @Override
    public void activate() {
        this.sineOscillator.activate();
    }

    @Override
    public void desactivate() {
        this.sineOscillator.desactivate();
    }

    @Override
    public void init() {
    }

    @Override
    public void run() {
    }

    public IOutput getOutput() {
        return this.output;
    }

    public IOscillator getSineOscillator() {
        return this.sineOscillator;
    }
}
