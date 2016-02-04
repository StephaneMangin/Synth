package org.istic.synthlab.core;

/**
 * Manage the potentiometer and calculate the step
 * for the buttons
 *
 * @author Cyprien Gottstein <>
 * */

public class Potentiometer extends Params<Double> {

    private double defaultValue = 0.0;
    private PotentiometerType type;
    private final int POWER_SCALE = 10;

    public Potentiometer(String label, PotentiometerType type, Double max, Double min, Double value) {
        super(label, max, min, value);
        this.type = type;
    }

    public double calculateStep(double wheelInput) {
        double value = 0.0;
        if (type == PotentiometerType.LINEAR) {
            value = (getMax() - getMin()) * wheelInput + getMin();
        } else {
            //128 à la place de 10 ?
            value = (getMax() - getMin()) / POWER_SCALE * Math.pow(POWER_SCALE, wheelInput) + getMin();
        }
        return value;
    }

}
