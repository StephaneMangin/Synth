package org.istic.synthlab.ui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.istic.synthlab.core.IObserver;
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
    private AnchorPane anchorPane;

    private Image imageScissors = new Image(getClass().getResourceAsStream("/ui/images/scissors.png"), 150, 0, true, true);

    private Boolean deleteMod = false;

    /**
     * This method initializes the list view and the grid
     * @param location Not used
     * @param resources Not used
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeListView();

        anchorPane.setOnMouseClicked(e -> System.out.println(e.getX() + " " + e.getY()));

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
                component.setLayoutX(event.getX());
                component.setLayoutY(event.getY());
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
        final String[] components = {"vcoa", "out", "oscilloscope", "replicator", "vca"};
        //for (String component: findAllPackagesStartingWith("org.istic.synthlab.components")) {
        for (final String component: components) {
            final URL image = getClass().getResource("/ui/components/" + component.toLowerCase() + "/images/small.png");
            if (image == null) {
                continue;
            }

            final Pane pane = new Pane();
            final ImageView imageView = new ImageView(new Image(image.toString()));
            imageView.preserveRatioProperty();
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
}
