package org.istic.synthlab.components.oscilloscope;

import com.jsyn.Synthesizer;
import com.jsyn.engine.SynthesisEngine;
import com.jsyn.scope.AudioScope;
import com.jsyn.scope.swing.AudioScopeView;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
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
 * Created by seb on 04/02/16.
 */
public class Controller implements Initializable{

    @FXML
    private GridPane pane;
    @FXML
    public Group swingNodeGroup;
    @FXML
    private Circle input;
    @FXML
    private Circle output;
    @FXML
    private Circle circleEvent;

    private static int numInstance = 0;
    private Oscilloscope oscilloscope = new Oscilloscope("Visualizer"+numInstance++);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        input.addEventHandler(MouseEvent.MOUSE_CLICKED, new GetIdWithClick());
        output.addEventHandler(MouseEvent.MOUSE_CLICKED, new GetIdWithClick());

        AudioScopeView byuu = gtreff();
        byuu.setSize(new Dimension(111, 111));

        final SwingNode swingNode = new SwingNode();
        JPanel panel = new JPanel();
        panel.setBackground(Color.RED);

        swingNode.setContent(byuu);
        pane.add(swingNode, 0, 0);
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

    @FXML
    public void connectOut(){
        ConnectionManager.makeOrigin(circleEvent, oscilloscope.getOutput());
    }

    @FXML
    public void connectIn(){
        ConnectionManager.makeDestination(circleEvent, oscilloscope.getInput());
    }

    private class GetIdWithClick implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            circleEvent = (Circle)event.getSource();
        }
    }


}
