package org.example.btl.controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import org.example.btl.game.GameManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GameController {

    @FXML
    private Canvas canvas;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label topScoreLabel;

    @FXML
    private Label levelLabel;

    @FXML
    private VBox scoreBoardBox;

    @FXML
    private Label score1, score2, score3, score4, score5, score6, score7, score8, score9, score10;

    private GameManager gameManager;
    private GraphicsContext gc;
    private final List<Integer> scoreBoard = new ArrayList<>();

    private static final String SCORE_FILE = "score.txt";
    private boolean isWinnerScreenActive;

    @FXML
    public void initialize() {
        loadScoresFromFile();
        displayScoresOnBoard();
        if (!scoreBoard.isEmpty()) {
            int highestScore = scoreBoard.get(0);
            GameManager.setTopScore(highestScore);
            topScoreLabel.setText("Top Score: " + highestScore);
        } else {
            GameManager.setTopScore(0);
            topScoreLabel.setText("Top Score: 0");
        }
        gc = canvas.getGraphicsContext2D();
        gameManager = new GameManager(gc, this);

        canvas.setCursor(Cursor.NONE);

        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(event -> handleKeyPressed(event));
        canvas.setOnKeyReleased(event -> handleKeyRealeased(event));

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!gameManager.win()) {
                    gameManager.updatePaddle();
                    gameManager.updateBall();
                    gameManager.checkBrickCollisions();
                    gameManager.updatePowerUp();
                    gameManager.updateAppliedPowerUp();
                }
                gameManager.renderGame();
            }
        }.start();
    }

    public void updateScore(int score, int topScore) {
        scoreLabel.setText("Score: " + gameManager.getScore());
        topScoreLabel.setText("Top Score: " + gameManager.getTopScore());
    }

    public void updateScoreBoard() {
        int score = gameManager.getScore();

        List<Integer> scores = new ArrayList<>();
        File file = new File(SCORE_FILE);

        try {
            if (file.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        scores.add(Integer.parseInt(line.trim()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        scores.add(score);

        scores.sort((a, b) -> b - a);
        if (scores.size() > 10) scores = scores.subList(0, 10);

        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (int s : scores) pw.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        scoreBoard.clear();
        scoreBoard.addAll(scores);
        displayScoresOnBoard();
    }

    public void updateLevel(int level) {
        levelLabel.setText(String.valueOf(level));
    }

    private void loadScoresFromFile() {
        try {
            Path path = Paths.get(SCORE_FILE);
            if (Files.exists(path)) {
                List<String> lines = Files.readAllLines(path);
                for (String line : lines) {
                    scoreBoard.add(Integer.parseInt(line.trim()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayScoresOnBoard() {
        Label[] labels = {score1, score2, score3, score4, score5,
                score6, score7, score8, score9, score10};

        for (int i = 0; i < labels.length; i++) {
            if (i < scoreBoard.size()) {
                labels[i].setText(String.valueOf(scoreBoard.get(i)));
            } else {
                labels[i].setText("0");
            }
        }
    }

    private void saveScoresToFile() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(SCORE_FILE))) {
            for (Integer s : scoreBoard) {
                writer.write(s.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void handleKeyPressed(KeyEvent event) {
        gameManager.handleKeyPressed(event);
    }
    private void handleKeyRealeased(KeyEvent event) {
        gameManager.handleKeyRealeased(event);
    }
}

