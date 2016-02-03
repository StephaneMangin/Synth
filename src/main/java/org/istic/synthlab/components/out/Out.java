package org.istic.synthlab.components.out;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import org.istic.synthlab.core.AComponent;


public class Out extends AComponent {

    private ILineOut lineOut;
    private Synthesizer synthesizer;

    public Out(String name) {
        super(name);
    }

    public void start() {
        this.synthesizer.add(this.lineOut.getLineOut());
        this.synthesizer.start();
    }

    public void stop() {
        this.synthesizer.stop();
    }

    public void init() {
        this.synthesizer = JSyn.createSynthesizer();
    }

    public void run() {
    }

    public void setLineOut(ILineOut lineOut) {
        this.lineOut = lineOut;
    }

}
