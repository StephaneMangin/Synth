package org.istic.synthlab.core.modules.oscilloscope;

import com.jsyn.engine.SynthesisEngine;
import com.jsyn.scope.AudioScope;
import com.jsyn.scope.swing.AudioScopeView;
import org.istic.synthlab.core.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.lineOuts.ILineOut;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;

import javax.swing.*;

/**
 * Created by stephane on 08/02/16.
 */
public class Oscilloscope implements IOscilloscope {

    private AudioScope audioScope;

    public Oscilloscope(IComponent component, IOutput output) {
        audioScope = new AudioScope(Factory.createSynthesizer());
        audioScope.addProbe(Register.retrieve(output));
    }

    @Override
    public JPanel getView() {
        return audioScope.getView();
    }
}
