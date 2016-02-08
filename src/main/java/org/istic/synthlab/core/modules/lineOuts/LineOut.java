package org.istic.synthlab.core.modules.lineOuts;

import com.jsyn.unitgen.FilterStateVariable;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;
import org.istic.synthlab.core.utils.parametrization.PotentiometerType;


/**
 * The class Line adapter.
 *
 * The class Line adapter that implements the interface ILineOut
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class LineOut implements ILineOut {

    private com.jsyn.unitgen.LineOut lineOut;
    private FilterStateVariable filter;
    private Potentiometer potentiometer;
    private IInput input;

    /**
     * Instantiates a new Line adapter.
     * @param component
     */
    public LineOut(IComponent component) {
        lineOut = new com.jsyn.unitgen.LineOut();
        filter = new FilterStateVariable();
        input = Factory.createInput("In", component, filter.input);

        // First declare the mappings
        Register.declare(component, filter);
        Register.declare(component, lineOut);
        Register.declare(component, input, filter.input);

        // Needed to make mono to stereo
        filter.output.connect(0, lineOut.input, 0);
        filter.output.connect(0, lineOut.input, 1);
        potentiometer = new Potentiometer("Volume", filter.amplitude, PotentiometerType.LINEAR, 1.0, 0.0, 0.2);

    }

    /**
     * Sets volume of the filter
     *
     * @param value the value
     */
    public void setVolume(double value) {
        potentiometer.setValue(value);
    }

    /**
     * Gets the  volume of the potentiometer.
     *
     * @return the volume : double
     */
    public double getVolume() {
        return potentiometer.getValue();
    }

    /**
     * Start the filter and the lineOut
     */
    public void start() {
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
        lineOut.setEnabled(true);
    }

    /**
     *  deactivates the line out
     *
     */
    @Override
    public void desactivate() {
        lineOut.setEnabled(false);
    }

    /**
     * Returns true if the lineout is activate or false
     *
     * @return boolean
     */
    @Override
    public boolean isEnable() {
        return this.lineOut.isEnabled();
    }
}
