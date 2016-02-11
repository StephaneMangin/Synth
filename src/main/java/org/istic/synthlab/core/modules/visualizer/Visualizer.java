package org.istic.synthlab.core.modules.visualizer;

import com.jsyn.scope.AudioScope;
import com.jsyn.scope.WaveTraceModel;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;

import javax.swing.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by stephane on 08/02/16.
 */
public class Visualizer implements IVisualizer {

    private AudioScope scope;
    private WaveTraceModel wave;
    private IComponent component;
    private ConcurrentLinkedQueue<Number> values = new ConcurrentLinkedQueue<>();

    public Visualizer(IComponent component) {
        this.component = component;
        // Pour l'affichage des courbes
        this.scope = new AudioScope(Factory.createSynthesizer());
        this.scope.setTriggerMode(AudioScope.TriggerMode.AUTO);
        this.scope.getModel().getTriggerModel().getLevelModel().setDoubleValue(0.01);
    }

    @Override
    public JPanel getView() {
        return this.scope.getView();
    }

    @Override
    public void linkTo(IOutput output) {
        // Connect the audioscope to the IOutput's UnitOutputPort
        this.scope.addProbe(Register.retrieve(output));
        wave = this.scope.getModel().getProbes()[0].getWaveTraceModel();
    }

    /**
     * Start the scope
     */
    @Override
    public void start() {
        this.scope.start();
    }

    /**
     * Stop the scope
     */
    @Override
    public void stop() {
        this.scope.stop();
    }


    public Number getValue() {
        if (wave != null) {
            int numSamples = wave.getVisibleSize();
            System.out.println("Samples "+numSamples);
            if (numSamples > 0) {
                int offset = wave.getStartIndex();
                // Draw lines to remaining points.
                for (int i = 0; i < numSamples; i++) {
                    values.add(wave.getSample(offset + i));
                }

                return values.remove();
            }
        }

        return 0;
    }
}
