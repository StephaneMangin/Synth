package org.istic.synthlab.core.modules.algorithms;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitFilter;
import com.jsyn.unitgen.UnitGenerator;

/**
 *
 * @author Thibaud Hulin <thibaud[dot]hulin[dot]cl[at]gmail[dot]com>
 */
public class VcoaFrequencyModulatorGenerator extends UnitGenerator {
    public UnitInputPort potentiometer;
    public UnitInputPort frequencyModulation;
    public UnitOutputPort output;

    public VcoaFrequencyModulatorGenerator() {
        potentiometer = new UnitInputPort("PotentiometerInput");
        frequencyModulation = new UnitInputPort("FrequencyModulation");
        output = new UnitOutputPort("Output");
        frequencyModulation.setDefault(0.0);
        potentiometer.setDefault(880.0);
        addPort(potentiometer);
        addPort(frequencyModulation);
        addPort(output);
    }

    @Override
    public void generate(int start, int limit) {
        double[] wheelFrequencies = potentiometer.getValues();
        double[] inputFrequencies = frequencyModulation.getValues();
        double[] outputs = output.getValues();
        frequencyModulation.get(0);

        for (int i = start; i < limit; i++) {
            outputs[i] =  wheelFrequencies[i]*Math.pow(2, inputFrequencies[i]);
        }
    }
}
