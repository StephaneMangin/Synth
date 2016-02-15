package org.istic.synthlab.core.modules.visualizer;

import com.jsyn.scope.AudioScope;
import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;

import javax.swing.*;

/**
 * Created by stephane on 08/02/16.
 */
public class Visualizer implements IVisualizer {

    private AudioScope scope;
    private boolean start;

    public Visualizer(IComponent component) {
        // Pour l'affichage des courbes
        scope = new AudioScope( Factory.createSynthesizer());
        scope.setTriggerMode( AudioScope.TriggerMode.AUTO );
        scope.getModel().getTriggerModel().getLevelModel().setDoubleValue( 0.01 );
    }

    @Override
    public JPanel getView() {
        return this.scope.getView();
    }

    @Override
    public void linkTo(IOutput output) {
        // Connect the audioscope to the IOutput's UnitOutputPort
        this.scope.addProbe(Register.retrieve(output));
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

    @Override
    public boolean isActivated() {
        return start;
    }


}
