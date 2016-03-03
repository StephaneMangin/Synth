package org.istic.synthlab.core.utils.functions;

import com.jsyn.unitgen.UnitBinaryOperator;

/**
 *
 */
public class VcfFunction extends UnitBinaryOperator {

        @Override
        public void generate(int start, int limit) {
        double[] inputFrequencies = inputA.getValues();
        double[] wheelFrequencies = inputB.getValues();
        double[] outputs = output.getValues();

        for (int i = start; i < limit; i++) {
            outputs[i] =  wheelFrequencies[i]*Math.pow(2, inputFrequencies[i]);
            //dB=20*Math.log(Vout/Vin);
            //-6dB = Vout/2
        }
    }
}
