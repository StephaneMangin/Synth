package org.istic.synthlab.core.modules.lineOuts;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.FilterStateVariable;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.UnitGenerator;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.parametrization.Potentiometer;
import org.istic.synthlab.core.modules.parametrization.PotentiometerType;
import org.istic.synthlab.core.services.IOMapping;
import org.istic.synthlab.core.services.ModulesFactory;


/**
 * The class Line adapter.
 *
 *
 * The type Line adapter that implements the interface ILineOut
 */
public class LineAdapter implements ILineOut {

    private LineOut lineOut;
    private FilterStateVariable filter;
    private Potentiometer potentiometer;
    private IInput input;

    /**
     * Instantiates a new Line adapter.
     * @param component component that use lineAdapter
     */
    public LineAdapter(IComponent component) {
        lineOut = new LineOut();
        filter = new FilterStateVariable();
        this.input = ModulesFactory.createInput(component, filter.input);

        // First declare the mappings
        IOMapping.declare(component, filter);
        IOMapping.declare(component, lineOut);

        filter.amplitude.setDefault(0.5);
        filter.output.connect(this.lineOut.input);

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
