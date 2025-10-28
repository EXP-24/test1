package org.example.btl.game;

import org.example.btl.controllers.GameController;
import org.example.btl.game.bricks.MapBrick;
import org.example.btl.game.Brick;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.example.btl.game.powerups.*;
import org.example.btl.game.sounds.SoundManager;
import org.example.btl.lives.LifeManage;

import static org.example.btl.GameApplication.*;

public class GameManager {

    private static GameController controller;
    private Renderer renderer;
    private Paddle paddle;
    private Ball ball;
    private List<Ball> balls;
    private MapBrick map;
    private LifeManage lifeManage;
    private List<GameObject> objects;
    private List<PowerUp> activePowerUps;
    private List<PowerUp> appliedPowerUps;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private int currentLevel;
    private boolean gameWon = false;
    private GraphicsContext gc;
    private Image winnerImage;
    private boolean isWinnerScreenActive = false;

    private static int score = 0;
    private static int topScore = 0;

    public int getScore() {
        return score;
    }

    public static void setTopScore(int score) {
        topScore = score;
    }

    public int getTopScore() {
        return topScore;
    }

    public GameManager(GraphicsContext gc, GameController controller) {
        this.renderer = new Renderer(gc);
        this.gc = gc;
        this.controller = controller;
        initGame();
    }

    public static void setController(GameController controllerRef) {
        controller = controllerRef;
    }

    private void nextLevel() {
        for (Ball currentBall : balls) {
            currentBall.setAttached(true);
        }
        this.currentLevel++;
        if (controller != null) {
            Platform.runLater(() -> controller.updateLevel(this.currentLevel));
        }
        loadLevel(this.currentLevel);
    }


    private void checkLevelCompletion() {
        if (gameWon) {
            return;
        }
        for (Brick brick : map.getBricks()) {
            if (brick.getBrickType() != 9) {
                return;
            }
        }
        if (!map.getBricks().isEmpty()) {
            nextLevel();
        } else {
            nextLevel();
        }
    }

    private void loadLevel(int levelNumber) {

        int[][] layout = MapBrick.loadMap(levelNumber);

        if (layout != null) {
            map.createMap(layout, PLAY_AREA_X, PLAY_AREA_Y);
            //resetLevelState();
        } else {
            this.gameWon = true;
        }
    }

    private void initGame() {
        paddle = new Paddle(540, 614, 64, 24, 3);
        ball = new Ball(0, 0, 12, 12, 2, -2, 1);
        balls = new ArrayList<>();
        balls.add(ball);
        map = new MapBrick();
        this.currentLevel = 1;
        if (controller != null) {
            Platform.runLater(() -> controller.updateLevel(this.currentLevel));
        }
        loadLevel(currentLevel);
        activePowerUps = new ArrayList<>();
        appliedPowerUps = new ArrayList<>();
        lifeManage = new LifeManage(5);

        try {
            String imagePath = "/org/example/btl/images/winDemo.png";
            winnerImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        } catch (Exception e) {
            System.err.println("Không thể tải ảnh 'winner.png'");
            e.printStackTrace();
        }
    }

