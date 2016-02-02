package org.istic.synthlab.out;

import org.istic.synthlab.core.AComponent;

/**
 * Created by stephane on 02/02/16.
 */
public class Out extends AComponent {

    private ILineOut lineOut;

    public Out(String name) {
        super(name);
    }

    public void start() {

    }

    public void stop() {

    }

    public void init() {

    }

    public void run() {

    }

    public void setLineOut(ILineOut lineOut) {
        this.lineOut = lineOut;
    }
}
