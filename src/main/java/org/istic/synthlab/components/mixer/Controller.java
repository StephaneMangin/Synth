package org.istic.synthlab.components.mixer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import org.istic.synthlab.components.AbstractController;
import org.istic.synthlab.ui.ConnectionManager;
import org.istic.synthlab.ui.controls.Potentiometer;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author  Dechaud John Marc johnmarcdechaud[at]gmail[dot]com on 2/13/16.
 */
public class Controller extends AbstractController implements Initializable{

    private Mixer mixer = new Mixer("Mixer");

    @FXML
    private Potentiometer inputPotentiometer1;
    @FXML
    private Potentiometer inputPotentiometer2;
    @FXML
    private Potentiometer inputPotentiometer3;
    @FXML
    private Potentiometer inputPotentiometer4;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        configure(mixer);

        this.inputPotentiometer1.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.mixer.setGainValueInput1((Double) newValue);
        });
        this.inputPotentiometer2.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.mixer.setGainValueInput2((Double) newValue);
        });
        this.inputPotentiometer3.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.mixer.setGainValueInput3((Double) newValue);
        });
        this.inputPotentiometer4.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.mixer.setGainValueInput4((Double) newValue);
        });

        this.inputPotentiometer1.setTitle("Gain 1");
        this.inputPotentiometer2.setTitle("Gain 2");
        this.inputPotentiometer3.setTitle("Gain 3");
        this.inputPotentiometer4.setTitle("Gain 4");

        this.inputPotentiometer1.setMinValue(mixer.getMinValue());
        this.inputPotentiometer2.setMinValue(mixer.getMinValue());
        this.inputPotentiometer3.setMinValue(mixer.getMinValue());
        this.inputPotentiometer4.setMinValue(mixer.getMinValue());

        this.inputPotentiometer1.setMaxValue(mixer.getMaxValue());
        this.inputPotentiometer2.setMaxValue(mixer.getMaxValue());
        this.inputPotentiometer3.setMaxValue(mixer.getMaxValue());
        this.inputPotentiometer4.setMaxValue(mixer.getMaxValue());
    }

    @FXML
    public void connectOutput(final MouseEvent event) {
        ConnectionManager.makeOrigin(this.mixer, (Node) event.getSource(), this.mixer.getOutput());
    }

    @FXML
    public void connectInput1(final MouseEvent event) {
        ConnectionManager.makeDestination(this.mixer, (Node) event.getSource(), this.mixer.getInput1());
    }

    @FXML
    public void connectInput2(final MouseEvent event) {
        ConnectionManager.makeDestination(this.mixer, (Node) event.getSource(), this.mixer.getInput2());
    }

    @FXML
    public void connectInput3(final MouseEvent event) {
        ConnectionManager.makeDestination(this.mixer, (Node) event.getSource(), this.mixer.getInput3());
    }

    @FXML
    public void connectInput4(final MouseEvent event) {
        ConnectionManager.makeDestination(this.mixer, (Node) event.getSource(), this.mixer.getInput4());
    }

}
