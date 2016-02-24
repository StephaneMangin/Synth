package org.istic.synthlab.core.modules.sequencer;

import com.jsyn.unitgen.UnitFilter;

/**
 * @author Dechaud John Marc johnmarcdechaud[at]gmail[dot]com on 2/22/16.
 */
public class VoltageProducer extends UnitFilter{
    @Override
    public void generate(int start, int limit) {
        double[] inputValues = input.getValues();
        double[] outputValues = output.getValues();
        for (int i = start; i < limit; i++ ){
            outputValues[i] = inputValues[i];
        }
    }
}
