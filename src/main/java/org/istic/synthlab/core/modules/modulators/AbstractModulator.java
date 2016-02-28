package org.istic.synthlab.core.modules.modulators;

import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.utils.parametrization.Potentiometer;

/**
 * Create an abstraction to manage a potentiometer through an internal logic
 *
 * 'Abstract Modulator' representation
 * -----------------------------------
 *
 *        External View
 *        +-----------------------------------------------------------+
 *        |                 +-------------------------------+         |
 *      +-+-+               |                               |       +-+-+
 * input|   +---------------> Internal custom logic         +------->   | Output
 *      +-+-+               |                               |       +-+-+
 *        |           +-----+                               |         |
 *        |           |     |                               |         |
 *        |           |     +-------------------------------+         |
 *        |           |                                               |
 *        |           |setPotentiometer                               |
 *        |         +-v-------------------+                           |
 *        |         | Potentiometer       |                           |
 *        |         |                     +-------------------------------> getters/setters for
 *        |         +---------------------+                           |      - Value & RawValue
 *        |                                                           |      - Min & Max
 *        | Internal view                                             |
 *        +-----------------------------------------------------------+
 *                                    Made with : http://asciiflow.com/
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public abstract class AbstractModulator implements IModulator {
    private final IComponent component;
    private Potentiometer potentiometer;
    protected IOutput output;
    protected IInput input;
    private final String name;

    public AbstractModulator(String name, IComponent component) {
        this.name = name;
        this.component = component;
    }

    @Override
    public IInput getInput() {
        return input;
    }

    @Override
    public IOutput getOutput() {
        return output;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public double getRawValue() {
        return potentiometer.getRawValue();
    }

    public double getValue() {
        return potentiometer.getValue();
    }

    public void setValue(double value) {
        potentiometer.setValue(value);
    }

    @Override
    public void setRawValue(double value) {
        potentiometer.setRawValue(value);
    }

    public double getMax() {
        return potentiometer.getMax();
    }

    public double getMin() {
        return potentiometer.getMin();
    }

    public void setMax(double value) {
        potentiometer.setMax(value);
    }

    public void setMin(double value) {
        potentiometer.setMin(value);
    }

    protected void setPotentiometer(Potentiometer potentiometer) {
        this.potentiometer = potentiometer;
    }
}
