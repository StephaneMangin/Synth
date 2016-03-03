package org.istic.synthlab.core.utils.functions;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

/**
 * @author Thibaud Hulin thibaud[dot]hulin[dot]cm[at]gmail[dot]com
 */
public class GainFunction extends UnitGenerator {
    public UnitInputPort potentiometer;
    public UnitInputPort input;
    public UnitOutputPort output;
    public GainFunction() {
        potentiometer = new UnitInputPort("PotentiometerInput");
        input = new UnitInputPort("Input");
        output = new UnitOutputPort("Output");
        input.setDefault(0.0);
        potentiometer.setDefault(1.0);
        addPort(potentiometer);
        addPort(input);
        addPort(output);
    }
    @Override
    public void generate(int start, int limit) {
        double[] wheelGain = potentiometer.getValues();
        double[] inputSignal = input.getValues();
        double[] outputs = output.getValues();

        for (int i = start; i < limit; i++) {
            //Conversion of potentiometer in decibel, then bounded between 0 and 1
            outputs[i] =  inputSignal[i] * wheelGain[i];//20*Math.log(2*wheelGain[i]) / 12
        }
    }
}