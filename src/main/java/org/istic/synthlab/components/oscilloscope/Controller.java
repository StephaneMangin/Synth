package org.istic.synthlab.components.oscilloscope;

<<<<<<< HEAD
import com.jsyn.scope.swing.AudioScopeView;
import javafx.animation.AnimationTimer;
=======
>>>>>>> 8497725a496336bc7041b5139af56285bcd34f56
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
<<<<<<< HEAD
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.istic.synthlab.ui.ConnectionManager;
=======
import javafx.scene.layout.Pane;
import org.istic.synthlab.components.AbstractController;
>>>>>>> 8497725a496336bc7041b5139af56285bcd34f56

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author Sebastien
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
<<<<<<< HEAD
public class Controller implements Initializable {

<<<<<<< HEAD
    private static final int MAX_DATA_POINTS = 500;
=======
    @FXML
    private AnchorPane oscilloscopePane;
    @FXML
    private GridPane pane;
    @FXML
    public Group swingNodeGroup;
>>>>>>> #GLOB_thibhul
    @FXML
    private Circle input;
    @FXML
    private Circle output;
    @FXML
    private Circle circleEvent;
    @FXML
    private LineChart chart;
    @FXML
    private NumberAxis timeAxis;

    private XYChart.Series<Number, Number> samples = new XYChart.Series<>();
    private int xSamplesData = 0;
=======
public class Controller extends AbstractController {
    @FXML
    private Pane pane;

    private Oscilloscope oscilloscope = new Oscilloscope("Oscilloscope");
>>>>>>> 8497725a496336bc7041b5139af56285bcd34f56

    /**
     * When the component is created, it initialize the component representation adding listener and MouseEvent
     * @param location type URL
     * @param resources type ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
<<<<<<< HEAD
        oscilloscopePane.setId("oscilloscopePane"+numInstance);
        input.addEventHandler(MouseEvent.MOUSE_CLICKED, new GetIdWithClick());
        output.addEventHandler(MouseEvent.MOUSE_CLICKED, new GetIdWithClick());



        chart.getData().addAll(samples);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                samples.getData().add(new XYChart.Data<>(xSamplesData, oscilloscope.getValue()));

                // remove points to keep us at no more than MAX_DATA_POINTS
                if (samples.getData().size() > MAX_DATA_POINTS) {
                    samples.getData().remove(0, samples.getData().size() - MAX_DATA_POINTS);
                }
                // update
                timeAxis.setLowerBound(xSamplesData - MAX_DATA_POINTS);
                timeAxis.setUpperBound(xSamplesData - 1);
            }
        }.start();
    }


    /**
     * Method called in view component file and start a connection manager calling the makeDestination method
     * with the output variable
     */
    @FXML
    public void connectOut(){
        ConnectionManager.makeOrigin(oscilloscope, circleEvent, oscilloscope.getOutput());
    }

    /**
     * Method called in view component file and start a connection manager calling the makeDestination method
     * with the input variable
     */
    @FXML
    public void connectIn(){
        ConnectionManager.makeDestination(oscilloscope, circleEvent, oscilloscope.getInput());
    }

    /**
     * Get the object clicked in the view and cast it into a Circle object
     */
    private class GetIdWithClick implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            circleEvent = (Circle)event.getSource();
        }
=======
        super.initialize(location, resources);
        configure(oscilloscope);

        final SwingNode swingNode = new SwingNode();
        swingNode.setContent(oscilloscope.getView());
        pane.getChildren().add(swingNode);
>>>>>>> 8497725a496336bc7041b5139af56285bcd34f56
    }

}
