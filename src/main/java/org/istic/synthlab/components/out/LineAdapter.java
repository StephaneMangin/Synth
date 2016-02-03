package org.istic.synthlab.components.out;

import com.jsyn.unitgen.LineOut;


public class LineAdapter implements ILineOut {

    private LineOut lineOut;

    public LineAdapter() {
        this.lineOut = new LineOut();
    }

    @Override
    public LineOut getLineOut() {
        return this.lineOut;
    }

    @Override
    public void setLineOut(LineOut lineOut) {
        this.lineOut = lineOut;
    }

}
