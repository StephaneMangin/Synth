package org.istic.synthlab.components.oscilloscope;

import com.jsyn.scope.swing.AudioScopeView;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import org.istic.synthlab.components.AbstractController;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Sebastien
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class Controller extends AbstractController {

    @FXML
    private Pane pane;

    private Oscilloscope oscilloscope   = new Oscilloscope("Visualizer");
    /**
     * When the component is created, it initialize the component representation adding listener and MouseEvent
     * @param location type URL
     * @param resources type ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        configure(oscilloscope);

        AudioScopeView byuu = (AudioScopeView) this.oscilloscope.getView();
        byuu.setSize(new Dimension(25, 25));

        final SwingNode swingNode = new SwingNode();
        swingNode.setContent(this.oscilloscope.getView());
        pane.getChildren().add(swingNode);
    }

}
