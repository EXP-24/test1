package org.example.btl.controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import org.example.btl.game.GameManager;


public class GameController {

    @FXML
    private Canvas canvas;

    private GameManager gameManager;
    private GraphicsContext gc;

    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        gameManager = new GameManager(gc);

        canvas.setCursor(Cursor.NONE);

        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(event -> handleKeyPressed(event));
        canvas.setOnKeyReleased(event -> handleKeyRealeased(event));

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameManager.updatePaddle();
                gameManager.updateBall();
                gameManager.checkBrickCollisions();
                gameManager.updatePowerUp();
                gameManager.updateAppliedPowerUp();
                gameManager.renderGame();
            }
        }.start();
    }

    private void handleKeyPressed(KeyEvent event) {
        gameManager.handleKeyPressed(event);
    }

    private void handleKeyRealeased(KeyEvent event) {
        gameManager.handleKeyRealeased(event);
    }
}

