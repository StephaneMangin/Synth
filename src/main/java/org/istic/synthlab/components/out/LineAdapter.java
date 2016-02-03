package org.istic.synthlab.components.out;

import com.jsyn.unitgen.LineOut;
import org.istic.synthlab.core.AdapterFactory;
import org.istic.synthlab.core.modules.lineOuts.ILineOut;
import org.istic.synthlab.core.modules.lineOuts.LineType;


public class LineAdapter implements ILineOut {

    private LineOut lineOut;

    public LineAdapter() {
        this.lineOut = new LineOut();
    }
}
