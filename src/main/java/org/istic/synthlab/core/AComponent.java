package org.istic.synthlab.core;

public abstract class AComponent implements IComponent {

    protected String name;

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
