package org.istic.synthlab.components.vcoa;

import com.jsyn.unitgen.UnitOscillator;


public class OscillatorAdapter implements Oscillator {

    private UnitOscillator unitOscillator;
    private double frequency;

    public OscillatorAdapter() {
        this.frequency = 0.0;
    }
}
