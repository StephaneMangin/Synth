package org.istic.synthlab.components.keyboard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import org.istic.synthlab.components.AbstractController;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends AbstractController implements Initializable {
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

    /**
     * C key
     */
    @FXML
    public void qPressed(final KeyEvent event) {
        System.out.println(event.getSource());
    }

    /**
     * D key
     */
    @FXML
    public void sPressed(final KeyEvent event) {
        System.out.println(event.getSource());
    }

    /**
     * E key
     */
    @FXML
    public void dPressed(final KeyEvent event) {
        System.out.println(event.getSource());
    }

    /**
     * F key
     */
    @FXML
    public void fPressed(final KeyEvent event) {
        System.out.println(event.getSource());
    }

    /**
     * G key
     */
    @FXML
    public void gPressed(final KeyEvent event) {
        System.out.println(event.getSource());
    }

    /**
     * A key
     */
    @FXML
    public void hPressed(final KeyEvent event) {
        System.out.println(event.getSource());
    }

    /**
     * B key
     */
    @FXML
    public void jPressed(final KeyEvent event) {
        System.out.println(event.getSource());
    }

    /**
     * Upper octave C key
     */
    @FXML
    public void kPressed(final KeyEvent event) {
        System.out.println(event.getSource());
    }

    /**
     * C# key
     */
    @FXML
    public void zPressed(final KeyEvent event) {
        System.out.println(event.getSource());
    }

    /**
     * D# key
     */
    @FXML
    public void ePressed(final KeyEvent event) {
        System.out.println(event.getSource());
    }

    /**
     * F# key
     */
    @FXML
    public void tPressed(final KeyEvent event) {
        System.out.println(event.getSource());
    }

    /**
     * G# key
     */
    @FXML
    public void yPressed(final KeyEvent event) {
        System.out.println(event.getSource());
    }

    /**
     * A# key
     */
    @FXML
    public void uPressed(final KeyEvent event) {
        System.out.println(event.getSource());
    }
}