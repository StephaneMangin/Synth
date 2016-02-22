package org.istic.synthlab.components.out;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.istic.synthlab.components.AbstractController;
import org.istic.synthlab.ui.ConnectionManager;
import org.istic.synthlab.ui.controls.Potentiometer;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class Controller extends AbstractController {
    @FXML
    private Potentiometer amplitude;

    private Out componentOut = new Out("Line Out");

    /**
     * When the component is created, it initialize the component representation adding listener and MouseEvent
     * @param location type URL
     * @param resources type ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        configure(componentOut);

        amplitude.valueProperty().addListener((observable, oldValue, newValue) -> {
            componentOut.getAmModulator().setValue(newValue.doubleValue());
        });
        amplitude.setTitle("Amplitude");
        amplitude.setMinValue(componentOut.getAmModulator().getMin());
        amplitude.setMaxValue(componentOut.getAmModulator().getMax());
    }

    @FXML
    public void toggleMute() {
        if (componentOut.isActivated()) {
            componentOut.deactivate();
        }
        else {
            componentOut.activate();
        }
    }

    @FXML
    public void toggleRecord(final Event event) {
        final ToggleButton recordButton = (ToggleButton) event.getSource();
        if (recordButton.isSelected()) {
            final FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName("record.wav");
            fileChooser.setInitialDirectory(new File(System.getProperty("java.io.tmpdir")));
            fileChooser.setTitle("Save File");
            final File fileToWrite = fileChooser.showSaveDialog(ConnectionManager.getStage());

            // If the user closes the FileChooser, cancel
            if (fileToWrite == null) {
                recordButton.setSelected(false);
                return;
            }

            componentOut.getLineOut().setFileToWrite(fileToWrite);
            componentOut.getLineOut().startRecord();
        }
        else {
            componentOut.getLineOut().stopRecord();
        }
    }
}
