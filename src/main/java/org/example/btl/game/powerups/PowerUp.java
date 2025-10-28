package org.example.btl.game.powerups;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.example.btl.game.Animation;
import org.example.btl.game.MovableObject;
import org.example.btl.game.Paddle;
import org.example.btl.game.GameObject;

public abstract class PowerUp extends MovableObject {
    protected String type;
    protected long duration;
    private Animation animation;
    private boolean  isActive = false;
    private long activationTime;
    public static final double POWER_UP_WIDTH = 32;
    public static final double POWER_UP_HEIGHT = 32;

    public PowerUp(double x, double y, String type, long duration) {
        super(x, y, POWER_UP_WIDTH, POWER_UP_HEIGHT);
        animation = new Animation(6, 200);
        this.type = type;
        this.duration = duration;
        this.dy = 1;
        String imagePath = "/org/example/btl/images/powerups/PowerUp_";
        animation.loadAnimation(imagePath, this.type);
    }

    public String getType() {
        return type;
    }

    public void active() {
        this.activationTime = System.currentTimeMillis();
        this.isActive = true;
    }

    public boolean isExpired() {
        if (!isActive) {
            return false;
        }
        return System.currentTimeMillis() - activationTime > duration;
    }

    @Override
    public void update() {
        move();
        animation.update();
    }

    @Override
    public void render(GraphicsContext gc) {
        animation.render(gc, getX(), getY(), getWidth(), getHeight());
    }

    public abstract void applyEffect(Paddle paddle);

    public abstract void removeEffect(Paddle paddle);
}
