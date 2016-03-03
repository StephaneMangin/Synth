package org.istic.synthlab.components.keyboard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import org.istic.synthlab.components.AbstractController;
import org.istic.synthlab.core.modules.keyboard.Note;
import org.istic.synthlab.ui.plugins.plug.InputPlug;
import org.istic.synthlab.ui.plugins.plug.OutputPlug;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends AbstractController implements Initializable {
    @FXML
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

        gate.setOutput(keyboard.getOutputGate());
    }
    @FXML
    public void keyPressedKeyboard(final KeyEvent event) {
        switch (event.getCode()) {
            case X:
                keyboard.playNote(Note.X);
                break;
            case W:
                keyboard.playNote(Note.W);
                break;
            case K:
                keyboard.playNote(Note.DOs);
                buttonK.setStyle("-fx-background-color: skyblue;");
                break;
            case J:
                keyboard.playNote(Note.SI);
                buttonJ.setStyle("-fx-background-color: magenta;");
                break;
            case U:
                keyboard.playNote(Note.LAd);
                buttonU.setStyle("-fx-background-color: midnightblue;");
                break;
            case H:
                keyboard.playNote(Note.LA);
                buttonH.setStyle("-fx-background-color: green;");
                break;
            case Y:
                keyboard.playNote(Note.SOLd);
                buttonY.setStyle("-fx-background-color: midnightblue;");
                break;
            case G:
                keyboard.playNote(Note.SOL);
                buttonG.setStyle("-fx-background-color: red;");
                break;
            case T:
                keyboard.playNote(Note.FAd);
                buttonT.setStyle("-fx-background-color: midnightblue;");
                break;
            case F:
                keyboard.playNote(Note.FA);
                buttonF.setStyle("-fx-background-color: orangered;");
                break;
            case D:
                keyboard.playNote(Note.MI);
                buttonD.setStyle("-fx-background-color: goldenrod;");
                break;
            case E:
                keyboard.playNote(Note.REd);
                buttonE.setStyle("-fx-background-color: midnightblue;");
                break;
            case S:
                keyboard.playNote(Note.RE);
                buttonS.setStyle("-fx-background-color: lightpink;");
                break;
            case Z:
                keyboard.playNote(Note.DOd);
                buttonZ.setStyle("-fx-background-color: midnightblue;");
                break;
            case Q:
                keyboard.playNote(Note.DO);
                buttonQ.setStyle("-fx-background-color: deepskyblue;");
                break;
            default:
                buttonQ.setStyle("-fx-background-color: white;");
                break;
        }
    }

    @FXML
    public void keyReleasedKeyboard(final KeyEvent event) {
        switch (event.getCode()) {
            case X:
                break;
            case W:
                break;
            case K:
                buttonK.setStyle("-fx-background-color: white;");
                break;
            case J:
                buttonJ.setStyle("-fx-background-color: white;");
                break;
            case U:
                buttonU.setStyle("-fx-background-color: black;");
                break;
            case H:
                buttonH.setStyle("-fx-background-color: white;");
                break;
            case Y:
                buttonY.setStyle("-fx-background-color: black;");
                break;
            case G:
                buttonG.setStyle("-fx-background-color: white;");
                break;
            case T:
                buttonT.setStyle("-fx-background-color: black;");
                break;
            case F:
                buttonF.setStyle("-fx-background-color: white;");
                break;
            case D:
                buttonD.setStyle("-fx-background-color: white;");
                break;
            case E:
                buttonE.setStyle("-fx-background-color: black;");
                break;
            case S:
                buttonS.setStyle("-fx-background-color: white;");
                break;
            case Z:
                buttonZ.setStyle("-fx-background-color: black;");
                break;
            case Q:
                buttonQ.setStyle("-fx-background-color: white;");
                break;
            default:
                break;
        }
        keyboard.releaseNote();
    }
    @FXML
    public void connectGate(final MouseEvent event) {
        manager.plugOutput((OutputPlug) event.getSource());
        event.consume();
    }
}