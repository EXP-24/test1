package org.example.btl.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static org.example.btl.GameApplication.MAX_HEIGHT;
import static org.example.btl.GameApplication.MAX_WIDTH;

public class MenuController {

    @FXML
    private ImageView playButton;

    @FXML
    private ImageView collectionButton;

    @FXML
    private ImageView exitButton;

    @FXML
    private ImageView creditsButton;

    private Image playButtonImage, playButtonHover;
    private Image exitButtonImage, exitButtonHover;
    private Image collectionButtonImage, collectionButtonHover;
    private Image creditsButtonImage, creditsButtonHover;
    private Image mouseImage;

    @FXML
    public void initialize() {
        mouseImage = loadImage("mouse");
        Platform.runLater(() -> {
            playButton.getScene().setCursor(new ImageCursor(mouseImage));
        });
        playButtonImage = loadImage("start");
        playButtonHover = loadImage("startHover");
        exitButtonImage = loadImage("exit");
        exitButtonHover = loadImage("exitHover");
        collectionButtonImage = loadImage("collection");
        collectionButtonHover = loadImage("collectionHover");
        creditsButtonImage = loadImage("credits");
        creditsButtonHover = loadImage("creditsHover");

        setHoverEffect(playButton, playButtonImage, playButtonHover);
        setHoverEffect(exitButton, exitButtonImage, exitButtonHover);
        setHoverEffect(collectionButton, collectionButtonImage, collectionButtonHover);
        setHoverEffect(creditsButton, creditsButtonImage, creditsButtonHover);

        playButton.setOnMouseClicked(e -> startgame());
        exitButton.setOnMouseClicked(e -> System.exit(0));
        collectionButton.setOnMouseClicked(e -> openCollection());
        creditsButton.setOnMouseClicked(e -> openCredits());

    }

    private Image loadImage(String filename) {
        return new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/org/example/btl/images/texts/" + filename +".png")));
    }

    private void setHoverEffect(ImageView button, Image normal, Image hover) {
        button.setOnMouseEntered(e -> button.setImage(hover));
        button.setOnMouseExited(e -> button.setImage(normal));
    }

    private void startgame() {
        try {
            Parent gameroot = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/org/example/btl/Game.fxml")));
            Stage stage = (Stage) playButton.getScene().getWindow();
            stage.setScene(new Scene(gameroot, MAX_WIDTH, MAX_HEIGHT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openCollection() {
        try {
            Parent gameroot = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/org/example/btl/Collections.fxml")));
            Stage stage = (Stage) collectionButton.getScene().getWindow();
            stage.setScene(new Scene(gameroot, MAX_WIDTH, MAX_HEIGHT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openCredits() {
        try {
            Parent gameroot = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/org/example/btl/Credits.fxml")));
            Stage stage = (Stage) creditsButton.getScene().getWindow();
            stage.setScene(new Scene(gameroot, MAX_WIDTH, MAX_HEIGHT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
