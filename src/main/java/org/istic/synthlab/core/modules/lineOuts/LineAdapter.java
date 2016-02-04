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
 * The class Line adapter that implements the interface ILineOut
 */
public class LineAdapter implements ILineOut {

    private LineOut lineOut;
    private FilterStateVariable filter;
    private Potentiometer potentiometer;
    private IInput input;

    /**
     * Instantiates a new Line adapter.
     * @param component
     */
    public LineAdapter(IComponent component) {
        lineOut = new LineOut();
        filter = new FilterStateVariable();
        this.input = ModulesFactory.createInput(filter.input);

        // First declare the mappings
        IOMapping.declare(component, filter);
        IOMapping.declare(component, lineOut);
        IOMapping.declare(component, input, filter.input);

        filter.amplitude.setDefault(0.5);
        filter.output.connect(this.lineOut.input);

        this.potentiometer = new Potentiometer("Volume", PotentiometerType.LINEAR, 10.0, 0.0, 3.0);

    }

    /**
     * Sets volume of the filter
     *
     * @param value the value
     */
    public void setVolume(double value) {
        filter.amplitude.set(value);
    }

    /**
     * Returns the potentiometer
     *
     * @return Potentiometer
     */
    @Override
    public Potentiometer getPotentiometer() {
        return potentiometer;
    }

    /**
     * Gets the  volume of the potentiometer.
     *
     * @return the volume : double
     */
    public double getVolume() {
        return filter.amplitude.getValue();
    }

    /**
     * Start the filter and the lineOut
     */
    public void start() {
        filter.start();
        lineOut.start();
    }

    /**
     * Stop the filter and the lineOut
     */
    public void stop() {
        filter.stop();
        lineOut.stop();
    }

    /**
     * Gets line out.
     *
     * @return UnitInputPort
     */
    public UnitInputPort getLineOut() {
        return this.filter.input;
    }

    /**
     * Gets the input port.
     *
     * @return  IInput,the input port
     */
    @Override
    public IInput getInput() {
        return input;
    }


    @Override
    public UnitGenerator getUnitGeneratorLineOut() {
        return this.lineOut;
    }

    @Override
    public UnitGenerator getUnitGeneratorFilter() {
        return this.filter;
    }

    /**
     * Activates the line out
     *
     */
    @Override
    public void activate() {
        this.lineOut.setEnabled(true);
    }

    /**
     *  deactivates the line out
     *
     */
    @Override
    public void desactivate() {
        this.lineOut.setEnabled(false);
    }
}
