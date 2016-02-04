package org.istic.synthlab.core.modules.parametrization;

public class Potentiometer extends GenericsParam<Double> {

    private double defaultValue = 0.0;
    private PotentiometerType type;

    public Potentiometer(String label, PotentiometerType type, Double max, Double min, Double value) {
        super(label, max, min, value);
        this.type = type;
    }









}
