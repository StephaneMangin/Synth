package org.istic.synthlab.core.modules.visualizer;

import com.jsyn.scope.AudioScope;
<<<<<<< HEAD
import com.jsyn.scope.WaveTraceModel;
import org.istic.synthlab.core.IComponent;
=======
import org.istic.synthlab.components.IComponent;
>>>>>>> 8497725a496336bc7041b5139af56285bcd34f56
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
<<<<<<< HEAD
    private IComponent component;
    private ConcurrentLinkedQueue<Number> values = new ConcurrentLinkedQueue<>();
=======
    private boolean start;
>>>>>>> 8497725a496336bc7041b5139af56285bcd34f56

    public Visualizer(IComponent component) {
        // Pour l'affichage des courbes
<<<<<<< HEAD
        this.scope = new AudioScope(Factory.createSynthesizer());
        this.scope.setTriggerMode(AudioScope.TriggerMode.AUTO);
        this.scope.getModel().getTriggerModel().getLevelModel().setDoubleValue(0.01);
=======
        scope = new AudioScope( Factory.createSynthesizer());
        scope.setTriggerMode( AudioScope.TriggerMode.AUTO );
        scope.getModel().getTriggerModel().getLevelModel().setDoubleValue( 0.01 );
>>>>>>> 8497725a496336bc7041b5139af56285bcd34f56
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

<<<<<<< HEAD

    public Number getValue() {
        WaveTraceModel wave = this.scope.getModel().getProbes()[0].getWaveTraceModel();
        if (Factory.createSynthesizer().isRunning()) {
            System.out.println("Size "+this.scope.getModel().getProbes()[0].getWaveTraceModel().getSize());
        }
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
=======
    @Override
    public boolean isActivated() {
        return start;
    }


>>>>>>> 8497725a496336bc7041b5139af56285bcd34f56
}
