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
            // When clicked on the workspace while creating a cable, delete the cable
            CoreController.getWorkspace().setOnMouseClicked(event -> {
                deleteCable(currentCable);
            });
        }
    }

    /**
     * Properly delete a cable
     *
     * @param cable
     */
    public void deleteCable(CurveCable cable) {
        if (cable != null ) {
            if (cable.isPlugged()) {
                Register.disconnect(cable.getInput().getInput());
            }

            if (cable.getPlugState() == CurveCable.PlugState.IN_PLUGGED) {
                cable.disconnectInputPlug();
            }
            if (cable.getPlugState() == CurveCable.PlugState.OUT_PLUGGED) {
                cable.disconnectOutputPlug();
            }

            CoreController.getWorkspace().getChildren().remove(cable);
            currentCable = null;
            // Then save to history
            history.add(cable, StateType.DELETED);
        }
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
