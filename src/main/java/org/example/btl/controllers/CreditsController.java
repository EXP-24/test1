package org.example.btl.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

import static org.example.btl.GameApplication.MAX_HEIGHT;
import static org.example.btl.GameApplication.MAX_WIDTH;

public class CreditsController {
    @FXML
    private Pane creditsPane;

    private Timeline timeline;

    @FXML
    private void initialize() {

        Text creditsText = new Text("""
               GAME: ARKANOID!
                
                
                
               Developed by: TEAM 7
               
               
               Leader, Game Logic, Menu & Audio Developer
               Phạm Đức Cường 
               
               
               
               
               Level Designer & Map System Developer
               Đỗ Trọng An 
               
               
               
               
               Score and Power-up System Developer
               Nguyễn Tuấn Anh
               
               
               
               
               Pause Menu & Game Over System Developer
               Khương Tuấn Anh
                
                
                
                
               Course Project:
               Object-Oriented Programming
               Semester: 2025
                
                
                
               Tools & Technologies:
               JavaFX
               Scene Builder
               IntelliJ IDEA
               Aseprite
                
                
                
               Special Thanks:
               Caffeine and Deadline
               """);

        creditsText.setStyle("""
            -fx-font-size: 30px;
            -fx-fill: white;
            -fx-font-family: 'PixelPurl';
        """);

        creditsText.setWrappingWidth(1152);
        creditsText.setTextAlignment(TextAlignment.CENTER);
        creditsText.setLayoutX(0);


        Text thanksText = new Text("THANK YOU FOR PLAYING!");
        thanksText.setStyle("""
            -fx-font-size: 36px;
            -fx-fill: white;
            -fx-font-family: 'Consolas';
        """);

        thanksText.setWrappingWidth(1152);
        thanksText.setTextAlignment(TextAlignment.CENTER);
        thanksText.setLayoutX(0);
        thanksText.setLayoutY(MAX_HEIGHT / 2.0);
        thanksText.setVisible(false);

        Text guideText = new Text("Nhấn ENTER để quay lại Menu");
        guideText.setStyle("""
            -fx-font-size: 20px;
            -fx-fill: gray;
            -fx-font-family: 'Consolas';
        """);
        guideText.setWrappingWidth(1152);
        guideText.setTextAlignment(TextAlignment.CENTER);
        guideText.setLayoutX(0);
        guideText.setLayoutY(MAX_HEIGHT - 30);
        creditsPane.getChildren().addAll(creditsText, thanksText, guideText);

        Platform.runLater(() -> {
            double paneHeight = creditsPane.getHeight();
            double textHeight = creditsText.getBoundsInLocal().getHeight();
            double startY = paneHeight + 50;
            double endY = -textHeight - 50;

            creditsText.setLayoutY(startY);

            timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0),
                            new KeyValue(creditsText.layoutYProperty(), startY)),
                    new KeyFrame(Duration.seconds(20),
                            new KeyValue(creditsText.layoutYProperty(), endY))
            );

            timeline.setOnFinished(e -> {
                creditsText.setVisible(false);
                thanksText.setVisible(true);
            });

            timeline.play();

            creditsPane.requestFocus();
            creditsPane.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    timeline.stop();
                    returnToMenu();
                }
            });
        });
    }

    private void returnToMenu() {
        try {
            Parent menuRoot = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/org/example/btl/Menu.fxml")));
            Stage stage = (Stage) creditsPane.getScene().getWindow();
            stage.setScene(new Scene(menuRoot, MAX_WIDTH, MAX_HEIGHT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
