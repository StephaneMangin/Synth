package org.istic.synthlab.components.out;

import com.jsyn.Synthesizer;
import org.istic.synthlab.core.AComponent;
import org.istic.synthlab.core.AdapterFactory;
import org.istic.synthlab.core.modules.lineOuts.ILineOut;
import org.istic.synthlab.core.modules.lineOuts.LineAdapter;
import org.istic.synthlab.core.modules.lineOuts.LineType;


public class Out extends AComponent {

    private ILineOut lineOut;
    private Synthesizer synthesizer;

    public Out(String name) {
        super(name);
        lineOut = AdapterFactory.createLineOut(this, LineType.OUT);
    }

    public void start() {
        this.lineOut.start();
    }

    public void stop() {
        this.lineOut.stop();
    }

    public void init() {
    }

    public void run() {
    }

}
