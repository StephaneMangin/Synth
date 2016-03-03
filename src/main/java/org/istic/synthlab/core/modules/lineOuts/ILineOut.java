package org.istic.synthlab.core.modules.lineOuts;


import org.istic.synthlab.core.Resource;
import org.istic.synthlab.core.modules.IModule;
import org.istic.synthlab.core.modules.io.IInput;

import java.io.File;

/**
 * This interface represents our LineOut
 *
 */
public interface ILineOut extends IModule, Resource {

    /**
     * Launch the line pulling
     */
    void start();

    /**
     * Stop the line pulling
     *
     */
    void stop();

    /**
     * Launch the recording
     */
    void startRecord();

    /**
     * Stop the recording
     */
    void stopRecord();

    /**
     * Set the file where the record will be written
     *
     * @param file a file
     */
    void setFileToWrite(File file);

    /**
     * Get the file currently used to store the record
     */
    File getFileToWrite();

    /**
     * Returns the input of this line
     *
     * @return IInput
     */
    IInput getInput();

}
