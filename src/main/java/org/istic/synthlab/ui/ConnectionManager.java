package org.istic.synthlab.ui;


import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.ui.history.History;
import org.istic.synthlab.ui.history.HistoryImpl;
import org.istic.synthlab.ui.history.StateType;
import org.istic.synthlab.ui.plugins.plug.InputPlug;
import org.istic.synthlab.ui.plugins.plug.OutputPlug;
import org.istic.synthlab.ui.plugins.workspace.ComponentPane;
import org.istic.synthlab.ui.plugins.workspace.CurveCable;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class manages the creation of cables
 * @author Sebastien
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 * @author Thibaut Rousseau <thibaut.rousseau@outlook.com>
 */
public class ConnectionManager {
    private History history = new HistoryImpl();
    private CoreController coreController;
    public void setCoreController(CoreController coreController) {
        this.coreController = coreController;
    }
    private CurveCable currentCable;

    private void finished() {
        Register.connect(
            currentCable.getInputPlug().getInput(),
            currentCable.getOutputPlug().getOutput()
        );

        // Save to history
        history.add(currentCable, StateType.CHANGED);
        // Then reset the current cable to allow a next one
        currentCable = null;
    }

    /**
     * This method is used to connect a cable on an output
     *
     * @param plug The graphical node representing the output
     */
    public void plugOutput(final OutputPlug plug) {
        if (currentCable == null) {
            if (plug.hasCable()) {
                currentCable = plug.getCable();
            } else {
                currentCable = new CurveCable();
                CoreController.getWorkspace().getChildren().add(currentCable);
                history.add(currentCable, StateType.CREATED);
            }
        }
        if (!plug.hasCable()) {
            // Connect the node and the cable
            currentCable.connectOutputPlug(plug);
        }

        // Use of the curvecable internal state to know if connection is done
        if (currentCable.isPlugged()) {
            finished();
        } else {
            // When clicked on the workspace while creating a cable, delet the cable
            CoreController.getWorkspace().setOnMouseClicked(event -> {
                if (currentCable != null) {
                    deleteCable(currentCable);
                }
            });
        }
    }

    /**
     * This method is used to connect a cable on an input
     *
     * @param plug The graphical node representing the input
     */
    public void plugInput(final InputPlug plug) {
        if (currentCable == null) {
            if (plug.hasCable()) {
                currentCable = plug.getCable();
            } else {
                currentCable = new CurveCable();
                CoreController.getWorkspace().getChildren().add(currentCable);
                history.add(currentCable, StateType.CREATED);
            }
        }
        if (!plug.hasCable()) {
            // Connect the node and the cable
            currentCable.connectInputPlug(plug);
        }

        // Use of the curvecable internal state to know if connection is done
        if (currentCable.isPlugged()) {
            finished();
        } else {
            // When clicked on the workspace while creating a cable, delet the cable
            CoreController.getWorkspace().setOnMouseClicked(event -> {
                if (currentCable != null) {
                    deleteCable(currentCable);
                }
            });
        }
    }

    /**
     * Properly delete a cable
     *
     * @param cable
     */
    public void deleteCable(CurveCable cable) {
        if (cable != null) {
            // Remove the cable
            removeCable(cable);
            // And reset current cable
            currentCable = null;
            if (cable.isPlugged()) {
                // Save to history
                history.add(cable, StateType.DELETED);
            }
        }
    }

    /**
     * Delete a cable and end the model connection
     *
     * @param cable The cable to delete
     */
    private void removeCable(final CurveCable cable) {
        CoreController.getWorkspace().getChildren().remove(cable);
        if (cable.isPlugged() || cable.isBeingPlugged()) {
            if (cable.getInputPlug() != null) {
                Register.disconnect(cable.getInputPlug().getInput());
                cable.deconnectInputPlug();
            }
            if (cable.getOutputPlug() != null) {
                Register.disconnect(cable.getOutputPlug().getOutput());
                cable.deconnectOutputPlug();
            }
        }
    }

    /**
     * Properly delete a component and all its connections
     *
     * @param componentPane The view of the component to delete
     */
    public void deleteComponent(final ComponentPane componentPane) {
        componentPane.getInputPlugs().forEach(inputPlug -> deleteCable(inputPlug.getCable()));
        componentPane.getOutputPlugs().forEach(outputPlug -> deleteCable(outputPlug.getCable()));

        CoreController.getWorkspace().getChildren().remove(componentPane);
        // Then save to history
        history.add(componentPane, StateType.DELETED);
    }

    /**
     * Return the history object
     *
     * @return
     */
    public History getHistory() {
        return history;
    }

}
