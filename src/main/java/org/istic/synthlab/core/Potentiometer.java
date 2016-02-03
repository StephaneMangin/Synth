package org.istic.synthlab.core;

public class Potentiometer extends Params<Double> {

    private double defaultValue = 0.0;
    private PotentiometerType type;

    public Potentiometer(String label, PotentiometerType type, Double max, Double min, Double value) {
        super(label, max, min, value);
        this.type = type;
    }









}
