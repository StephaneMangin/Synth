package org.istic.synthlab.core.utils.parametrization;

import com.jsyn.ports.UnitInputPort;

/**
 * Manager for linear or exponential values inside views.
 *
 * @author Stéphane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 * @author Cyprien Gottstein <gottstein[dot]cyprien[at]gmail[dot]com>
 *
 */
public class Potentiometer extends GenericsParam<Double> {

    public static final int POWER_SCALE = 10;
    private final UnitInputPort port;
    private PotentiometerType type;

    /**
     * Instantiates a new Potentiometer.
     *
     *  @param label the label
     * @param port
     * @param type  the type
     * @param max   the max
     * @param min   the min
     * @param value the value
     */
    public Potentiometer(String label, UnitInputPort port, PotentiometerType type, double max, double min, double value) {
        super(label, value);
        this.type = type;
        this.port = port;

        // Set the value of the port
        this.port.setDefault(value);
        setValue(value);
        setMin(min);
        setMax(max);
    }

    public void setValue(double value) {
        if (value <= getMax() && value >= getMin()) {
            super.setValue(value);
            this.port.set(value);
        }
    }

    /**
     * Gets the max value.
     *
     * @return the max
     */
    public double getMax() {
        return this.port.getMaximum();
    }

    /**
     * Sets the max value.
     *
     * @param max the max
     */
    public void setMax(double max) {
        this.port.setMaximum(max);
    }

    /**
     * Gets min value.
     *
     * @return the min
     */
    public double getMin() {
        return this.port.getMinimum();
    }

    /**
     * Sets min value.
     *
     * @param min the min
     */
    public void setMin(double min) {
        this.port.setMinimum(min);
    }

    public double calculateStep(double wheelInput) {
        double value;
        if (type == PotentiometerType.LINEAR) {
            value = (getMax() - getMin()) * wheelInput + getMin();
        } else if (type == PotentiometerType.EXPONENTIAL) {
            //128 à la place de 10 ?
            value = (getMax() - getMin()) / POWER_SCALE * Math.pow(POWER_SCALE, wheelInput) + getMin();
        } else {
            value = wheelInput;
        }
        return value;
    }

    /**
     * Return the potentiometer type
     */
    public PotentiometerType getType() {
        return type;
    }
}
