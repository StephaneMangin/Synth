package org.istic.synthlab.core;

/**
 * @author  Group1
 * The class Potentiometer.
 */
public class Potentiometer extends Params<Double> {

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
