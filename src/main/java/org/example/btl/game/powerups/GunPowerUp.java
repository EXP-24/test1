package org.example.btl.game.powerups;

import javafx.scene.canvas.GraphicsContext;
import org.example.btl.game.*;
import org.example.btl.game.Brick;
import org.example.btl.game.sounds.SoundManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GunPowerUp extends PowerUp {
    private static final long FIRE_INTERVAL = 300;
    private long lastFireTime;
    private final List<Bullet> bullets;
    private boolean isPickedUp = false;

    private final List<PowerUp> pendingDrops = new ArrayList<>();

    public GunPowerUp(double x, double y) {
        super(x, y, "Gun", 5000);
        this.bullets = new ArrayList<>();
    }

    @Override
    public void applyEffect(Paddle paddle) {
        isPickedUp = true;
        lastFireTime = System.currentTimeMillis();
    }

    @Override
    public void removeEffect(Paddle paddle) {
        bullets.clear();
    }

    public void updateBullets(List<Brick> bricks, List<Ball> balls) {
        Iterator<Bullet> bulletIterator = bullets.iterator();

        while (bulletIterator.hasNext()) {
            Bullet b = bulletIterator.next();
            b.update();

            if (!b.isActive()) {
                bulletIterator.remove();
                continue;
            }

            Iterator<Brick> brickIterator = bricks.iterator();
            while (brickIterator.hasNext()) {
                Brick brick = brickIterator.next();
                if (b.isColliding(brick)) {
                    brick.takeDamage();
                    if (brick.isDestroyed()) {
                        SoundManager.playGunFire();
                        brickIterator.remove();
                    }
                    b.deactivate();

                    if (brick.getBrickType() == 2) {
                        PowerUp newPowerUp = null;
                        switch (brick.getPowerUpType()) {
                            case 1:
                                newPowerUp = new TinyBallPowerUp(brick.getX(), brick.getY(), balls);
                                break;
                            case 2:
                                newPowerUp = new FastBallPowerUp(brick.getX(), brick.getY(), balls);
                                break;
                            case 3:
                                newPowerUp = new TripleBallPowerUp(brick.getX(), brick.getY(), balls);
                                break;
                            case 4:
                                newPowerUp = new ExpandPaddlePowerUp(brick.getX(), brick.getY());
                                break;
                            case 5:
                                newPowerUp = new GunPowerUp(brick.getX(), brick.getY());
                                break;
                        }
                        if (newPowerUp != null) {
                            pendingDrops.add(newPowerUp);
                        }
                    }
                    break;
                }
            }
        }
    }

    public void updateWhileActive(Paddle paddle, List<Brick> bricks, List<Ball> balls) {
        if (!isPickedUp) return;

        long now = System.currentTimeMillis();
        if (now - lastFireTime > FIRE_INTERVAL) {
            double xLeft = paddle.getX() + 5;
            double xRight = paddle.getX() + paddle.getWidth() - 10;
            double y = paddle.getY() - 10;

            bullets.add(new Bullet(xLeft, y));
            bullets.add(new Bullet(xRight, y));

            lastFireTime = now;
        }

        updateBullets(bricks, balls);
    }

    /** Trả về các powerup được sinh ra khi đạn phá gạch */
    public List<PowerUp> consumePendingDrops() {
        if (pendingDrops.isEmpty()) return List.of();
        List<PowerUp> result = new ArrayList<>(pendingDrops);
        pendingDrops.clear();
        return result;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!isPickedUp) {
            super.render(gc);
        }
        for (Bullet b : bullets) {
            b.render(gc);
        }
    }

    public boolean isPickedUp() {
        return isPickedUp;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }


}
