package org.example.btl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class GameApplication extends Application {

    public static final double MAX_WIDTH = 1152;
    public static final double MAX_HEIGHT = 704;
    public static final double PLAY_AREA_X = 288;
    public static final double PLAY_AREA_Y = 64;
    public static final double PLAY_AREA_WIDTH = 544;
    public static final double PLAY_AREA_HEIGHT = 608;

    @Override
    public void start(Stage stage) throws IOException {

        URL fxmlUrl = Objects.requireNonNull(
                getClass().getResource("/org/example/btl/Menu.fxml"),
                "Cannot find Game.fxml"
        );

        Parent root = FXMLLoader.load(fxmlUrl);
        Scene scene = new Scene(root, MAX_WIDTH, MAX_HEIGHT);

        URL iconUrl = Objects.requireNonNull(
                getClass().getResource("/org/example/btl/images/arkanoid_512.png"),
                "Cannot find arkanoid_512.png"
        );

        Image icon = new Image(iconUrl.toExternalForm());
        stage.getIcons().add(icon);
        stage.setTitle("Arkanoid!");
        stage.setResizable(false);

        stage.setScene(scene);
        stage.show();
    }
}
