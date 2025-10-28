package org.example.btl.game.bricks;

import org.example.btl.game.Brick;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Objects;

public class BossBrick extends Brick {
    private int hp;
    private Image[] bossStages;
    private Image hitImage;
    private int currentStage;
    private boolean isHit = false;
    private long hitStartTime;
    private static final long HIT_DURATION = 150;

    public BossBrick(double x, double y, double width, double height, int brickType, int powerUpType) {
        super(x, y, width, height, brickType, powerUpType);
        this.hp = 50;
        this.currentStage = 1;

        preloadImages();
    }

    private void preloadImages() {
        bossStages = new Image[9];
        for (int i = 1; i <= 8; i++) {
            try {
                bossStages[i] = new Image(Objects.requireNonNull(getClass().getResource(
                        "/org/example/btl/images/bricks/Brick_Boss" + i + ".png")).toExternalForm());
            } catch (Exception e) {
                System.err.println("Không thể tải Brick_Boss" + i + ".png");
            }
        }

        try {
            hitImage = new Image(Objects.requireNonNull(getClass().getResource(
                    "/org/example/btl/images/bricks/Brick_Boss0.png")).toExternalForm());
        } catch (Exception e) {
            System.err.println("Không thể tải ảnh Brick_Boss_Hit.png");
        }
    }

    public void takeDamage() {
        if (hp > 0) {
            hp--;
            updateBossStage();
            // bật hiệu ứng animation
            isHit = true;
            hitStartTime = System.currentTimeMillis();
        }
    }

    private void updateBossStage() {
        int newStage;
        if (hp < 2) newStage = 8;
        else if (hp < 6) newStage = 7;
        else if (hp < 10) newStage = 6;
        else if (hp < 15) newStage = 5;
        else if (hp < 25) newStage = 4;
        else if (hp < 30) newStage = 3;
        else if (hp < 40) newStage = 2;
        else newStage = 1;

        currentStage = newStage;
    }

    @Override
    public void render(GraphicsContext gc) {
        long now = System.currentTimeMillis();
        if (isHit && hitImage != null) {
            gc.drawImage(hitImage, getX(), getY(), getWidth(), getHeight());
            if (now - hitStartTime >= HIT_DURATION) {
                isHit = false;
            }
        } else {
            Image img = bossStages[currentStage];
            if (img != null) {
                gc.drawImage(img, getX(), getY(), getWidth(), getHeight());
            }
        }
    }

    @Override
    public boolean isDestroyed() {
        return hp <= 0;
    }

    public int getHp() {
        return hp;
    }
}
