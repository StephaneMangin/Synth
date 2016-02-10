package org.istic.synthlab.components.oscilloscope;

import com.jsyn.Synthesizer;
import com.jsyn.engine.SynthesisEngine;
import com.jsyn.scope.AudioScope;
import com.jsyn.scope.swing.AudioScopeView;
import javafx.embed.swing.SwingNode;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import org.istic.synthlab.components.out.Out;
import org.istic.synthlab.components.vcoa.Vcoa;
import org.istic.synthlab.core.modules.oscillators.OscillatorType;
import org.istic.synthlab.core.modules.oscillators.SawtoothOscillator;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.ui.ConnectionManager;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Sebastien
 */
public class Controller implements Initializable{

    @FXML
    private Pane pane;
    @FXML
    public Group swingNodeGroup;
    @FXML
    private Circle input;
    @FXML
    private Circle output;
    @FXML
    private Circle circleEvent;

    private static int numInstance      = 0;
    private Oscilloscope oscilloscope   = new Oscilloscope("Visualizer"+numInstance++);
    /**
     * When the component is created, it initialize the component representation adding listener and MouseEvent
     * @param location type URL
     * @param resources type ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        input.addEventHandler(MouseEvent.MOUSE_CLICKED, new GetIdWithClick());
        output.addEventHandler(MouseEvent.MOUSE_CLICKED, new GetIdWithClick());

        final SwingNode swingNode = new SwingNode();
        JPanel panel = new JPanel();
        panel.setBackground(Color.RED);
        panel.setSize(new Dimension(300, 300));

        swingNode.setContent(panel);
        pane.getChildren().add(swingNode);
    }

    private AudioScopeView gtreff() {
        Vcoa vcoa;
        Out out;
        Synthesizer synth;

        vcoa = new Vcoa("VCOA");
        vcoa.activate();
        out = new Out("OUT");
        out.activate();
        vcoa.setAmplitudeSquare(1);
        vcoa.setExponentialFrequency(200);
        vcoa.setAmplitudeSine(10000);
        //vcoa.setAmplitudeSquare(10000);
        synth = Factory.createSynthesizer();
        out.getInput().connect(vcoa.getOutput());

        Vcoa vcoa1 = new Vcoa("VCOA1");
        vcoa1.setAmplitudeSine(50);
        vcoa1.getOutput().connect(vcoa.getInput());
        SawtoothOscillator s = (SawtoothOscillator) Factory.createOscillator(vcoa, OscillatorType.SAWTOOTH);
        vcoa.getSawToothOutput().connect(out.getInput());

        // Pour l'affichage des courbes
        AudioScope scope = new AudioScope( synth );
        scope.addProbe(vcoa.getOutput().getUnitOutputPort());
        scope.setTriggerMode( AudioScope.TriggerMode.AUTO );
        scope.getModel().getTriggerModel().getLevelModel().setDoubleValue( 0.0001 );
        //scope.getView().setShowControls( true );
        scope.start();

        out.start();
        synth.start();
        ((SynthesisEngine)synth).printConnections();

        return scope.getView();
    }

    /**
     * Method called in view component file and start a connection manager calling the makeDestination method
     * with the output variable
     */
    @FXML
    public void connectOut(){
        ConnectionManager.makeOrigin(circleEvent, oscilloscope.getOutput());
    }
    /**
     * Method called in view component file and start a connection manager calling the makeDestination method
     * with the input variable
     */
    @FXML
    public void connectIn(){
        ConnectionManager.makeDestination(circleEvent, oscilloscope.getInput());
    }
    /**
     * Get the object clicked in the view and cast it into a Circle object
     */
    private class GetIdWithClick implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            circleEvent = (Circle)event.getSource();
        }
    }


}
