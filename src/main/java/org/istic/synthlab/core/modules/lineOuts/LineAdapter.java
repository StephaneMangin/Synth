package org.istic.synthlab.core.modules.lineOuts;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.FilterStateVariable;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.UnitGenerator;
import org.istic.synthlab.core.AdapterFactory;
import org.istic.synthlab.core.Potentiometer;
import org.istic.synthlab.core.PotentiometerType;
import org.istic.synthlab.core.modules.io.IInput;


/**
 * The class Line adapter.
 *
 * @author Group1
 * The type Line adapter that implements the interface ILineOut
 */
public class LineAdapter implements ILineOut {

    private LineOut lineOut;
    private FilterStateVariable filter;
    private Potentiometer potentiometer;
    private IInput input;

    /**
     * Instantiates a new Line adapter.
     */
    public LineAdapter() {
        this.lineOut = new LineOut();

        filter = new FilterStateVariable();
        filter.amplitude.setDefault(0.5);
        filter.output.connect(this.lineOut.input);

        this.input = AdapterFactory.createInput(filter.input);
        this.potentiometer = new Potentiometer("Volume", PotentiometerType.LINEAR, 10.0, 0.0, 3.0);
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
    @Override
    public Potentiometer getPotentiometer() {
        return potentiometer;
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

    /**
     * Gets the input port.
     *
     * @return the input port
     */
    @Override
    public IInput getInput() {
        return input;
    }

    /**
     * It activates the line out
     *
     */
    @Override
    public UnitGenerator getUnitGeneratorLineOut() {
        return this.lineOut;
    }

    @Override
    public UnitGenerator getUnitGeneratorFilter() {
        return this.filter;
    }

    @Override
    public void activate() {
        this.lineOut.setEnabled(true);
    }

    /**
     * It deactivates the line out
     *
     */
    @Override
    public void desactivate() {
        this.lineOut.setEnabled(false);
    }
}
