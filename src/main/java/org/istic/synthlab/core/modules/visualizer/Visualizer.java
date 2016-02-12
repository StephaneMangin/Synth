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
    private IComponent component;

    public Visualizer(IComponent component) {
        this.component = component;
        // Pour l'affichage des courbes
        this.scope = new AudioScope( Factory.createSynthesizer());
        this.scope.setTriggerMode( AudioScope.TriggerMode.AUTO );
        this.scope.getModel().getTriggerModel().getLevelModel().setDoubleValue( 0.01 );
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
}
