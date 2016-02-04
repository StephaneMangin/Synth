package org.istic.synthlab.core.modules.parametrization;

/**
 * Manager for linear or exponential values inside views.
 *
 * @author Stéphane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 * @author Cyprien Gottstein <gottstein[dot]cyprien[at]gmail[dot]com>
 *
 */
public class Potentiometer extends GenericsParam<Double> {

    private static final int POWER_SCALE = 10;
    private double defaultValue = 0.0;
    private PotentiometerType type;
    private ValueType valType;
    private double max;
    private double min;

    /**
     * Instantiates a new Potentiometer.
     *
     * @param label the label
     * @param type  the type
     * @param max   the max
     * @param min   the min
     * @param value the value
     */
    public Potentiometer(String label, PotentiometerType type, double max, double min, double value) {
        super(label, value);
        this.type = type;
        this.max = max;
        this.min = min;
    }


    /**
     * Gets the max value.
     *
     * @return the max
     */
    public double getMax() {
        return max;
    }

    /**
     * Sets the max value.
     *
     * @param max the max
     */
    public void setMax(double max) {
        this.max = max;
    }

    /**
     * Gets min value.
     *
     * @return the min
     */
    public double getMin() {
        return min;
    }

    /**
     * Sets min value.
     *
     * @param min the min
     */
    public void setMin(double min) {
        this.min = min;
    }

    public double calculateStep(double wheelInput) {
        double value;
        if (type == PotentiometerType.LINEAR) {
            value = (getMax() - getMin()) * wheelInput + getMin();
        } else {
            //128 à la place de 10 ?
            value = (getMax() - getMin()) / POWER_SCALE * Math.pow(POWER_SCALE, wheelInput) + getMin();
        }
        return value;
    }
}
