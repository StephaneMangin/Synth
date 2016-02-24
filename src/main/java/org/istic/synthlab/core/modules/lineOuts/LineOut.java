package org.istic.synthlab.core.modules.lineOuts;

import org.istic.synthlab.components.IComponent;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

/**
 * The class Line adapter.
 *
 * The class Line adapter that implements the interface ILineOut
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class LineOut implements ILineOut {

    private com.jsyn.unitgen.LineOut lineOut;
    private com.jsyn.unitgen.PassThrough passThrough;
    private com.jsyn.util.WaveRecorder waveRecorder;
    private IInput input;

    private Date date = new Date();
    private String path = "/tmp/SynthLab_" + date.toString() + ".wav";
    private File file;

    /**
     * Instantiates a new Line adapter.
     * @param component
     */
    public LineOut(IComponent component) {
        lineOut = new com.jsyn.unitgen.LineOut();
        passThrough = new com.jsyn.unitgen.PassThrough();

        try {
            file = new File(path);
            waveRecorder = new com.jsyn.util.WaveRecorder(Factory.createSynthesizer(), file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        input = Factory.createInput("In", component, passThrough.input);
        // First declare the mappings
        Register.declare(component, lineOut);
        Register.declare(component, passThrough);
        Register.declare(component, input, passThrough.getInput());

        passThrough.output.connect(0, lineOut.input, 0);
        passThrough.output.connect(0, lineOut.input, 1);
        passThrough.output.connect(waveRecorder.getInput());
    }

    /**
     * Start the filter and the lineOut
     */
    public void start() {
        lineOut.start();
        Factory.createSynthesizer().start();
    }

    /**
     * Stop the filter and the lineOut
     */
    public void stop() {
        lineOut.stop();
        waveRecorder.stop();
        Factory.createSynthesizer().stop();
    }

    @Override
    public void startRecord() {
        waveRecorder.start();
    }

    @Override
    public void stopRecord(){
        waveRecorder.stop();
    }

    @Override
    public void setFileToWrite(File file) {
        try {
            waveRecorder = new com.jsyn.util.WaveRecorder(Factory.createSynthesizer(), file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        passThrough.output.connect(waveRecorder.getInput());
    }

    @Override
    public File getFileToWrite(){
        return file;
    }

    @Override
    public IInput getInput() {
        return input;
    }

    @Override
    public void activate() {
        passThrough.setEnabled(true);
        lineOut.setEnabled(true);
    }

    @Override
    public void deactivate() {
        passThrough.setEnabled(false);
        lineOut.setEnabled(false);
    }

    @Override
    public boolean isActivated() {
        return this.lineOut.isEnabled() && this.passThrough.isEnabled();
    }
}
