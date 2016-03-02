package org.istic.synthlab.ui.plugins.plug;

import org.istic.synthlab.ui.plugins.workspace.ComponentPane;
import org.istic.synthlab.ui.plugins.workspace.CurveCable;

/**
 * Created by blacknight on 01/03/16.
 */
public interface Plug {

    /**
     * Define the name of this plug
     *
     * @param value
     */
    void setText(String value);

    /**
     * Get the name of this plug
     *
     * @return
     */
    String getText();

    /**
     * Get the plug connected to this plug
     *
     * @return
     */
    CurveCable getCable();

    /**
     * Set the plug to be connected to this plug
     *
     * @param cable
     */
    void setCable(CurveCable cable);

    /**
     * Returns true if a plug is connected
     *
     * @return
     */
    boolean hasCable();

    /**
     * Get the parent compoennt
     *
     * @return
     */
    ComponentPane getComponentPane();
}
