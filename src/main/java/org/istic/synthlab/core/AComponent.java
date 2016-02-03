package org.istic.synthlab.core;

/**
 * Created by stephane on 02/02/16.
 */
public abstract class AComponent implements IComponent {

    private String name;

    public AComponent(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public void rename(String name) {
        setName(name);
    }

}
