package org.example.btl.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Brick extends GameObject {
    protected Image brickImage;
    protected int brickType;
    protected int powerUpType;
    protected int healthBrick;

    public Brick(double x, double y, double width, double height, int brickType, int powerUpType) {
        super(x, y, width, height);
        this.brickType = brickType;
        this.powerUpType = powerUpType;
    }

    public void takeDamage() {
        if (healthBrick > 0 && healthBrick < 999999) {
            healthBrick--;
        }
    }


    public boolean isDestroyed() {
        return healthBrick <= 0;
    }

    public int getBrickType() {
        return brickType;
    }

    public int getPowerUpType() {
        return powerUpType;
    }

    @Override
    public void update() {}

    @Override
    public void render(GraphicsContext gc) {
        if (brickImage != null) {
            gc.drawImage(brickImage, getX(), getY(), getWidth(), getHeight());
        } else {
            System.err.println("Ảnh Brick chưa được tải.");
        }
    }

    protected Image loadBrickImage(String colorName) {
        return loadImage("/org/example/btl/images/bricks/Brick_" + colorName + ".png");
    }
}