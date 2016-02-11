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
    @Override
    public void start(final Stage primaryStage) throws Exception {
        final Parent root = FXMLLoader.load(getClass().getResource("/ui/core.fxml"));
        ConnectionManager.setStage(primaryStage);
        primaryStage.setTitle("Synth");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