    public void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.A) {
            leftPressed = true;
        } else if (event.getCode() == KeyCode.D) {
            rightPressed = true;
        } else if (event.getCode() == KeyCode.SPACE) {
            for (Ball b : balls) {
                if (b.isAttached()) {
                    b.setAttached(false);
                }
            }
        }
    }

    public void handleKeyRealeased(KeyEvent event) {
        if (event.getCode() == KeyCode.A) {
            leftPressed = false;
        } else if (event.getCode() == KeyCode.D) {
            rightPressed = false;
        }
    }

    public void updatePaddle() {
        if (leftPressed && !rightPressed) {
            paddle.startMovingLeft();
        } else if (rightPressed && !leftPressed) {
            paddle.startMovingRight();
        } else {
            paddle.stop();
        }
        paddle.update();
    }

    public void updateBall() {
        Iterator<Ball> ballIterator = balls.iterator();
        while (ballIterator.hasNext()){
            Ball currentball = ballIterator.next();
            if (currentball.isAttached()) {
                currentball.setX(paddle.getX() + (paddle.getWidth() / 2) - ball.getWidth()/2);
                currentball.setY(paddle.getY() - 10);
                continue;
            }
            else {
                currentball.update();
                currentball.bounceOff();
                if (currentball.isColliding(paddle)) {
                    currentball.bounce(paddle);
                    SoundManager.playBounce();
                }
                if (currentball.getY() > PLAY_AREA_Y + PLAY_AREA_HEIGHT) {
                    ballIterator.remove();
                }
            }
        }

        if (balls.isEmpty()) {
            lose();
            Ball newBall = new Ball(paddle.getX() + paddle.getWidth() / 2 - 6,
                    paddle.getY() - 12, 12, 12, 2, -2, 1);
            newBall.setAttached(true);
            balls.add(newBall);
        }
    }


    public void checkBrickCollisions() {
        Iterator<Brick> brickIterator = map.getBricks().iterator();
        while (brickIterator.hasNext()) {
            Brick brick = brickIterator.next();
            if (brick.isDestroyed()) continue;

            for (Ball ball : balls) {
                if (ball.isColliding(brick)) {
                    ball.bounce(brick);
                    brick.takeDamage();
                    if (brick.isDestroyed()) {
                        SoundManager.playBrickDestroySound();
                    } else {
                        SoundManager.playBrickHitSound();
                    }

                    if (brick.getBrickType() == 2) {
                        PowerUp newPowerUp;
                        switch (brick.getPowerUpType()) {
                            case 1:
                                newPowerUp = new TinyBallPowerUp(brick.getX(), brick.getY(), balls);
                                activePowerUps.add(newPowerUp);
                                break;
                            case 2:
                                newPowerUp = new FastBallPowerUp(brick.getX(), brick.getY(), balls);
                                activePowerUps.add(newPowerUp);
                                break;
                            case 3:
                                newPowerUp = new TripleBallPowerUp(brick.getX(), brick.getY(), balls);
                                activePowerUps.add(newPowerUp);
                                break;
                            case 4:
                                newPowerUp = new ExpandPaddlePowerUp(brick.getX(), brick.getY());
                                activePowerUps.add(newPowerUp);
                                break;
                            case 5:
                                newPowerUp = new GunPowerUp(brick.getX(), brick.getY(), this);
                                activePowerUps.add(newPowerUp);
                                break;
                        }
                    }
                }
                if (brick.isDestroyed()) {
                    if(brick.getBrickType() == 7 || brick.getBrickType() == 8) {
                        brickIterator.remove();
                        score += 6;
                        if(score > topScore) {
                            topScore = score;
                        }
                        controller.updateScore(score, topScore);
                        break;
                    }
                    else {
                        brickIterator.remove();
                        score += 3;
                        if(score > topScore) {
                            topScore = score;
                        }
                        controller.updateScore(score, topScore);
                        break;
                    }
                }
            }
        }
    }

    public void updatePowerUp() {
        Iterator<PowerUp> powerUpIterator = activePowerUps.iterator();
        while(powerUpIterator.hasNext()) {
            PowerUp powerUp = powerUpIterator.next();
            powerUp.update();

            if (powerUp.isColliding(paddle)) {
                boolean effectExist = false;

                for(PowerUp existingEffect : appliedPowerUps) {
                    if (existingEffect.getType().equals(powerUp.getType())) {
                        existingEffect.active();
                        effectExist = true;
                        break;
                    }
                }
                if (!effectExist) {
                    powerUp.applyEffect(paddle);
                    powerUp.active();
                    appliedPowerUps.add(powerUp);
                }
                powerUpIterator.remove();
            }
            if (powerUp.getY() > PLAY_AREA_Y + PLAY_AREA_HEIGHT) {
                powerUpIterator.remove();
            }
        }
    }

    public void updateAppliedPowerUp() {
        Iterator<PowerUp> powerUpIterator = appliedPowerUps.iterator();
        while (powerUpIterator.hasNext()) {
            PowerUp powerUp = powerUpIterator.next();
            if (powerUp.isExpired()) {
                powerUp.removeEffect(paddle);
                powerUpIterator.remove();
            }
            else if (powerUp instanceof GunPowerUp) {
                GunPowerUp gun = (GunPowerUp) powerUp;
                gun.updateWhileActive(paddle, map.getBricks(), balls);
                activePowerUps.addAll(gun.consumePendingDrops());
            }
        }
    }

    public static void addScore(int amount) {
        score += amount;
        if (score > topScore) topScore = score;

        if (controller != null) {
            controller.updateScore(score, topScore);
        }
    }

    public void lose() {
        lifeManage.loseLife(ball);
        if (lifeManage.getLives() <= 0 && controller != null) {
            Platform.runLater(() -> controller.updateScoreBoard());
        }
    }

    public boolean win() {
        return isWinnerScreenActive || (lifeManage.getLives() <= 0);
    }

    public void renderGame() {
        if (gameWon) {
            if (!isWinnerScreenActive) {
                isWinnerScreenActive = true;

                // Cập nhật bảng điểm lần cuối khi thắng
                if (controller != null) {
                    Platform.runLater(() -> controller.updateScoreBoard());
                }
            }

            // Xóa toàn bộ màn hình game
            renderer.clear();

            // Vẽ màn hình chiến thắng
            if (winnerImage != null) {
                double x = (gc.getCanvas().getWidth() - winnerImage.getWidth()) / 2;
                double y = (gc.getCanvas().getHeight() - winnerImage.getHeight()) / 2;
                gc.drawImage(winnerImage, x, y);
            }

            // Ngăn không cho vẽ phần gameplay nữa
            return;
        }

        // ----- Nếu chưa thắng -----
        if (currentLevel == 10) {
            // map boss
        }

        objects = new ArrayList<>();
        objects.addAll(lifeManage.getLiveIcons());
        objects.add(paddle);
        objects.addAll(balls);
        objects.addAll(activePowerUps);
        renderer.clear();
        renderer.renderMap(map);
        renderer.renderAll(objects);
        checkLevelCompletion();

        for (PowerUp powerUp : appliedPowerUps) {
            if (powerUp instanceof GunPowerUp) {
                ((GunPowerUp) powerUp).render(gc);
            }
        }
    }

}
