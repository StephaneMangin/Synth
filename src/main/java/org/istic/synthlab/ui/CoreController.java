package org.istic.synthlab.ui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.istic.synthlab.core.modules.io.IInput;
import org.istic.synthlab.core.modules.io.IOutput;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.ui.plugins.cable.CurveCable;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * FX controller of core.fxml
 * Represents the global view of the application
 * @author Thibaut Rousseau <thibaut.rousseau@outlook.com>
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class CoreController implements Initializable, IObserver {
    // Be sure there's never a component named like this for this to work
    private static final String DRAG_N_DROP_MOVE_GUARD = "";
    private static final double ZOOM_STEP = 0.01;
    private static final double ZOOM_MAX = 1.2;
    private static final double ZOOM_MIN = 0.6;

    @FXML
    private TitledPane componentList;

    private final double componentListX = 50.0;
    private final double componentListY = 50.0;

    @FXML
    private BorderPane borderPane;
    @FXML
    private ScrollPane scrollpane;
    @FXML
    private ListView<Node> listView;
    @FXML
    private TextArea textarea;
    @FXML
    private Button pauseButton;
    @FXML
    private Button playButton;
    @FXML
    public AnchorPane anchorPane;

    private Image imageScissors = new Image(getClass().getResourceAsStream("/ui/images/scissors.png"), 150, 0, true, true);

    private Boolean deleteMod = false;

    /**
     * This method initializes the list view and the grid
     * @param location Not used
     * @param resources Not used
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onPause();
        initializeListView();

//        componentList.setLayoutX(componentListX);
//        componentList.setLayoutY(componentListY);
//        scrollpane.vvalueProperty().addListener((observable, oldValue, newValue) -> {
//            componentList.relocate(
//                componentList.getLayoutX(),
//                componentListY + (anchorPane.getPrefHeight() * scrollpane.getVvalue() * 0.72)
//            );
//        });
//        scrollpane.hvalueProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println(anchorPane.getPrefWidth());
//            System.out.println(scrollpane.getPrefWidth());
//            System.out.println(anchorPane.getPrefWidth() / scrollpane.getPrefWidth());
//            System.out.println(scrollpane.getPrefWidth() / anchorPane.getPrefWidth());
//            componentList.relocate(
//                componentListX + (anchorPane.getPrefWidth() * scrollpane.getHvalue() * 0.3824),
//                componentList.getLayoutY()
//            );
//        });

        // Center the anchorpane inside the scrollpane when zooming
//        anchorPane.boundsInParentProperty().addListener(
//                new ChangeListener<Bounds>() {
//                    @Override
//                    public void changed(ObservableValue<? extends Bounds> observableValue, Bounds oldBounds, Bounds newBounds) {
//                        System.out.println("Bound changed");
//                        anchorPane.setPrefSize(
//                                Math.max(scrollpane.getBoundsInParent().getMaxX(), newBounds.getWidth()),
//                                Math.max(scrollpane.getBoundsInParent().getMaxY(), newBounds.getHeight())
//                        );
//                    }
//                }
//        );

        anchorPane.setOnDragOver(event -> {
            if (event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });

        anchorPane.setOnDragDropped(event -> {
            final Dragboard db = event.getDragboard();
            if (db.hasString()) {
                Node component = null;
                if (db.getString().equals(DRAG_N_DROP_MOVE_GUARD)) {
                    component = (Node) event.getGestureSource();
                }
                else {
                    try {
                        component = FXMLLoader.load(getClass().getResource("/ui/components/" + db.getString().toLowerCase() + "/view.fxml"));
                        component.setOnDragDetected(new DragDetectedComponentEventHandler());

                        anchorPane.getChildren().add(component);
                    } catch (final IOException e) {
                        e.printStackTrace();
                        event.consume();
                        return;
                    }
                }
                assert component != null;

                component.setLayoutX(event.getX()-(component.getBoundsInLocal().getWidth()/2));
                component.setLayoutY(event.getY()-(component.getBoundsInLocal().getHeight()/2));
                event.setDropCompleted(true);
            }
            event.consume();
        });

        initializeFunctions();
        ConnectionManager.addObserver(this);
        ConnectionManager.setCoreController(this);

    }


    @Override
    public void update(Map<IOutput, IInput> arg) {
        String total = "";
        Set keys = arg.keySet();
        for (Object key : keys) {
            IOutput origin = (IOutput) key;
            IInput destination = arg.get(origin);
            total += origin.toString() + " ---------> " + destination.toString() + "\n";
        }
        textarea.setText(total);
    }

    public void draw(Node node){
        anchorPane.getChildren().add(node);
    }

    public void undraw(Node node){
        anchorPane.getChildren().remove(node);
    }

    /**
     * Draw a cable between to point (one input and one one output)
     * @param arg an HashMap which contains all the cable we have into our view
     */
    @Override
    public void drawLine(HashMap<CurveCable, Connection> arg) {
        for(CurveCable key : arg.keySet()){
            key.setOnMouseClicked(event -> {
                if(deleteMod){
                    ConnectionManager.deleteLine(key);
                    deleteMod = false;
                    borderPane.setCursor(Cursor.DEFAULT);
                }
                else{
                    Stage dialog = new Stage();
                    dialog.initModality(Modality.NONE);
                    dialog.initOwner(ConnectionManager.getStage());
                    ColorPicker colorPicker = new ColorPicker();
                    VBox dialogVbox = new VBox();
                    colorPicker.setValue(key.getColor());
                    dialogVbox.getChildren().add(colorPicker);
                    Scene dialogScene = new Scene(dialogVbox);
                    dialog.setScene(dialogScene);
                    dialog.show();
                    event.consume();
                    colorPicker.valueProperty().addListener(e -> {
                            key.setColor(colorPicker.getValue());
                            dialog.hide();
                    });
                }
            });
            anchorPane.getChildren().add(key);
        }
    }

    public void unDrawLine(HashMap<CurveCable, Connection> arg){
        ObservableList<Node> list = anchorPane.getChildren();
        ObservableList<Node> temp = FXCollections.observableArrayList();

        temp.addAll(list.stream().filter(node -> node instanceof CurveCable).collect(Collectors.toList()));
        temp.forEach(list::remove);
    }

    private void initializeFunctions(){
        imageScissors = new Image(getClass().getResourceAsStream("/ui/images/scissors.png"), 150, 0, true, true);
        scrollpane.setOnMouseClicked(event -> {
            deleteMod = false;
            borderPane.setCursor(Cursor.DEFAULT);
        });
        //imageScissors = new Image(getClass().getResource("/ui/images/scissors.png").getPath());
    }

    private void initializeListView() {
        final ObservableList<Node> data = FXCollections.observableArrayList();

        // FIXME: autodetect the components
        // replicator wasn't detected by findAllPackagesStartingWith()
        final String[] components = {"vcoa", "out", "oscilloscope", "replicator", "eg", "vca"};
        //for (String component: findAllPackagesStartingWith("org.istic.synthlab.components")) {
        for (final String component: components) {
            final URL image = getClass().getResource("/ui/components/" + component.toLowerCase() + "/images/small.png");
            if (image == null) {
                continue;
            }

            final Pane pane = new Pane();
            final ImageView imageView = new ImageView(new Image(image.toString()));
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(100);
            imageView.setFitHeight(50);
            imageView.setSmooth(true);
            imageView.setId(component);

            pane.getChildren().add(imageView);
            pane.setOnDragDetected(new DragDetectedListItemEventHandler());
            data.add(pane);
        }

        listView.setItems(data);
    }

    /**
     * Close the application
     */
    @FXML
    public void onActionClose() {
        Factory.createSynthesizer().stop();
        Platform.exit();
    }

    /**
     * Pause the synthesizer
     */
    @FXML
    public void onPause() {
        pauseButton.setDisable(true);
        playButton.setDisable(false);

        Factory.createSynthesizer().stop();
    }

    @FXML
    public void onCut(){
        deleteMod = !deleteMod;
        if(deleteMod){
            borderPane.setCursor(new ImageCursor(imageScissors));
        }
        else{
            borderPane.setCursor(Cursor.DEFAULT);
        }
    }

    /**
     * Start the synthesizer
     */
    @FXML
    public void onPlay() {
        pauseButton.setDisable(false);
        playButton.setDisable(true);

        Factory.createSynthesizer().start();
        Register.startComponents();
    }

    /**
     * Zoom in
     */
    @FXML
    public void zoomIn(ActionEvent actionEvent) {
        if (anchorPane.getScaleX() < ZOOM_MAX && anchorPane.getScaleY() < ZOOM_MAX) {

            double oldWidth = anchorPane.getPrefWidth();
            double oldHeight = anchorPane.getPrefHeight();
            double newWidth = anchorPane.getMinWidth() * (1 / anchorPane.getScaleX());
            double newHeight = anchorPane.getMinHeight() * (1 / anchorPane.getScaleY());
            anchorPane.setScaleX(anchorPane.getScaleX() + ZOOM_STEP);
            anchorPane.setScaleY(anchorPane.getScaleY() + ZOOM_STEP);
            anchorPane.resize(newWidth, newHeight);
            scrollpane.setVvalue(scrollpane.getVvalue() + (oldHeight / newHeight));
            scrollpane.setHvalue(scrollpane.getHvalue() + (oldWidth / newWidth));
            scrollpane.getContent().relocate(0.0, 0.0);
        }
    }

    /**
     * Zoom in
     */
    @FXML
    public void zoomOut(ActionEvent actionEvent) {
        if (anchorPane.getScaleX() > ZOOM_MIN && anchorPane.getScaleY() > ZOOM_MIN) {

            double oldWidth = anchorPane.getPrefWidth();
            double oldHeight = anchorPane.getPrefHeight();
            double newWidth = anchorPane.getMinWidth() * (1 / anchorPane.getScaleX());
            double newHeight = anchorPane.getMinHeight() * (1 / anchorPane.getScaleY());
            anchorPane.setScaleX(anchorPane.getScaleX() - ZOOM_STEP);
            anchorPane.setScaleY(anchorPane.getScaleY() - ZOOM_STEP);
            anchorPane.resize(newWidth, newHeight);
            scrollpane.setVvalue(scrollpane.getVvalue() + (oldHeight / newHeight));
            scrollpane.setHvalue(scrollpane.getHvalue() + (oldWidth / newWidth));
        }
    }

    public void defaultZoom(ActionEvent actionEvent) {
        anchorPane.setScaleX(1);
        anchorPane.setScaleY(1);
        anchorPane.setPrefSize(
                anchorPane.getMinWidth(),
                anchorPane.getMinHeight()
        );
    }

    /**
     * Place a component from the list to the pane
     */
    private class DragDetectedListItemEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(final MouseEvent event) {
            final Pane pane = (Pane) event.getSource();
            final ImageView view = (ImageView) pane.getChildren().get(0);
            final Dragboard db = view.startDragAndDrop(TransferMode.COPY);
            final ClipboardContent content = new ClipboardContent();
            content.putString(view.getId());
            try {
                final Node node = FXMLLoader.load(getClass().getResource("/ui/components/" + pane.getChildren().get(0).getId().toLowerCase() + "/view.fxml"));

                // Temporarily add the node to the pane so the CSS is applied
                anchorPane.getChildren().add(node);
                final WritableImage w  = node.snapshot(null,null);
                anchorPane.getChildren().remove(node);
                content.putImage(w);
            } catch (final IOException e) {
                e.printStackTrace();
            }
            db.setContent(content);
            event.consume();
        }
    }

    /**
     * Move a component inside the pane
     */
    private class DragDetectedComponentEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(final MouseEvent event) {
            final Node node = (Node) event.getSource();
            final Dragboard db = node.startDragAndDrop(TransferMode.COPY);
            final ClipboardContent content = new ClipboardContent();

            final WritableImage image = node.snapshot(null, null);
            content.putImage(image);

            content.putString(DRAG_N_DROP_MOVE_GUARD);
            db.setContent(content);
            event.consume();
        }
    }

    /**
     * Finds all package names starting with prefix
     * @return Set of package names
     */
    public Set<String> findAllPackagesStartingWith(String prefix) {
        final List<ClassLoader> classLoadersList = new ArrayList<>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[classLoadersList.size()])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(prefix))));
        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);

        final Set<String> packageNameSet = new HashSet<>();
        for (final Class classInstance : classes) {
            String packageName = classInstance.getPackage().getName();
            packageName = packageName.split("\\.")[packageName.split("\\.").length-1].toLowerCase();
            packageNameSet.add(packageName);
        }
        return packageNameSet;
    }

    /**
     * Remove a component from the anchorPane
     * @param pane the pane we will remove.
     */
    public void removeViewComponent(Pane pane){
        anchorPane.getChildren().remove(pane);
    }
}
