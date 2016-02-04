package org.istic.synthlab.core.modules.parametrization;

import com.jsyn.ports.UnitInputPort;

/**
 * Manager for linear or exponential values inside views.
 *
 * @author Stéphane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 * @author Cyprien Gottstein <gottstein[dot]cyprien[at]gmail[dot]com>
 *
 */
public class Potentiometer extends GenericsParam<Double> {

    private double defaultValue = 0.0;
    private PotentiometerType type;
    private ValueType valType;
    private double max;
    private double min;
    private UnitInputPort port;

    /**
     * Instantiates a new Potentiometer.
     *
     * @param label the label
     * @param type  the type
     * @param port  the type
     * @param valType  the type
     * @param max   the max
     * @param min   the min
     * @param value the value
     */
    public Potentiometer(String label, PotentiometerType type, UnitInputPort port, ValueType valType, double max, double min, double value) {
        super(label, value);
        this.type = type;
        this.max = max;
        this.min = min;
        this.valType = valType;
        this.port = port;
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

}
