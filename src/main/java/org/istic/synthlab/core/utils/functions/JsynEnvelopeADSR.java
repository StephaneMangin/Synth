package org.istic.synthlab.core.utils.functions;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.UnitGate;

/**
 *
 * @author Ngassam Paola Jovany <npaolita[at]yahoo[dot]fr>
 */
public class JsynEnvelopeADSR extends UnitGate {

    public UnitInputPort attack;
    public UnitInputPort decay;
    public UnitInputPort sustain;
    public UnitInputPort release;
    public UnitInputPort amplitude;

    public enum State {
        NOTHING, ATTACK, DECAY, SUSTAIN, RELEASE
    }

    private State state;

    /**
     * Instantiates a new jsyn envelope adsr.
     */
    public JsynEnvelopeADSR() {
        super();
        addPort(attack = new UnitInputPort("Attack"));
        addPort(decay = new UnitInputPort("Decay"));
        addPort(sustain = new UnitInputPort("Sustain"));
        addPort(release = new UnitInputPort("Release"));
        addPort(amplitude = new UnitInputPort("Amplitude"));
        state = State.NOTHING;
    }

    @Override
    public void generate(int start, int limit) {
        double[] inputs = input.getValues();
        double[] outputs = output.getValues();
        double[] amplitudes = amplitude.getValues();
    }

    public UnitInputPort getAttack() {
        return this.attack;
    }

    public UnitInputPort getDecay() {
        return this.decay;
    }

    public UnitInputPort getSustain() {
        return this.sustain;
    }

    public UnitInputPort getRelease() {
        return this.release;
    }

    public UnitInputPort getAmplitude() {
        return this.amplitude;
    }
}
