package org.istic.synthlab.core.modules.parametrization;

/**
 *
 * The class Params.
 *
 * @param <T> the type parameter
 */
public class GenericsParam<T> {

    private T max;
    private T min;
    private T defaultValue = null;
    private T value;
    private String label;

    /**
     * Instantiates a new Params.
     *
     * @param label the label
     * @param max   the max
     * @param min   the min
     * @param value the value
     */
    public GenericsParam(String label, T max, T min, T value) {
        this.label = label;
        this.max = max;
        this.min = min;
        this.value = value;
        this.defaultValue = value;
    }

    /**
     * Gets label.
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets label.
     *
     * @param label the label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Gets the max value.
     *
     * @return the max
     */
    public T getMax() {
        return max;
    }

    /**
     * Sets the max value.
     *
     * @param max the max
     */
    public void setMax(T max) {
        this.max = max;
    }

    /**
     * Gets min value.
     *
     * @return the min
     */
    public T getMin() {
        return min;
    }

    /**
     * Sets min value.
     *
     * @param min the min
     */
    public void setMin(T min) {
        this.min = min;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(T value) {
        this.value = value;
    }
}
