package org.istic.synthlab.components.oscilloscope;

import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by seb on 04/02/16.
 */
public class Controller implements Initializable{
    @FXML
    SwingNode swing;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        swing.setContent(new JButton("Click me!"));
    }


}
