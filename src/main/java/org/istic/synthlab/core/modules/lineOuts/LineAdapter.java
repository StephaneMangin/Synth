package org.istic.synthlab.core.modules.lineOuts;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.FilterStateVariable;
import com.jsyn.unitgen.LineOut;


/**
 * The type Line adapter that implements the interface ILineOut
 *
 */
public class LineAdapter implements ILineOut {

    private LineOut lineOut;
    private FilterStateVariable filter;

    /**
     * Instantiates a new Line adapter.
     */
    public LineAdapter() {
        this.lineOut = new LineOut();
        filter = new FilterStateVariable();
        filter.amplitude.setDefault(0.5);
        filter.output.connect(this.lineOut.input);
    }

    /**
     * Sets volume.
     *
     * @param value the value
     */
    public void setVolume(double value) {
        filter.amplitude.set(value);
    }

    /**
     * Gets volume.
     *
     * @return the volume
     */
    public double getVolume() {
        return filter.amplitude.getValue();
    }

    /**
     * Start.
     */
    public void start() {
        filter.start();
        lineOut.start();
    }

    /**
     * Stop.
     */
    public void stop() {
        filter.stop();
        lineOut.stop();
    }

    /**
     * Gets line out.
     *
     * @return the line out
     */
    public UnitInputPort getLineOut() {
        return this.filter.input;
    }

}
