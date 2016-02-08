package org.istic.synthlab.ui;

import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;

/**
 * Created by seb on 08/02/16.
 */
public class Connection {
    private IOutput output;
    private IInput input;

    public Connection(IOutput output, IInput input) {
        this.output = output;
        this.input = input;
    }

    public boolean equals(Connection o){
        return ((this.output == o.getOutput()) && (this.input == o.getInput()));
    }

    public IOutput getOutput() {
        return output;
    }

    public void setOutput(IOutput output) {
        this.output = output;
    }

    public IInput getInput() {
        return input;
    }

    public void setInput(IInput input) {
        this.input = input;
    }
}
