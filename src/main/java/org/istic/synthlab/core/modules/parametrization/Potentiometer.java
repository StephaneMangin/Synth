package org.istic.synthlab.core.modules.parametrization;

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

    /**
     * Instantiates a new Potentiometer.
     *
     * @param label the label
     * @param type  the type
     * @param max   the max
     * @param min   the min
     * @param value the value
     */
    public Potentiometer(String label, PotentiometerType type, Double max, Double min, Double value) {
        super(label, max, min, value);
        this.type = type;
    }



}
