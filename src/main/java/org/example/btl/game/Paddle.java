package org.example.btl.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static org.example.btl.GameApplication.PLAY_AREA_WIDTH;
import static org.example.btl.GameApplication.PLAY_AREA_X;

public class Paddle extends MovableObject {
    private double speed;
    private Image image;

    public Paddle(double x, double y, double width, double height, double speed) {
        super(x, y, width, height);
        this.speed = speed;
        int skinIndexSelected = SkinManager.getSkinIndex();
        image = loadImage("/org/example/btl/images/paddles/Paddle"+ skinIndexSelected + ".png");
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void startMovingLeft() {
        this.dx = -speed;
    }

    public void startMovingRight() {
        this.dx = speed;
    }

    public void stop() {
        this.dx = 0;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public void update() {
        move();
        if (getX() <= PLAY_AREA_X) {
            setX(PLAY_AREA_X);
        } else if (getX() + getWidth() >= PLAY_AREA_X + PLAY_AREA_WIDTH) {
            setX(PLAY_AREA_X + PLAY_AREA_WIDTH - getWidth());
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, getX(), getY(), getWidth(), getHeight());
    }
}
