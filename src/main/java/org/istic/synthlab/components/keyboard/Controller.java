package org.istic.synthlab.components.keyboard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import org.istic.synthlab.components.AbstractController;

public class Controller extends AbstractController implements Initializable {
    private Keyboard keyboard = new Keyboard("Keyboard");
    public Controller() {
        configure(keyboard);
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
}