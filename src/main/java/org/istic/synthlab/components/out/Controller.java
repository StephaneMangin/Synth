package org.istic.synthlab.components.out;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import org.istic.synthlab.components.AbstractController;
import org.istic.synthlab.ui.ConnectionManager;
import org.istic.synthlab.ui.controls.Potentiometer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class Controller extends AbstractController {
    @FXML
    private Potentiometer amplitude;

    @FXML
    private ToggleButton muteButton;

    @FXML
    private ToggleButton recordButton;

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

        recordButton.setOnMouseClicked(new RecordEventHandler());
        muteButton.setOnMouseClicked(new MuteEventHandler());
    }

    /**
     * Start / Stop recording
     */
    private class RecordEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(final MouseEvent event) {
            final ToggleButton button = (ToggleButton) event.getSource();
            if (button.isSelected()) {
                final FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialFileName("record.wav");
                fileChooser.setInitialDirectory(new File(System.getProperty("java.io.tmpdir")));
                fileChooser.setTitle("Save File");
                final File fileToWrite = fileChooser.showSaveDialog(ConnectionManager.getStage());
                componentOut.getLineOut().setFileToWrite(fileToWrite);
                componentOut.getLineOut().startRecord();
                button.setSelected(true);
                button.setText("Stop");
                button.setTextFill(Color.RED);
            } else {
                componentOut.getLineOut().stopRecord();
                button.setSelected(false);
                button.setText("Record");
            }
            event.consume();
        }
    }

    /**
     * Start / Stop recording
     */
    private class MuteEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(final MouseEvent event) {
            final ToggleButton button = (ToggleButton) event.getSource();
            if (button.isSelected()) {
                componentOut.deactivate();
                button.setSelected(true);
                button.setText("Un-Mute");
            } else {
                componentOut.activate();
                button.setSelected(false);
                button.setText("Mute");
            }
            event.consume();
        }
    }
}
