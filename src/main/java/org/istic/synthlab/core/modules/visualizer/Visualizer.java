package org.istic.synthlab.core.modules.visualizer;

import com.jsyn.scope.AudioScope;
import com.jsyn.scope.WaveTraceModel;
import com.jsyn.swing.ExponentialRangeModel;
import org.istic.synthlab.core.components.IComponent;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;

import javax.swing.*;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by stephane on 08/02/16.
 */
public class Visualizer implements IVisualizer {

    private AudioScope scope;
    private ConcurrentLinkedQueue<Number> values = new ConcurrentLinkedQueue<>();
    private boolean start;
    private double width;

    public Visualizer(IComponent component) {
        // Pour l'affichage des courbes
        scope = new AudioScope( Factory.createSynthesizer());
        scope.setTriggerMode(AudioScope.TriggerMode.AUTO);
        scope.getModel().getTriggerModel().getLevelModel().setDoubleValue(0.01);
    }

    @Override
    public JPanel getView() {
        return this.scope.getView();
    }

    @Override
    public void linkTo(IOutput output) {
        // Connect the audioscope to the IOutput's UnitOutputPort
        this.scope.addProbe(Register.retrieve(output), 0);
        this.scope.getModel().getProbes()[0].setVerticalScale(2.0);
        this.scope.getModel().getProbes()[0].setAutoScaleEnabled(false);

    }

    @Override
    public void activate() {
        start = true;
        this.scope.start();
    }

    @Override
    public void deactivate() {
        start = false;
        this.scope.stop();
    }

    /**
     *
     * @return the number of values to draw
     */
    public double getWidth() {
        return this.scope.getModel().getProbes()[0].getWaveTraceModel() != null
                ? this.scope.getModel().getProbes()[0].getWaveTraceModel().getVisibleSize()
                : 0;
    }

    /**
     *
     * @return the value of a signal sample
     */
    public Number getValue() {
        Number ret = values.poll();

        //Try to fill the buffer, otherwise return default value
        if (ret == null) {
            fillBuffer();
            try {
                ret = values.remove();
            } catch (NoSuchElementException e) {
                ret = 0.0;
            }
        }

        return ret;
    }

    /*
     * Copyright 2009 Phil Burk, Mobileer Inc
     *
     * Licensed under the Apache License, Version 2.0 (the "License");
     * you may not use this file except in compliance with the License.
     * You may obtain a copy of the License at
     *
     *     http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software
     * distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and
     * limitations under the License.
     */
    /**
     * Adapted from JSYN drawWave function
     *
     */
    private void fillBuffer() {
        WaveTraceModel wave = this.scope.getModel().getProbes()[0].getWaveTraceModel();
        double maxValue = 0.0;
        double minValue = 0.0;

        if (wave != null) {
            int numSamples = wave.getVisibleSize();
            if (numSamples > 0) {
                int offset = wave.getStartIndex();

                for (int i = 0; i < numSamples; i++) {
                    double value = wave.getSample(offset + i);
                    values.add(value);

                    if (value > maxValue) {
                        maxValue = value;
                    }
                    else if (value < minValue) {
                        minValue = value;
                    }
                }
                autoScale(maxValue);
            }
        }
    }

    /*
     * Copyright 2009 Phil Burk, Mobileer Inc
     *
     * Licensed under the Apache License, Version 2.0 (the "License");
     * you may not use this file except in compliance with the License.
     * You may obtain a copy of the License at
     *
     *     http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software
     * distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and
     * limitations under the License.
     */
    /**
     * Imported from JSYN
     * @param maxValue
     */
    private void autoScale(double maxValue) {
        //TODO: only if Autoscale enable
        ExponentialRangeModel verticalScaleModel = this.scope.getModel().getProbes()[0].getVerticalScaleModel();
        double scaledMax = maxValue * 1.1;
        double current = verticalScaleModel.getDoubleValue();
        if (scaledMax > current) {
            verticalScaleModel.setDoubleValue(scaledMax);
        } else {
            double decayed = current * 0.95;
            if (decayed > verticalScaleModel.getMinimum()) {
                if (scaledMax < decayed) {
                    verticalScaleModel.setDoubleValue(decayed);
                }
            }
        }
    }

    @Override
    public boolean isActivated() {
        return start;
    }
}
