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

    @Override
    public void activate() {
        this.lineOut.start();
    }

    @Override
    public void deactivate() {
        this.lineOut.stop();
    }

    @Override
    public void init() {
        this.lineOut.setVolume(1);
    }

    @Override
    public void run() {
        this.lineOut.start();
    }

}
