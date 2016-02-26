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
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import org.istic.synthlab.Main;
import org.istic.synthlab.components.AbstractController;
import org.istic.synthlab.components.IController;
import org.istic.synthlab.core.services.Factory;
import org.istic.synthlab.core.services.Register;
import org.istic.synthlab.ui.plugins.ComponentPane;
import org.istic.synthlab.ui.plugins.WorkspacePane;
import org.istic.synthlab.ui.plugins.history.StateType;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

/**
 * FX controller of core.fxml
 * Represents the global view of the application
 * @author Thibaut Rousseau <thibaut.rousseau@outlook.com>
 * @author Stephane Mangin <stephane[dot]mangin[at]freesbee[dot]fr>
 */
public class CoreController implements Initializable {

    private static ConnectionManager manager = new ConnectionManager();

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
    private WorkspacePane workspace;

    public AnchorPane getWorkspace() {
        return workspace;
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
        manager.setCoreController(this);
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
                final Scene scene = workspace.getScene();
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
    }

    private void initializeFunctions() {
        // We set this event so that when we click somewhere random when in cable remove mode
        // it goes back to normal mode
        workspace.setOnMouseClicked(event -> {
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
        workspace.zoomIn();
    }

    /**
     * Zoom out
     */
    @FXML
    public void zoomOut(final ActionEvent actionEvent) {
        workspace.zoomOut();
    }

    public void defaultZoom() {
        workspace.defaultZoom();
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
            final ComponentPane componentPane = loadComponent(pane.getChildren().get(0).getId().toLowerCase());
            final WritableImage w  = componentPane.snapshot(null,null);
            //workspace.getChildren().remove(componentPane);
            content.putImage(w);
            db.setContent(content);
            event.consume();
            manager.getHistory().add(componentPane, StateType.CREATED);
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

    public static ConnectionManager getConnectionManager() {
        return manager;
    }


    /**
     * Save a configuration
     */
    @FXML
    public void saveConfiguration() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + ".json");
        fileChooser.setInitialDirectory(new File(System.getProperty("java.io.tmpdir")));
        fileChooser.setTitle("Save File");
        final File file = fileChooser.showSaveDialog(manager.getStage());
        try {
            manager.getHistory().save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load a configuration
     */
    @FXML
    public boolean cancelConfiguration() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Modification detected");
        alert.setHeaderText("The workspace needs to be cleaned !");
        alert.setContentText("Are your sure ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                workspace.getChildrenUnmodifiable().forEach(node -> workspace.getChildren().remove(node));
            } catch (Exception e) {
                // Do nothing
            }
            return true;
        } else {
            // ... user chose CANCEL or closed the dialog
            return false;
        }
    }

    /**
     * Load a configuration
     */
    @FXML
    public void loadConfiguration() {
        boolean okToProcess = true;
        if (workspace.getChildrenUnmodifiable().size() > 0) {
            okToProcess = cancelConfiguration();
        }
        if (okToProcess) {
            final FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + ".json");
            fileChooser.setInitialDirectory(new File(System.getProperty("java.io.tmpdir")));
            fileChooser.setTitle("Load File");
            final File file = fileChooser.showOpenDialog(manager.getStage());
            try {
                manager.getHistory().load(file, workspace);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Return to the previous state
     */
    @FXML
    public void previousConfiguration() {
        manager.getHistory().previous();;
    }

    /**
     * Go to the next state
     */
    @FXML
    public void nextConfiguration() {
        manager.getHistory().next();
    }
    /**
     * Helper class to load a specific component
     *
     *
     * @param name
     * @return
     */
    public static ComponentPane loadComponent(String name) {
        ComponentPane componentPane = null;
        try {
            FXMLLoader loader = new FXMLLoader(CoreController.class.getResource("/ui/components/" + name + "/view.fxml"));

            componentPane = loader.load();
            componentPane.setName(name);
            componentPane.setController(loader.<IController>getController());
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return componentPane;
    }
}
