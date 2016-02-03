package org.istic.synthlab.core;

import java.util.HashSet;
import java.util.Set;

public abstract class AComponent implements IComponent {

    private String name;
    private Resource resource; // Adapter component JSYN
    private Set<Params> param;
    private Set<IInput> inputs;
    private Set<IOutput> outputs;

    public AComponent(String name) {
        setName(name);
        param = new HashSet<>();
        inputs = new HashSet<>();
        outputs = new HashSet<>();

        // Futur appel à la factory pour instancier la Resource.
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

    public Set<IInput> getInputs() {
        return this.inputs;
    }

    public void setInputs(Set<IInput> inputs) {
        this.inputs = inputs;
    }

    public Set<IOutput> getOutputs() {
        return this.outputs;
    }

    public void setOutputs(Set<IOutput> outputs) {
        this.outputs = outputs;
    }

    public Set<Params> getParam() {
        return param;
    }

    public void setParam(Set<Params> param) {
        this.param = param;
    }

    public void activate() {
        resource.activate();
    }

    public void desactivate() {
        resource.desactivate();
    }

}
