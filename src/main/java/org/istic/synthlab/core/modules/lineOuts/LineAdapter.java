package org.istic.synthlab.core.modules.lineOuts;

import com.jsyn.unitgen.FilterStateVariable;
import com.jsyn.unitgen.LineOut;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;
import org.istic.synthlab.core.modules.modulators.ModulatorType;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.services.ModulesFactory;
import org.istic.synthlab.core.services.Register;


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
        this.input = ModulesFactory.createInput("In", component, filter.input);

        // First declare the mappings
        Register.declare(component, filter);
        Register.declare(component, lineOut);
        Register.declare(component, input, filter.input);

        filter.amplitude.setDefault(0.5);
        filter.output.connect(this.lineOut.input);

        this.potentiometer = new Potentiometer("Volume", filter.amplitude, PotentiometerType.LINEAR, 10.0, 0.0, 3.0);

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
        ModulesFactory.createSynthesizer().start();
        lineOut.start();
    }

    /**
     * Stop the filter and the lineOut
     */
    public void stop() {
        lineOut.stop();
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
