package org.istic.synthlab;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.istic.synthlab.ui.ConnectionManager;

/**
 * @author Thibaut Rousseau <thibaut.rousseau@outlook.com>
 */
public final class Main extends Application {

    public static final String DEFAULT_SKIN = "Metal";

    @Override
    public void start(final Stage primaryStage) throws Exception {
        final Parent root = FXMLLoader.load(getClass().getResource("/ui/core.fxml"));
        final Scene scene = new Scene(root);
        ConnectionManager manager = new ConnectionManager();
        scene.getStylesheets().add("/ui/stylesheets/global.css");
        scene.getStylesheets().add("/ui/stylesheets/" + DEFAULT_SKIN.toLowerCase() + ".css");
        manager.setStage(primaryStage);
        primaryStage.setTitle("Synth");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
