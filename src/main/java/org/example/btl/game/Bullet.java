package org.example.btl.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bullet extends MovableObject {
    private static final double BULLET_SPEED = 5;
    private Image image;
    private boolean active = true;

    public Bullet(double x, double y) {
        super(x, y, 8, 16); //
        this.dy = -BULLET_SPEED;
        this.image = loadImage("/org/example/btl/images/Bullet.png");
    }

    @Override
    public void update() {
        move();
        if (getY() + getHeight() < 0) {
            active = false;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (active && image != null) {
            gc.drawImage(image, getX(), getY(), getWidth(), getHeight());
        }
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
    }
}
