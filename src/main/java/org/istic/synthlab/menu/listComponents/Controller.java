package org.istic.synthlab.menu.listComponents;

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
 * Created by augustin
 */
public class Controller implements Initializable {
    private ListComponents listComponents;
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
    @FXML
    public void initialize(URL location, ResourceBundle resources){
        paneComponents.setId("paneComponents"+counter++);
        labelComponent.setText("labelComponent"+counter);
        imageComponent = new Image(getClass().getResourceAsStream("/images/logo.jpg"), 150, 0, true, true);
        imageViewComponent.setImage(imageComponent);
        testListDrag();
    }

    public void testListDrag(){
            imageViewComponent.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                System.out.println(paneComponents.getParent());
                registerDragEvent(paneComponents.getParent());
                event.consume();
            });
    }


    protected void registerDragEvent(Parent root){
        //DRAG DETECTED
        paneComponents.setOnDragDetected(event -> {
            dragboard = imageViewComponent.startDragAndDrop(TransferMode.COPY);
            ClipboardContent content = new ClipboardContent();
            content.putImage(imageViewComponent.getImage());
            //content.putString(labelComponent.getText());
            dragboard.setContent(content);
            System.out.println("Drag detected");
            event.consume();
        });
        //DRAG OVER
        //root.setOnDragOver(event -> {
        //    event.acceptTransferModes(TransferMode.COPY);
            //System.out.println("Drag over detected");
        //    event.consume();
        //});
    }

    public Dragboard getDragboard() {
        return dragboard;
    }
}
