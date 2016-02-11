package org.istic.synthlab.core.utils.jsyn;

import com.jsyn.unitgen.UnitBinaryOperator;

public class VcaFunction extends UnitBinaryOperator {

    @Override
    public void generate(int start, int limit) {
        double[] inputAmplitudes = inputA.getValues();
        double[] wheelModulations = inputB.getValues();
        double[] outputs = output.getValues();

        for (int i = start; i < limit; i++) {
            //translate modulations to Decibels and convert to an amplitude coefficient
            outputs[i] =  Math.pow(10, 60*(inputAmplitudes[i] + wheelModulations[i] - 1)/20)-0.001;
        }
    }
}
