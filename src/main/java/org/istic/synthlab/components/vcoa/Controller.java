package org.istic.synthlab.components.vcoa;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import org.istic.synthlab.core.AbstractController;
import org.istic.synthlab.ui.ConnectionManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author stephane
 */
public class Controller extends AbstractController implements Initializable {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Circle output;
    @FXML
    private Circle input;
    @FXML
    private Circle circleEvent;

    private static int numInstance = 0;

    private Vcoa vcoa = new Vcoa("VCOA" + numInstance++);

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        output.addEventHandler(MouseEvent.MOUSE_CLICKED, new GetIdWithClick());
    }

    @FXML
    public void connectOut() {
        ConnectionManager.makeOrigin(circleEvent, vcoa.getOutput());
    }

    private class GetIdWithClick implements EventHandler<Event> {
        @Override
        public void handle(Event event) {
            circleEvent = (Circle) event.getSource();
            System.out.println("Vcoa BoundsInLocal: "+circleEvent.localToScene(circleEvent.getCenterX(), circleEvent.getCenterY()));
            System.out.println("Vcoa BoundsInParent: "+circleEvent.localToScene(circleEvent.getCenterX(), circleEvent.getCenterY()));
            System.out.println("Vcoa BoundsInLocal: "+circleEvent.localToScreen(circleEvent.getCenterX(), circleEvent.getCenterY()));
            System.out.println("Vcoa BoundsInParent: "+circleEvent.localToScreen(circleEvent.getCenterX(), circleEvent.getCenterY()));
        }
    }

}
