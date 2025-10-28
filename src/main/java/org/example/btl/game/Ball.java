package org.example.btl.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static org.example.btl.GameApplication.PLAY_AREA_X;
import static org.example.btl.GameApplication.PLAY_AREA_Y;
import static org.example.btl.GameApplication.PLAY_AREA_WIDTH;
import static org.example.btl.GameApplication.PLAY_AREA_HEIGHT;


public class Ball extends MovableObject {

    private double speed;
    private double directionX;
    private double directionY;
    private Image image;
    private double oldX;
    private double oldY;

    private boolean attached = true;

    public Ball(double x, double y, double width, double height,
                double directionX, double directionY, double speed) {
        super(x, y, width, height);
        this.directionX = directionX;
        this.directionY = directionY;
        this.speed = speed;
        image = loadImage("/org/example/btl/images/powerups/ball.png");

        updateVelocity();
    }

    public void updateVelocity() {
        this.dx = this.directionX * this.speed;
        this.dy = this.directionY * this.speed;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
        updateVelocity();
    }

    public double getDirectionX() {
        return directionX;
    }

    public double getDirectionY() {
        return directionY;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    //Kiem tra ball nam tren Paddle khong
    public boolean isAttached() {
        return attached;
    }

    public void setAttached(boolean attached) {
        this.attached = attached;
    }

    //Ball va cham voi gioi han man hinh
    public void bounceOff() {
        if (getX() <= PLAY_AREA_X) {
            setX(PLAY_AREA_X);
            directionX *= -1;
        } else if (getX() + getWidth() >= PLAY_AREA_X + PLAY_AREA_WIDTH) {
            setX(PLAY_AREA_X + PLAY_AREA_WIDTH - getWidth());
            directionX *= -1;
        } else if (getY() + getHeight() >= PLAY_AREA_Y + PLAY_AREA_HEIGHT) {
            //setAttached(true);
            //setY(PLAY_AREA_Y + PLAY_AREA_HEIGHT - getHeight());
            //directionY *= -1;
            return;
        } else if (getY() <= PLAY_AREA_Y) {
            setY(PLAY_AREA_Y);
            directionY *= -1;
        }
        updateVelocity();
    }

    public void bounce(@NotNull GameObject object) {
        Rectangle ballBounds = getRec();
        Rectangle objectBounds = object.getRec();

        if (!ballBounds.intersects(objectBounds)) {
            return;
        }

        if (object instanceof Paddle) {
            setY(object.getY() - getHeight() - 1);
            directionY = -Math.abs(directionY);
            updateVelocity();
            return;
        }

        double ballOldBottom = getOldY() + getHeight();

        if (ballOldBottom <= object.getY() || getOldY() >= object.getY() + object.getHeight()) {
            setY(getOldY());
            directionY *= -1;
        } else {
            if (getX() < object.getX()) {
                setX(getOldX() - 5);
            } else {
                setX(getOldX() + 5);
            }
            directionX *= -1;
        }
        updateVelocity();
    }

    @Override
    public void update() {
        oldX = getX();
        oldY = getY();
        move();
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, getX(), getY(), getHeight(), getWidth());
    }
}
