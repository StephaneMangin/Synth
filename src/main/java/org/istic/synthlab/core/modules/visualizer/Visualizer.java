package org.istic.synthlab.core.modules.visualizer;

import com.jsyn.scope.AudioScope;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;

import javax.swing.*;

/**
 * Created by stephane on 08/02/16.
 */
public class Visualizer implements IVisualizer {

    private AudioScope audioScope;

    public Visualizer(IComponent component) {
        audioScope = new AudioScope(Factory.createSynthesizer());
        audioScope.setViewMode(AudioScope.ViewMode.WAVEFORM);
        audioScope.setTriggerLevel(0.01);
        audioScope.setTriggerMode(AudioScope.TriggerMode.NORMAL);

    }

    @Override
    public JPanel getView() {
        return audioScope.getView();
    }

    @Override
    public void linkTo(IOutput output) {
        // Connect the audioscope to the IOutput's UnitOutputPort
        audioScope.addProbe(Register.retrieve(output));
    }
}
