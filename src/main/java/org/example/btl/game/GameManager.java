package org.example.btl.game;

import org.example.btl.game.bricks.MapBrick;
import org.example.btl.game.Brick;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.example.btl.game.powerups.*;
import org.example.btl.game.sounds.SoundManager;
import org.example.btl.lives.LifeManage;

import static org.example.btl.GameApplication.*;

public class GameManager {

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

    public GameManager(GraphicsContext gc) {
        this.renderer = new Renderer(gc);
        this.gc = gc;
        initGame();
    }

    private void initGame() {
        paddle = new Paddle(540, 614, 64, 24, 3);
        ball = new Ball(0, 0, 12, 12, 2, -2, 1);
        balls = new ArrayList<>();
        balls.add(ball);
        map = new MapBrick();
        this.currentLevel = 0;
        loadLevel(currentLevel);
        activePowerUps = new ArrayList<>();
        appliedPowerUps = new ArrayList<>();
        lifeManage = new LifeManage(5);
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

    private void nextLevel() {
        resetLevelState();
        this.currentLevel++;
        loadLevel(this.currentLevel);
    }


    private void checkLevelCompletion() {
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
        if (levelNumber == 11) {
            map.createBossMap(1152, 704);
            return;
        }

        int[][] layout = MapBrick.loadMap(levelNumber);

        if (layout != null) {
            map.createMap(layout, PLAY_AREA_X, PLAY_AREA_Y);
        } else {
            this.gameWon = true;
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

                        if (brick.getBrickType() == 20) {

                            // khi gạch boss vỡ
                            currentLevel = 5;
                            loadLevel(currentLevel);
                            return;
                        }
                    }
                    else {
                        SoundManager.playBrickHitSound();
                    }

                    if (brick.getBrickType() == 2) {
                        PowerUp newPowerUp;
                        switch (brick.getPowerUpType()) {
                            case 1:
                                newPowerUp = new ShrinkPaddlePowerUp(brick.getX(), brick.getY());
                                activePowerUps.add(newPowerUp);
                                break;
                            case 2:
                                newPowerUp = new ExpandPaddlePowerUp(brick.getX(), brick.getY());
                                activePowerUps.add(newPowerUp);
                                break;
                            case 3:
                                newPowerUp = new TripleBallPowerUp(brick.getX(), brick.getY(), balls);
                                activePowerUps.add(newPowerUp);
                                break;
                            case 4:
                                newPowerUp = new FastBallPowerUp(brick.getX(), brick.getY(), balls);
                                activePowerUps.add(newPowerUp);
                                break;
                            case 5:
                                newPowerUp = new GunPowerUp(brick.getX(), brick.getY());
                                activePowerUps.add(newPowerUp);
                                break;
                        }
                    }
                }
                if (brick.isDestroyed()) {
                    brickIterator.remove();
                    break;
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
                SoundManager.playPowerUpSound();
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

    public void lose() {
        lifeManage.loseLife(ball);
    }

    public void resetLevelState() {
        for (PowerUp powerUp : appliedPowerUps) {
            powerUp.removeEffect(paddle);
        }
        appliedPowerUps.clear();
        activePowerUps.clear();
        balls.clear();

        Ball newBall;
        newBall = new Ball(0, 0, 12, 12, 2, -2, 1);
        newBall.setAttached(true);
        balls.add(newBall);
    }

    public void renderGame() {
        if (gameWon) {
            checkLevelCompletion();
            resetLevelState();
        }

        if (currentLevel == 11 && gameWon) {
            // hien chien thang
            return;
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
