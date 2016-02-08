package org.istic.synthlab.core;

/**
 * The abstract class component.
 */
public abstract class AComponent implements IComponent {

    /**
     * The Name.
     */
    protected String name;

    /**
     * Instantiates a new  component.
     *
     * @param name the name
     */
    public AComponent(String name) {
        setName(name);
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the name
     */
    protected void setName(String name) {
        this.name = name;
    }

    /**
     * Rename.
     *
     * @param name the name
     */
    public void rename(String name) {
        setName(name);
    }

}
