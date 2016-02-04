package org.istic.synthlab.core;

public class Params<T> {

    private T max;
    private T min;
    private final T defaultValue = null;
    private T value;
    private String label;

    public Params(String label, T max, T min, T value) {
        this.label = label;
        this.max = max;
        this.min = min;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public T getMax() {
        return max;
    }

    public void setMax(T max) {
        this.max = max;
    }

    public T getMin() {
        return min;
    }

    public void setMin(T min) {
        this.min = min;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
