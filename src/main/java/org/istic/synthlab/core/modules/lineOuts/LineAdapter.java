package org.istic.synthlab.core.modules.lineOuts;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.FilterStateVariable;
import com.jsyn.unitgen.LineOut;


public class LineAdapter implements ILineOut {

    private LineOut lineOut;
    private FilterStateVariable filter;

    public LineAdapter() {
        this.lineOut = new LineOut();
        filter = new FilterStateVariable();
        filter.amplitude.setDefault(0.5);
        filter.output.connect(this.lineOut.input);
    }

    public void setVolume(double value) {
        filter.amplitude.set(value);
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

}
