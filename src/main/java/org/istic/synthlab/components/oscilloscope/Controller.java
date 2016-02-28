package org.istic.synthlab.components.oscilloscope;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.istic.synthlab.components.AbstractController;
import org.istic.synthlab.ui.plugins.controls.Potentiometer;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Thibaud Hulin <<thibaud>dot<hulin>dot<cl>at<gmail>dot<com>>
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */

public class Controller extends AbstractController {
    @FXML
    private LineChart<Number, Number> chart;
    @FXML
    private NumberAxis timeAxis;
    @FXML
    private NumberAxis scaleAxis;
    @FXML
    private Potentiometer xScale;
    @FXML
    private Potentiometer yScale;

    private XYChart.Series<Number, Number> samples = new XYChart.Series<>();
    private int xSamplesData = 0;
    private int runningTime = 0;

    private Oscilloscope oscilloscope = new Oscilloscope("Oscilloscope");

    public Controller() {
        configure(oscilloscope);
    }
    /**
     * When the component is created, it initialize the component representation adding listener and MouseEvent
     * @param location type URL
     * @param resources type ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        yScale.setTitle("Y Scale");
        yScale.setMinValue(0.1);
        yScale.setMaxValue(10);

        chart.getData().add(samples);

        scaleAxis.setUpperBound(1.0);
        scaleAxis.setLowerBound(-1.0);

        yScale.valueProperty().addListener((observable, oldValue, newValue) -> {
            scaleAxis.setUpperBound(10*(double)newValue);
            scaleAxis.setLowerBound(-10*(double)newValue);
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                //Each 7 frames of JavaFX we update
                if (runningTime % 7 == 0) {
                    drawWave();
                }
                runningTime++;
            }
        }.start();
    }

    private void drawWave() {
        int max_data_points = (int) oscilloscope.getWidth();
        //fill the data series
        for (int i = 0; i < max_data_points; i++) {
            xSamplesData++;
            samples.getData().add(new XYChart.Data<>(xSamplesData, oscilloscope.getValue()));
        }
        // remove points to keep us at no more than max_data_points
        if (samples.getData().size() > max_data_points) {
            samples.getData().remove(0, samples.getData().size() - max_data_points);
        }
        // update
        timeAxis.setLowerBound(xSamplesData - max_data_points + 1);
        timeAxis.setUpperBound(xSamplesData - 1);
    }
}
