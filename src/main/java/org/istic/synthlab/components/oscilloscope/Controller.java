package org.istic.synthlab.components.oscilloscope;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.istic.synthlab.components.AbstractController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Thibaud Hulin <<thibaud>dot<hulin>dot<cl>at<gmail>dot<com>>
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */

public class Controller extends AbstractController {
    @FXML
    private LineChart chart;
    @FXML
    private NumberAxis timeAxis;
    @FXML
    private NumberAxis scaleAxis;

    private static final int DEFAULT_DATA_POINTS = 1000;
    private XYChart.Series<Number, Number> samples = new XYChart.Series<>();
    private int xSamplesData = 0;

    private Oscilloscope oscilloscope = new Oscilloscope("Oscilloscope");

    /**
     * When the component is created, it initialize the component representation adding listener and MouseEvent
     * @param location type URL
     * @param resources type ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        configure(oscilloscope);

        chart.getData().addAll(samples);

        scaleAxis.setUpperBound(1.0);
        scaleAxis.setLowerBound(-1.0);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                drawWave();
            }
        }.start();
    }

    private void drawWave() {
        double numSamples = oscilloscope.getWidth();
        //fill the data series
        for (int i = 0; i < numSamples; i++) {
            xSamplesData++;
            samples.getData().add(new XYChart.Data<>(xSamplesData, oscilloscope.getValue()));
        }
        // remove points to keep us at no more than MAX_DATA_POINTS
        if (samples.getData().size() > DEFAULT_DATA_POINTS) {
            samples.getData().remove(0, samples.getData().size() - DEFAULT_DATA_POINTS);
        }
        // update
        timeAxis.setLowerBound(xSamplesData - DEFAULT_DATA_POINTS);
        timeAxis.setUpperBound(xSamplesData - 1);
    }
}
