package org.istic.synthlab.components.keyboard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.istic.synthlab.components.AbstractController;
import org.istic.synthlab.ui.plugins.plug.OutputPlug;


import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends AbstractController implements Initializable {
    public OutputPlug gate;
    @FXML
    private Button buttonQ;
    @FXML
    private Button buttonS;
    @FXML
    private Button buttonD;
    @FXML
    private Button buttonF;
    @FXML
    private Button buttonG;
    @FXML
    private Button buttonH;
    @FXML
    private Button buttonJ;
    @FXML
    private Button buttonZ;
    @FXML
    private Button buttonE;
    @FXML
    private Button buttonT;
    @FXML
    private Button buttonY;
    @FXML
    private Button buttonU;
    @FXML
    private Button buttonK;

    private Keyboard keyboard = new Keyboard("Keyboard");
    public Controller() {
        configure(keyboard);
    }
    /**
     * When the component is created, it initialize the component representation and adding listener and MouseEvent
     * @param location type URL
     * @param resources type ResourceBundle
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        super.initialize(location, resources);
    }
    @FXML
    public void qPressed(final KeyEvent event) {
        System.out.println(event.getSource());
    }
    @FXML
    public void sPressed(final KeyEvent event) {
        System.out.println(event.getSource());
    }
    @FXML
    public void dPressed(final KeyEvent event) {
        System.out.println(event.getSource());
    }
    @FXML
    public void fPressed(final KeyEvent event) {
        System.out.println(event.getSource());
    }
    @FXML
    public void gPressed(final KeyEvent event) {
        System.out.println(event.getSource());
    }
    @FXML
    public void hPressed(final KeyEvent event) {
        System.out.println(event.getSource());
    }
    @FXML
    public void jPressed(final KeyEvent event) {
        System.out.println(event.getSource());
    }
    @FXML
    public void zPressed(final KeyEvent event) {
        System.out.println(event.getSource());
    }
    @FXML
    public void ePressed(final KeyEvent event) {
        System.out.println(event.getSource());
    }
    @FXML
    public void tPressed(final KeyEvent event) {
        System.out.println(event.getSource());
    }
    @FXML
    public void yPressed(final KeyEvent event) {
        System.out.println(event.getSource());
    }
    @FXML
    public void uPressed(final KeyEvent event) {
        System.out.println(event.getSource());
    }
    @FXML
    public void kPressed(final KeyEvent event) {
        System.out.println(event.getSource());
    }
    @FXML
    public void connectGate(final MouseEvent event) {

    }
}