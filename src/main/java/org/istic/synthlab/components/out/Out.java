package org.istic.synthlab.components.out;

import com.jsyn.Synthesizer;
import org.istic.synthlab.core.AComponent;
import org.istic.synthlab.core.AdapterFactory;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.lineOuts.ILineOut;
import org.istic.synthlab.core.modules.lineOuts.LineAdapter;
import org.istic.synthlab.core.modules.lineOuts.LineType;


public class Out extends AComponent {

    private ILineOut lineOut;
    private IInput input;

    public Out(String name) {
        super(name);
        lineOut = AdapterFactory.createLineOut(this, LineType.OUT);
    }

    @Override
    public void activate() {
        //?
    }

    @Override
    public void desactivate() {
        //?
    }

    @Override
    public void init() {
    }

    @Override
    public void run() {
        //?
    }

}
