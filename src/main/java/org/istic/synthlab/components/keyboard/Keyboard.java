package org.istic.synthlab.components.keyboard;

import org.istic.synthlab.components.AbstractComponent;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.modules.keyboard.IKeyboard;
import org.istic.synthlab.core.modules.keyboard.Note;
import org.istic.synthlab.core.services.Factory;

public class Keyboard extends AbstractComponent {

    private IKeyboard keyboard;

    /**
     * Instantiates a new component.
     *
     * @param name the name
     */
    public Keyboard(String name) {
        super(name);
        this.keyboard = Factory.createKeyboard(this);
        this.keyboard.getOutputGate().connect(getSinkGate());
        this.keyboard.getOutputCV().connect(getSink());
    }

    public void playNote(Note note) {
        keyboard.noteOn(note);
    }

    public void releaseNote() {
        keyboard.noteOff();
    }

    public IOutput getOutputGate(){
        return this.keyboard.getOutputGate();
    }

    public IOutput getOutputCv(){
        return this.keyboard.getOutputCV();
    }

    @Override
    public void activate() {
        this.keyboard.activate();
    }

    @Override
    public void deactivate() {
        this.keyboard.deactivate();
    }

    @Override
    public boolean isActivated() {
        return this.keyboard.isActivated();
    }

}