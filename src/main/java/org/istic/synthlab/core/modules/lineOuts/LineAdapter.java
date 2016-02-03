package org.istic.synthlab.core.modules.lineOuts;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.FilterStateVariable;
import com.jsyn.unitgen.LineOut;
import org.istic.synthlab.core.AdapterFactory;
import org.istic.synthlab.core.Potentiometer;
import org.istic.synthlab.core.PotentiometerType;
import org.istic.synthlab.core.modules.io.IInput;


public class LineAdapter implements ILineOut {

    private LineOut lineOut;
    private FilterStateVariable filter;
    private Potentiometer potentiometer;
    private IInput input;

    public LineAdapter() {
        this.lineOut = new LineOut();

        filter = new FilterStateVariable();
        filter.amplitude.setDefault(0.5);
        filter.output.connect(this.lineOut.input);

        this.input = AdapterFactory.createInput(filter.input);
        this.potentiometer = new Potentiometer("Volume", PotentiometerType.LINEAR, 10.0, 0.0, 3.0);
    }

    public void setVolume(double value) {
        filter.amplitude.set(value);
    }

    @Override
    public Potentiometer getPotentiometer() {
        return potentiometer;
    }

    public double getVolume() {
        return filter.amplitude.getValue();
    }

    public void start() {
        filter.start();
        lineOut.start();
    }

    public void stop() {
        filter.stop();
        lineOut.stop();
    }

    public UnitInputPort getLineOut() {
        return this.filter.input;
    }

    @Override
    public IInput getInput() {
        return input;
    }

    @Override
    public void activate() {
        this.lineOut.setEnabled(true);
    }

    @Override
    public void desactivate() {
        this.lineOut.setEnabled(false);
    }
}
