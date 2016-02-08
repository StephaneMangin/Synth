package org.istic.synthlab.components.oscilloscope;

import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by seb on 04/02/16.
 */
public class Controller implements Initializable{

    @FXML
    Pane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final SwingNode swingNode = new SwingNode();
        swingNode.setContent(new JButton("Click me!"));
        pane.getChildren().add(swingNode);

    }



}
