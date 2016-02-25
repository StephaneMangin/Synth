package org.istic.synthlab.ui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import org.istic.synthlab.Main;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * FX controller of core.fxml
 * Represents the global view of the application
 * @author Thibaut Rousseau <thibaut.rousseau@outlook.com>
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class CoreController implements Initializable {
    // Be sure there's never a component named like this for this to work
    private static final String DRAG_N_DROP_MOVE_GUARD = "";
    private static final double ZOOM_STEP = 0.01;
    private static final double ZOOM_MAX = 2;
    private static final double ZOOM_MIN = 0.4;

    @FXML
    private Menu skinMenu;
    @FXML
    private TitledPane componentList;
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

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    /**
     * This method initializes the list view and the grid
     * @param location Not used
     * @param resources Not used
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Stop the synthesizer att the beginning
        onPause();
        initializeSkinMenu();
        initializeListView();
        initializeMainPane();
        initializeFunctions();
        ConnectionManager.setCoreController(this);

    }

    // Skin related stuff
    private final List<RadioMenuItem> stylesheets = new ArrayList<RadioMenuItem>() {{
        add(new RadioMenuItem("Metal"));
        add(new RadioMenuItem("Wood"));
    }};
    private final ToggleGroup skinToggleGroup = new ToggleGroup();
    private String currentStylesheet = "/ui/stylesheets/" + Main.DEFAULT_SKIN.toLowerCase() + ".css";

    private void initializeSkinMenu() {
        for (final RadioMenuItem item : stylesheets) {
            item.setToggleGroup(skinToggleGroup);
            if (item.getText().equalsIgnoreCase(Main.DEFAULT_SKIN))
                item.setSelected(true);

            item.selectedProperty().addListener((observable, oldValue, newValue) -> {
                final String stylesheet = "/ui/stylesheets/" + item.getText().toLowerCase() + ".css";
                final Scene scene = anchorPane.getScene();
                scene.getStylesheets().remove(currentStylesheet);
                scene.getStylesheets().add(stylesheet);
                currentStylesheet = stylesheet;
            });
            skinMenu.getItems().add(item);
        }
    }

    private void initializeListView() {
        final ObservableList<Node> data = FXCollections.observableArrayList();
        for (String component: findAllPackagesStartingWith("org.istic.synthlab.components", false)) {
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

    private void initializeMainPane() {
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

                // Move a component
                if (db.getString().equals(DRAG_N_DROP_MOVE_GUARD)) {
                    component = (Node) event.getGestureSource();
                }
                // Load a component
                else {
                    try {
                        component = FXMLLoader.load(getClass().getResource("/ui/components/" + db.getString().toLowerCase() + "/view.fxml"));
                        addWithDragging(anchorPane, component);
                    } catch (final IOException e) {
                        e.printStackTrace();
                        event.consume();
                        return;
                    }
                }


                assert component != null;
                double x = event.getX()-(component.getBoundsInLocal().getWidth()/2),
                       y = event.getY()-(component.getBoundsInLocal().getHeight()/2);

                // Prevent the components from being outside the anchorPane
                if (x < 0) {
                    x = 0;
                }
                if (y < 0) {
                    y = 0;
                }
                if (x + component.getBoundsInParent().getWidth() > anchorPane.getWidth()) {
                    x = anchorPane.getWidth() - component.getBoundsInParent().getWidth();
                }
                if (y + component.getBoundsInParent().getHeight() > anchorPane.getHeight()) {
                    y = anchorPane.getHeight() - component.getBoundsInParent().getHeight();
                }

                // Place the component
                component.setLayoutX(x);
                component.setLayoutY(y);

                // Detect collisions
                // TODO: modify x and y so that there's no collision
                for (final Node otherComponent : anchorPane.getChildren()) {
                    if (component != otherComponent) {
                        if (component.getBoundsInParent().intersects(otherComponent.getBoundsInParent())) {
                            System.out.println("Collision");
                        }
                    }
                }

                event.setDropCompleted(true);
            }
            event.consume();
        });
    }

    private void initializeFunctions() {
        // We set this event so that when we click somewhere random when in cable remove mode
        // it goes back to normal mode
        anchorPane.setOnMouseClicked(event -> {
            if (cableRemoveMode) {
                cableRemoveMode = false;
                borderPane.setCursor(Cursor.DEFAULT);
            }
            event.consume();
        });
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

    // Cable deletion stuff
    private boolean cableRemoveMode = false;

    @FXML
    public void onCut() {
        cableRemoveMode = !cableRemoveMode;
        if (cableRemoveMode) {
            borderPane.setCursor(new ImageCursor(new Image(getClass().getResourceAsStream("/ui/images/scissors.png"), 150, 0, true, true)));
        }
        else {
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
    public void zoomIn(final ActionEvent actionEvent) {
        if (anchorPane.getScaleX() < ZOOM_MAX && anchorPane.getScaleY() < ZOOM_MAX) {
            //double oldWidth = anchorPane.getPrefWidth();
            //double oldHeight = anchorPane.getPrefHeight();
            double newWidth = anchorPane.getMinWidth() * (1 / anchorPane.getScaleX());
            double newHeight = anchorPane.getMinHeight() * (1 / anchorPane.getScaleY());
            anchorPane.setScaleX(anchorPane.getScaleX() + ZOOM_STEP);
            anchorPane.setScaleY(anchorPane.getScaleY() + ZOOM_STEP);
            anchorPane.resize(newWidth, newHeight);
            //scrollpane.setVvalue(scrollpane.getVvalue() + (1 - (oldHeight / newHeight)));
            //scrollpane.setHvalue(scrollpane.getHvalue() + (1 - (oldWidth / newWidth)));
        }
    }

    /**
     * Zoom out
     */
    @FXML
    public void zoomOut(final ActionEvent actionEvent) {
        if (anchorPane.getScaleX() > ZOOM_MIN && anchorPane.getScaleY() > ZOOM_MIN) {

            //double oldWidth = anchorPane.getPrefWidth();
            //double oldHeight = anchorPane.getPrefHeight();
            double newWidth = anchorPane.getMinWidth() * (1 / anchorPane.getScaleX());
            double newHeight = anchorPane.getMinHeight() * (1 / anchorPane.getScaleY());
            anchorPane.setScaleX(anchorPane.getScaleX() - ZOOM_STEP);
            anchorPane.setScaleY(anchorPane.getScaleY() - ZOOM_STEP);
            anchorPane.resize(newWidth, newHeight);
            //scrollpane.setVvalue(scrollpane.getVvalue() + (1 - (oldHeight / newHeight)));
            //scrollpane.setHvalue(scrollpane.getHvalue() + (1 - (oldWidth / newWidth)));
        }
    }

    public void defaultZoom() {
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
     * Finds all package names starting with prefix
     * @param prefix The package in which to start searching
     * @param statik True to statically return components names
     * @return a set of component name
     */
    public Set<String> findAllPackagesStartingWith(final String prefix, final boolean statik) {
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
        if (statik) {
            packageNameSet.clear();
            packageNameSet.add("vcoa");
            packageNameSet.add("out");
            packageNameSet.add("oscilloscope");
            packageNameSet.add("replicator");
            packageNameSet.add("eg");
            packageNameSet.add("vca");
            packageNameSet.add("vcfa");
            packageNameSet.add("mixer");
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

    /**
     * Add a component to the anchorpane and declare the dragging controls handlers
     */
    private void addWithDragging(final AnchorPane root, final Node component) {
        component.setOnDragDetected(event -> {
            // TODO: add component relocation
            final AnchorPane node = (AnchorPane) event.getSource();
            node.startFullDrag();
            final Dragboard db = node.startDragAndDrop(TransferMode.ANY);
            final ClipboardContent content = new ClipboardContent();

            final SnapshotParameters params = new SnapshotParameters();
            final Scale scale = new Scale();
            scale.setX(anchorPane.getScaleX());
            scale.setY(anchorPane.getScaleY());
            // FIXME: Work fot the minimum scale value but not for the maximum while zooming the anchorpane ?!
            final WritableImage image = node.snapshot(
                    params,
                    new WritableImage(
                            ((Double)(node.getWidth() * anchorPane.getScaleX())).intValue(),
                            ((Double)(node.getHeight() * anchorPane.getScaleY())).intValue()
                    )
            );
            content.putImage(image);

            content.putString(DRAG_N_DROP_MOVE_GUARD);
            db.setContent(content);
            event.consume();
        });
        root.getChildren().add(component);
    }
}
