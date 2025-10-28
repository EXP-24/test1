package org.example.btl.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.btl.game.SkinManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.example.btl.GameApplication.MAX_HEIGHT;
import static org.example.btl.GameApplication.MAX_WIDTH;

public class CollectionsController {
    @FXML
    private ImageView paddle0, paddle1, paddle2, paddle3, paddle4,
            paddle5, paddle6, paddle7, paddle8, paddle9;

    @FXML
    private ImageView backButton;

    private Image backImage;
    private Image backHover;
    private Image mouseImage;
    private ImageView selectedPaddle;
    private List<ImageView> paddles;

    @FXML
    public void initialize() {
        paddles = new ArrayList<>();
        paddles.addAll(List.of(paddle0, paddle1, paddle2, paddle3, paddle4, paddle5, paddle6, paddle7, paddle8, paddle9));
        backImage = loadImage("texts/back");
        backHover = loadImage("texts/backHover");
        mouseImage = loadImage("texts/mouse");
        Platform.runLater(() -> {
            backButton.getScene().setCursor(new ImageCursor(mouseImage));
        });

        for (int i = 0; i < paddles.size(); i++) {
            Image image = loadImage("paddles/Paddle" + i);
            paddles.get(i).setImage(image);

            int finalI = i;
            paddles.get(i).setOnMouseClicked(e -> selectedSkin(paddles.get(finalI)));
        }

        setHoverEffect(backButton, backImage, backHover);
        backButton.setOnMouseClicked(e -> backToMainMenu());
    }

    private Image loadImage(String filename) {
        return new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/org/example/btl/images/" + filename +".png")));
    }

    private void setHoverEffect(ImageView button, Image normal, Image hover) {
        button.setOnMouseEntered(e -> button.setImage(hover));
        button.setOnMouseExited(e -> button.setImage(normal));
    }

    private void selectedSkin(ImageView paddle) {
        if (selectedPaddle != null) {
            selectedPaddle.setEffect(null);
        }
        DropShadow glow = new DropShadow();
        glow.setColor(Color.LIGHTBLUE);
        glow.setWidth(40);
        glow.setHeight(40);
        glow.setSpread(0.8);

        paddle.setEffect(glow);
        selectedPaddle = paddle;

        int selectedIndex = paddles.indexOf(paddle);
        SkinManager.setSkinIndex(selectedIndex);
    }

    private void backToMainMenu() {
        try{
            Parent gameroot = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/org/example/btl/Menu.fxml")));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(gameroot, MAX_WIDTH, MAX_HEIGHT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
