package org.istic.synthlab.core.utils.jsyn;

import com.jsyn.unitgen.UnitOscillator;
import com.jsyn.util.PseudoRandom;

/**
 * Delegates generation to the jsyn WhiteNoise oscillator which does not inherits from UnitOscillators.
 * Needed by the white noise IOscillator modules.
 *
 * by blacknight on 14/02/16.
 */
public class WhiteNoise extends UnitOscillator {
    private PseudoRandom randomNum = new PseudoRandom();

    public WhiteNoise() {
    }

    public void generate(int var1, int var2) {
        double[] var3 = this.amplitude.getValues();
        double[] var4 = this.output.getValues();

        for(int pass = var1; pass < var2; ++pass) {
            var4[pass] = this.randomNum.nextRandomDouble() * var3[pass];
        }

    }
}