package org.istic.synthlab.components.out;

import com.jsyn.unitgen.LineOut;

/**
 * Created by stephane on 02/02/16.
 */
public class LineAdapter implements ILineOut {
    private LineOut lineOut;

    public LineAdapter(LineOut lineOut) {
        this.lineOut = lineOut;
    }
}
