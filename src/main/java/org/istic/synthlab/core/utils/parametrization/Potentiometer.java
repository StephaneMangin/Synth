package org.istic.synthlab.core.utils.parametrization;

import com.jsyn.ports.UnitInputPort;

/**
 * Manager for linear or exponential values inside views.
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 * @author Cyprien Gottstein <gottstein[dot]cyprien[at]gmail[dot]com>
 *
 */
public class Potentiometer extends GenericsParam<Double> {

    public static final int POWER_SCALE = 10;
    private final UnitInputPort port;
    private PotentiometerType type;

    /**
   0  * Instantiates a new Potentiometer.
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
        // Call the super because of convertion in the current setter
        setValue(value);
        setMin(min);
        setMax(max);
    }

    /**
     * Set the value of the potentiometer
     *
     * @param value double between 0 to 1
     */
    public void setValue(double value) {
        if (value <= getMax() && value >= getMin()) {
            super.setValue(value);
            this.port.set(value);
        }
    }

    /**
     * Get the value of the potentiometer
     *
     * @return  value Double between 0 to 1
     */
    public Double getValue() {
       return this.port.get();
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

    /**
     * Return the corrected value
     *
     * @param value double between 0 to 1
     * @return double
     */
    public double calculateStep(double value) {
        double result;
        //
        if (type == PotentiometerType.LINEAR) {
            result = (getMax() - getMin()) * value + getMin();
        } else if (type == PotentiometerType.EXPONENTIAL) {
            //128 à la place de 10 ?
            result = (getMax() - getMin()) / POWER_SCALE * Math.pow(POWER_SCALE, value) + getMin();
        } else {
            result = value;
        }
        return result;
    }

    /**
     * Return the potentiometer type
     */
    public PotentiometerType getType() {
        return type;
    }

}
