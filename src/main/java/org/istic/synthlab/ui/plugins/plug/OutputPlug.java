package org.istic.synthlab.ui.plugins.plug;

import org.istic.synthlab.core.modules.io.IOutput;

/**
 * Allow direct insertion of a output plug inside a node
 *
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class OutputPlug extends AbstractPlug {

    private IOutput output;

    public OutputPlug() {
        super("output");
//        setOnMousePressed(event -> {
//            if (this.hasCable()) {
//                cable.disconnectOutputPlug();
//                event.consume();
//            }
//        });
    }

    public IOutput getOutput() {
        return output;
    }

    public void setOutput(IOutput output) {
        this.output = output;
    }

}
