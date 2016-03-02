package org.istic.synthlab.components.mixer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import org.istic.synthlab.components.AbstractController;
import org.istic.synthlab.ui.plugins.plug.InputPlug;
import org.istic.synthlab.ui.plugins.controls.Potentiometer;

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

    @FXML
    private InputPlug input1;
    @FXML
    private InputPlug input2;
    @FXML
    private InputPlug input3;
    @FXML
    private InputPlug input4;


    public Controller() {
        configure(mixer);
    }

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

        inputPotentiometer1.valueProperty().addListener((observable, oldValue, newValue) -> {
            mixer.setGainValueInput1((Double) newValue);
        });
        inputPotentiometer2.valueProperty().addListener((observable, oldValue, newValue) -> {
            mixer.setGainValueInput2((Double) newValue);
        });
        inputPotentiometer3.valueProperty().addListener((observable, oldValue, newValue) -> {
            mixer.setGainValueInput3((Double) newValue);
        });
        inputPotentiometer4.valueProperty().addListener((observable, oldValue, newValue) -> {
            mixer.setGainValueInput4((Double) newValue);
        });

        inputPotentiometer1.setTitle("Gain 1");
        inputPotentiometer2.setTitle("Gain 2");
        inputPotentiometer3.setTitle("Gain 3");
        inputPotentiometer4.setTitle("Gain 4");

        inputPotentiometer1.setMinValue(mixer.getMinValue());
        inputPotentiometer2.setMinValue(mixer.getMinValue());
        inputPotentiometer3.setMinValue(mixer.getMinValue());
        inputPotentiometer4.setMinValue(mixer.getMinValue());

        inputPotentiometer1.setMaxValue(mixer.getMaxValue());
        inputPotentiometer2.setMaxValue(mixer.getMaxValue());
        inputPotentiometer3.setMaxValue(mixer.getMaxValue());
        inputPotentiometer4.setMaxValue(mixer.getMaxValue());

        input1.setText("Input 1");
        input1.setInput(mixer.getInput1());
        input2.setText("Input 2");
        input2.setInput(mixer.getInput2());
        input3.setText("Input 3");
        input3.setInput(mixer.getInput3());
        input4.setText("Input 4");
        input4.setInput(mixer.getInput4());
    }

    @FXML
    public void connectInput1(final MouseEvent event) {
        manager.plugInput((InputPlug) event.getSource());
    }

    @FXML
    public void connectInput2(final MouseEvent event) {
        manager.plugInput((InputPlug) event.getSource());
    }

    @FXML
    public void connectInput3(final MouseEvent event) {
        manager.plugInput((InputPlug) event.getSource());
    }

    @FXML
    public void connectInput4(final MouseEvent event) {
        manager.plugInput((InputPlug) event.getSource());
    }

}
