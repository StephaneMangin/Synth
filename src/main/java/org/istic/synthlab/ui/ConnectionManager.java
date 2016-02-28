package org.istic.synthlab.ui;


import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.ui.plugins.ComponentPane;
import org.istic.synthlab.ui.plugins.cable.CurveCable;
import org.istic.synthlab.ui.plugins.cable.InputPlug;
import org.istic.synthlab.ui.plugins.cable.OutputPlug;
import org.istic.synthlab.ui.plugins.history.HistoryImpl;
import org.istic.synthlab.ui.plugins.history.History;
import org.istic.synthlab.ui.plugins.history.StateType;

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

    private HashMap<ComponentPane, Set<CurveCable>> plugComponentMapping = new HashMap<>();


    private void finished() {
        Register.connect(
            currentCable.getInput().getInput(),
            currentCable.getOutput().getOutput()
        );

        // Add the cable to the mapping only if not yet present
        // because the linking has been done byth cable itself
        ComponentPane componentPane = currentCable.getOutput().getComponentPane();
        if (!plugComponentMapping.containsKey(componentPane)) {
            plugComponentMapping.put(componentPane, new TreeSet<>());
        }
        Set<CurveCable> nodes = plugComponentMapping.get(componentPane);
        if (!nodes.contains(currentCable)) {
            nodes.add(currentCable);
        }

        // Reset the current cable to allow a next one
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
        // Connect the node and the cable
        currentCable.connectOutputPlug(plug);

        // Then save to history
        history.add(currentCable, StateType.CHANGED);
        // Use of the curvecable internal state to know if connection is done
        if (currentCable.isPlugged()) {
            finished();
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
        // Connect the node and the cable
        currentCable.connectInputPlug(plug);

        // Then save to history
        history.add(currentCable, StateType.CHANGED);
        // Use of the curvecable internal state to know if connection is done
        if (currentCable.isPlugged()) {
            finished();
        }
    }

    /**
     * Properly delete a cable
     *
     * @param cable
     */
    public void deleteCable(CurveCable cable) {
        if (cable != null) {
            removeCable(cable);
            currentCable = null;
            // Then save to history
            history.add(cable, StateType.DELETED);
        }
    }

    /**
     * Delete a cable and end the model connection
     *
     * @param cable The cable to delete
     */
    private void removeCable(final CurveCable cable) {
        Register.disconnect(cable.getInput().getInput());
        cable.deconnectInputPlug();
        cable.deconnectOutputPlug();
        CoreController.getWorkspace().getChildren().remove(cable);
    }

    /**
     * Properly delete a component and all its connections
     *
     * @param componentPane The view of the component to delete
     */
    public void deleteComponent(final ComponentPane componentPane) {
        final Set<CurveCable> toDelete = plugComponentMapping.get(componentPane);
        if (toDelete != null) {
            toDelete.forEach(this::deleteCable);
        }
        CoreController.getWorkspace().getChildren().remove(componentPane);
        // Then save to history
        history.add(componentPane, StateType.DELETED);
    }

    public History getHistory() {
        return history;
    }

}
