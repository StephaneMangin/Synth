package org.istic.synthlab.ui.plugins.plug;

import org.istic.synthlab.core.modules.io.IInput;

/**
 * Allow direct insertion of a input plug inside a node
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class InputPlug extends AbstractPlug {

    private IInput input;

    public InputPlug() {
        super("input");
//        setOnMousePressed(event -> {
//            if (hasCable()) {
//                cable.disconnectInputPlug();
//                event.consume();
//            }
//        });
    }

    public IInput getInput() {
        return input;
    }

    public void setInput(IInput input) {
        this.input = input;
    }

}
