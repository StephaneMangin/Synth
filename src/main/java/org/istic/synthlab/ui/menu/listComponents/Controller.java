package org.istic.synthlab.ui.menu.listComponents;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author augustin <augustin.bardou@gmail.com>
 */
public class Controller implements Initializable {
    public static int counter = 0;
    @FXML
    private Label labelComponent;
    @FXML
    private Pane paneComponents;
    @FXML
    private Image imageComponent;
    @FXML
    private ImageView imageViewComponent;

    private Dragboard dragboard;


    public void initialize(URL location, ResourceBundle resources){
        paneComponents.setId("paneComponents"+counter++);
        labelComponent.setText("labelComponent"+counter);
        imageComponent = new Image(getClass().getResourceAsStream("/ui/images/logo.jpg"), 150, 0, true, true);
        imageViewComponent.setImage(imageComponent);
        testListDrag();
    }

    public void testListDrag(){
        imageViewComponent.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            registerDragEvent(paneComponents.getParent());
            event.consume();
        });
    }

    protected void registerDragEvent(Parent root){
        //DRAG DETECTED
        paneComponents.setOnDragDetected(event -> {
            dragboard = labelComponent.startDragAndDrop(TransferMode.COPY);
            ClipboardContent content = new ClipboardContent();
            content.putString(labelComponent.getText());
            dragboard.setContent(content);
            event.consume();
        });
    }
    public Dragboard getDragboard() {
        return dragboard;
    }
}
