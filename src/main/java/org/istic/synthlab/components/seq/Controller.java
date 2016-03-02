package org.istic.synthlab.components.seq;

import javafx.fxml.FXML;
import org.istic.synthlab.components.AbstractController;
import org.istic.synthlab.ui.plugins.controls.Potentiometer;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author  Dechaud John Marc johnmarcdechaud[at]gmail[dot]com on 2/25/16.
 * @author  Ngassam Noumi paola Jovany npaolita[at]yahoo[dot]fr on 2/25/16.
 */
public class Controller extends AbstractController {

        @FXML
        private Potentiometer step1;
        @FXML
        private Potentiometer step2;
        @FXML
        private Potentiometer step3;
        @FXML
        private Potentiometer step4;
        @FXML
        private Potentiometer step5;
        @FXML
        private Potentiometer step6;
        @FXML
        private Potentiometer step7;
        @FXML
        private Potentiometer step8;


        private Sequencer sequencer = new Sequencer("Sequencer");

        public Controller() {
            configure(sequencer);
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

            step1.valueProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("Step 1 changed from " + oldValue + " to " + newValue);
               // sequencer.getReleasePotentiometer().setValue((double) newValue);
            });
            step1.setTitle("Step 1");
            step1.setMinValue(-1);
            step1.setMaxValue(1);

            step2.valueProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("Step 2 changed from " + oldValue + " to " + newValue);
               // eg.getAttackPotentiometer().setValue((double) newValue);
            });
            step2.setTitle("Step 2");
            step2.setMinValue(-1);
            step2.setMaxValue(1);

            step3.valueProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("Step 3 changed from " + oldValue + " to " + newValue);
                // eg.getAttackPotentiometer().setValue((double) newValue);
            });
            step3.setTitle("Step 3");
            step3.setMinValue(-1);
            step3.setMaxValue(1);

            step4.valueProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("Step 4 changed from " + oldValue + " to " + newValue);
                // eg.getAttackPotentiometer().setValue((double) newValue);
            });
            step4.setTitle("Step 4");
            step4.setMinValue(-1);
            step4.setMaxValue(1);

            step5.valueProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("Step 5 changed from " + oldValue + " to " + newValue);
                // eg.getAttackPotentiometer().setValue((double) newValue);
            });
            step5.setTitle("Step 5");
            step5.setMinValue(-1);
            step5.setMaxValue(1);

            step6.valueProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("Step 6 changed from " + oldValue + " to " + newValue);
                // eg.getAttackPotentiometer().setValue((double) newValue);
            });
            step6.setTitle("Step 6");
            step6.setMinValue(-1);
            step6.setMaxValue(1);


            step7.valueProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("Step 7 changed from " + oldValue + " to " + newValue);
                // eg.getAttackPotentiometer().setValue((double) newValue);
            });
            step7.setTitle("Step 7");
            step7.setMinValue(-1);
            step7.setMaxValue(1);


            step8.valueProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("Step 8 changed from " + oldValue + " to " + newValue);
                // eg.getAttackPotentiometer().setValue((double) newValue);
            });
            step8.setTitle("Step 8");
            step8.setMinValue(-1);
            step8.setMaxValue(1);
        }

}
